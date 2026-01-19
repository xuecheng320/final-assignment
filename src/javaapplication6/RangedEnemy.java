package javaapplication6;

import java.util.ArrayList;
import javaapplication6.Enemy;
import javaapplication6.EnemyBullet;
import javaapplication6.Lantern;
import javaapplication6.Obstacle;
import javaapplication6.Player;
import javaapplication6.SpriteSet;
import processing.core.PApplet;
import processing.core.PImage;

// Ranged enemy
// Does not move
// Shoots player when nearby
// Shoots lantern when player is far away
public class RangedEnemy extends Enemy {

    // Current shooting cooldown
    private int shootCooldown = 0;

    // Shooting cooldown maximum value
    private int shootCooldownMax = 180;

    // Player within this range will be shot
    private float playerRange = 260;

    // Bullet speed
    private float bulletSpeed = 3f;

    // Damage to player on hit
    private int bulletDamagePlayer = 40;

    // Damage to lantern on hit
    private int bulletDamageLantern = 20;

    // Bullet image
    private PImage bulletImg;

    /**
     * Create a ranged enemy
     * bulletImg is the bullet sprite image
     */
    public RangedEnemy(PApplet app, float x, float y, SpriteSet sprites, int hpBase, PImage bulletImg) {
        super(app, x, y, sprites, hpBase);
        this.bulletImg = bulletImg;

        width = 96;
        height = 108;
    }
    
/**
 * Update ranged enemy
 * Does not move only shoots toward target
 */
public void updateRanged(Lantern lantern, Player player, ArrayList<Obstacle> obstacles, int worldW, int worldH, ArrayList<EnemyBullet> bullets) {

    // Decrease shoot cooldown each frame
    if (shootCooldown > 0) shootCooldown--;

    //ai:gemini
    //prompt: Processing how to make enemy shoot bullets toward player?
    //prompt: Java how to calculate bullet direction to hit moving target?
    // Decide who to shoot: player if close, otherwise lantern
    float dxP = player.cx() - cx();
    float dyP = player.cy() - cy();
    float distPlayer = (float) Math.sqrt(dxP * dxP + dyP * dyP);

    float targetX;
    float targetY;
    int damage;

    if (distPlayer <= playerRange) {
        targetX = player.cx();
        targetY = player.cy();
        damage = bulletDamagePlayer;
    } else {
        targetX = lantern.cx();
        targetY = lantern.cy();
        damage = bulletDamageLantern;
    }

    // Calculate bullet direction toward chosen target
    float dx = targetX - cx();
    float dy = targetY - cy();
    float dist = (float) Math.sqrt(dx * dx + dy * dy);

    float vx = 0;
    float vy = 0;
    if (dist > 0.001f) {
        vx = dx / dist * bulletSpeed;  // Normalize and scale by speed
        vy = dy / dist * bulletSpeed;
    }

    // Face the direction the bullet is going
    if (Math.abs(vx) > Math.abs(vy)) {
        if (vx < 0) dir = LEFT;
        else if (vx > 0) dir = RIGHT;
    } else {
        if (vy < 0) dir = UP;
        else if (vy > 0) dir = DOWN;
    }
    //end

    // Advance animation frame
    animTick();

    // Fire a bullet if cooldown is ready
    if (bullets != null && shootCooldown == 0) {
        float sx = cx();
        float sy = cy();

        EnemyBullet b = new EnemyBullet(sx, sy, vx, vy, damage, bulletImg);

        // Don't shoot if spawned inside an obstacle
        if (obstacles != null) {
            boolean blocked = false;
            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).containsPoint(sx, sy)) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                bullets.add(b);
            }
        } else {
            bullets.add(b);
        }

        shootCooldown = shootCooldownMax;
        attackTimer = 10;  // Show short attack flash
    }

    // Keep enemy inside world bounds (just in case)
    if (x < 0) x = 0;
    if (y < 0) y = 0;
    if (x + width > worldW) x = worldW - width;
    if (y + height > worldH) y = worldH - height;
}
}
   
