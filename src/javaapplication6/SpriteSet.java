/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import processing.core.PApplet;
import processing.core.PImage;

// Sprite frame collection
// Used to cut sprite sheet into four direction animation arrays
public class SpriteSet {

    public PImage[][] walk;   // [direction][frame] for walking animation
    public PImage[][] idle;   // [direction][frame] for idle animation
    public PImage[][] attack; // [direction][frame] for attack animation

    private int cellW; // Width of each sprite frame in pixels
    private int cellH; // Height of each sprite frame in pixels

    /**
     * Load player walk and sword attack sprites
     * Idle uses a specific frame from walk animation
     */
    public void loadPlayer(PApplet app, int cellW, int cellH, String walkSheet, String swordAttackSheet, int idleFrameIndex) {
        this.cellW = cellW;
        this.cellH = cellH;

        // Load walk and attack animations from separate sheets
        walk = cut(app, walkSheet);
        attack = cut(app, swordAttackSheet);

        // Create idle animation by picking one frame from walk cycle
        idle = new PImage[4][1];
        for (int d = 0; d < 4; d++) {
            if (walk != null && walk[d] != null && walk[d].length > 0) {
                int idx = idleFrameIndex;
                if (idx < 0) idx = 0;
                if (idx >= walk[d].length) idx = walk[d].length - 1;
                idle[d][0] = walk[d][idx]; // Use specified frame as idle pose
            } else {
                idle[d][0] = null;
            }
        }
    }

    /**
     * Load enemy walk and attack sprites
     */
    public void loadEnemy(PApplet app, int cellW, int cellH, String walkSheet, String attackSheet) {
        this.cellW = cellW;
        this.cellH = cellH;
        walk = cut(app, walkSheet);
        idle = null; // Enemies don't have idle animation
        if (attackSheet == null) {
            attack = null;
        } else {
            attack = cut(app, attackSheet);
        }
    }

    // Overload: load enemy with only attack animation (for stationary enemies)
    public void loadEnemy(PApplet app, int cellW, int cellH, String attackSheet) {
        this.cellW = cellW;
        this.cellH = cellH;
        walk = null; // No walking animation
        idle = null; // No idle animation
        if (attackSheet == null) {
            attack = null;
        } else {
            attack = cut(app, attackSheet);
        }
    }

    /**
     * Cut sprite sheet into 4-direction animation arrays
     * Assumes standard layout: 4 rows (DOWN, UP, RIGHT, LEFT from top to bottom)
     */
    private PImage[][] cut(PApplet app, String file) {
        PImage sheet = app.loadImage(file);
        if (sheet == null) return null;

        // Calculate how many frames per row
        int cols = sheet.width / cellW;
        int rows = sheet.height / cellH;
        if (cols <= 0 || rows <= 0) return null;

        // Map directions to sprite sheet rows (Processing loads top-to-bottom)
        // DOWN=3, UP=2, RIGHT=1, LEFT=0 matches your sprite sheet layout
        int[] dirToRow = { 3, 2, 1, 0 };

        PImage[][] out = new PImage[4][cols];

        for (int dir = 0; dir < 4; dir++) {
            int ry = dirToRow[dir];
            if (ry < 0) ry = 0;
            if (ry > rows - 1) ry = rows - 1;

            for (int i = 0; i < cols; i++) {
                out[dir][i] = sheet.get(i * cellW, ry * cellH, cellW, cellH);
            }
        }
        return out;
    }
}