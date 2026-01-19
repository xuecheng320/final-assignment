/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import processing.core.PApplet;
import processing.core.PImage;

// Arrow shot by the player
// Flies in a straight line at fixed speed
// Removed when it flies out of map bounds
public class Arrow {

    // Current position x coordinate of the arrow
    public float x;

    // Current position y coordinate of the arrow
    public float y;

    // Per frame movement speed x component
    public float vx;

    // Per frame movement speed y component
    public float vy;

    // Damage on hit
    public int dmg = 8;

    // Whether still active false means it will be removed from list
    public boolean alive = true;

    // Shared sprite image for all arrows set once in MySketch
    public static PImage sprite;

    /**
     * Set the arrow's sprite image
     */
    public static void setSprite(PImage img) {
        sprite = img;
    }

    /**
     * Create an arrow
     * Pass initial position velocity and damage
     */
    public Arrow(float x, float y, float vx, float vy, int dmg) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.dmg = dmg;
    }

    /**
     * Create an arrow
     * Default damage is 5
     */
    public Arrow(float x, float y, float vx, float vy) {
        this(x, y, vx, vy, 5);
    }

    /**
     * Update arrow position
     * Mark as dead if it flies out of map
     */
    public void update(int worldW, int worldH) {
        x += vx;
        y += vy;

        if (x < -20 || y < -20 || x > worldW + 20 || y > worldH + 20) {
            alive = false;
        }
    }

    /**
     * Draw the arrow's sprite
     */
    public void draw(PApplet app) {
        if (sprite != null) {
            app.image(sprite, x - 8, y - 8, 32, 32);
        }
    }
}