/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
import processing.core.PApplet;

// Solid obstacle
// Blocks player and enemy movement
// Does not need animation so a solid color block is enough for drawing
public class Obstacle {

    // Top left x coordinate Processing coordinate system origin is at top left of screen
    public float x;

    // Top left y coordinate Processing coordinate system y increases downward
    public float y;

    // Obstacle width in pixels
    public float width;

    // Obstacle height in pixels
    public float height;

    /**
     * Create an obstacle
     * Pass top left coordinates and width height
     */
    public Obstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Draw the obstacle
     * Obstacle is a fixed block and does not need sprite images
     */
    public void draw(PApplet app) {
        app.fill(60, 80, 90);
        app.rect(x, y, width, height);
    }

    //ai:gemini
    //prompt: Why does my game think objects are colliding when they're not? Fix my code.
    /**
     * Check if another rectangle overlaps with this obstacle
     * Used during movement to determine if blocked
     */
    public boolean overlapsRect(float otherX, float otherY, float otherWidth, float otherHeight) {
        return otherX < x + width
                && otherX + otherWidth > x
                && otherY < y + height
                && otherY + otherHeight > y;
    }
    //end

    /**
     * Check if a point is inside the obstacle
     * Commonly used for click detection or simple trigger checks
     */
    public boolean containsPoint(float px, float py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }
}