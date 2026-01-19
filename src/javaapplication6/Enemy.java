package javaapplication6;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import static javaapplication6.Actor.DOWN;
import static javaapplication6.Actor.LEFT;
import static javaapplication6.Actor.RIGHT;
import static javaapplication6.Actor.UP;
import processing.core.PApplet;
import processing.core.PImage;

// Melee enemy
// Moves toward the lantern
// Deals damage on contact with lantern or player
public class Enemy extends Actor {

    // Movement speed lower than player to give player time to react
    public float speed = 1.20f;

    // Attack lantern cooldown frames prevents damage every frame when touching
    private int lanternCooldown = 60;

    // Attack player cooldown frames prevents damage every frame when touching
    private int playerCooldown = 0;

    // Cooldown maximum value 
    public int playerCooldownMax = 30;

    // Attack animation timer shows attack frames when greater than 0
    protected int attackTimer = 0;

    /**
     * Create an enemy
     * hpBase makes enemies tougher at different difficulties
     */
    public Enemy(PApplet app, float x, float y, SpriteSet sprites, int hpBase) {
        super(app, x, y, 96, 96);
        this.walk = sprites.walk;
        this.idle = sprites.walk;
        this.attack = sprites.attack;
        maxHp = hpBase;
        hp = hpBase;
        atk = 4;
    }

    //ai:chatgpt
    //prompt: Processing how to make enemy move towards a target smoothly?
    //prompt: Java how to calculate direction to move towards player?
    //prompt: Why does my enemy move faster when farther away? Fix my code.
    /**
     * Update enemy AI
     * Enemy chases lantern and attacks on contact
     */
   public void updateAI(Lantern lantern, Player player, ArrayList<Obstacle> obstacles, int worldW, int worldH) {
    // Decrease cooldowns each frame
        if (lanternCooldown > 0) lanternCooldown--;
        if (playerCooldown > 0) playerCooldown--;
        if (attackTimer > 0) attackTimer--;

        // Calculate direction toward the lantern (target)
        float dx = lantern.cx() - cx();
        float dy = lantern.cy() - cy();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        float nx = 0;
        float ny = 0;

        // Only move if not too close (avoid jittering at distance < 1)
        if (dist > 1) {
            nx = dx / dist * speed;  // Normalize and scale by speed
            ny = dy / dist * speed;
        }

        // Set moving flag for animation
        moving = (nx != 0 || ny != 0);

        // Update facing direction based on movement
        if (Math.abs(nx) > Math.abs(ny)) {
            if (nx < 0) dir = LEFT;
            else if (nx > 0) dir = RIGHT;
        } else {
            if (ny < 0) dir = UP;
            else if (ny > 0) dir = DOWN;
        }

        // Move X and check collision with obstacles
        x += nx;
        if (obstacles != null) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).overlapsRect(x, y, width, height)) {
                    x -= nx;  // Undo movement if blocked
                    break;
                }
            }
        }

        // Move Y and check collision with obstacles
        y += ny;
        if (obstacles != null) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).overlapsRect(x, y, width, height)) {
                    y -= ny;  // Undo movement if blocked
                    break;
                }
            }
        }

        // Keep enemy inside world bounds
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > worldW) x = worldW - width;
        if (y + height > worldH) y = worldH - height;

        // Advance animation frame
        animTick();

        // Attack lantern on contact (with cooldown)
        if (overlapsActor(lantern) && lanternCooldown == 0) {
            lantern.damage(atk);
            lanternCooldown = playerCooldownMax;
            attackTimer = 10;  // Show short attack animation
        }

        // Attack player on contact (with cooldown)
        if (overlapsActor(player) && playerCooldown == 0) {
            player.hp -= atk;
            playerCooldown = playerCooldownMax;
            attackTimer = 10;  // Show short attack animation
    }
}
    //end

    /**
     * This project uses updateAI so this is left empty
     */
    @Override
    public void update() {
    }

    /**
     * Draw the enemy
     * Show attack frames when attacking otherwise show walk frames
     */
    @Override
    public void draw() {
        PImage img;
        // Use attack animation if currently attacking, otherwise use walk animation
        if (attackTimer > 0 && attack != null) {
            img = pickFrame(attack);
        } else {
            img = pickFrame(walk);
        }

        // Only draw if we actually got a valid image
        if (img != null) {
            app.image(img, x, y, width, height);
        }
    }
}