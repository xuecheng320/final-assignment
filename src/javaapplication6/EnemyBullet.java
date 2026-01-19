/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
// Bullet fired by ranged enemies

import processing.core.PApplet;
import processing.core.PImage;

// Flies in a straight line at fixed speed
// Removed when it flies out of map bounds
public class EnemyBullet {

    // Current position x coordinate of the bullet
    public float x;

    // Current position y coordinate of the bullet
    public float y;

    // Per frame movement speed x component
    public float vx;

    // Per frame movement speed y component
    public float vy;

    // Damage on hit
    public int dmg = 3;

    // Whether still active false means it will be removed from list
    public boolean alive = true;

    // Bullet sprite image
    public PImage img;

    /**
     * Create a bullet
     * Pass initial position velocity damage and sprite image
     */
    public EnemyBullet(float x, float y, float vx, float vy, int dmg, PImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.dmg = dmg;
        this.img = img;
    }

    /**
     * Update bullet position
     * Mark as dead when it goes beyond map bounds
     */
    public void update(int worldW, int worldH) {
        x += vx;
        y += vy;

        if (x < -40 || y < -40 || x > worldW + 40 || y > worldH + 40) {
            alive = false;
        }
    }

    /**
     * Draw the bullet sprite
     */
    public void draw(PApplet app) {
        if (img != null) {
            app.image(img, x, y, 32, 32);
        }
    }
}