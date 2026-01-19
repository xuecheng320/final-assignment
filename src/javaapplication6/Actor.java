package javaapplication6;

import processing.core.PApplet;
import processing.core.PImage;

// Base class for all moving units in the game
// Holds position size health direction and provides basic rectangle overlap detection and animation frame switching
public abstract class Actor {

    // Face left
    public static final int LEFT = 0;
    // Face right
    public static final int RIGHT = 1;
    // Face up
    public static final int UP = 2;
    // Face down
    public static final int DOWN = 3;

    // Processing drawing object used for rendering and loading resources
    protected PApplet app;

    // Top left x coordinate Processing coordinate system origin is at top left of screen
    public float x;

    // Top left y coordinate Processing coordinate system y increases downward
    public float y;

    // Collision box width in pixels
    public int width;

    // Collision box height in pixels
    public int height;

    // Current facing direction using LEFT RIGHT UP DOWN
    public int dir = DOWN;

    // Whether currently moving used to decide walk animation
    public boolean moving = false;

    // Current health points
    public int hp = 100;

    // Maximum health points
    public int maxHp = 100;

    // Base attack power
    public int atk = 10;

    // Walk animation frames walk[dir][frame]
    protected PImage[][] walk;

    // Idle animation frames idle[dir][frame]
    protected PImage[][] idle;

    // Attack animation frames attack[dir][frame]
    protected PImage[][] attack;

    // Current animation frame index
    protected int frame = 0;

    // Frame timer used to control animation speed
    protected int frameTimer = 0;

    // How many frames to wait before switching image larger value means slower animation
    protected int frameDelay = 6;

    /**
     * Create a character
     * Pass initial position and collision box size
     */
    public Actor(PApplet app, float x, float y, int width, int height) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Return collision box center x coordinate
     */
    public float cx() {
        return x + width / 2f;
    }

    /**
     * Return collision box center y coordinate
     */
    public float cy() {
        return y + height / 2f;
    }

    //ai:chatgpt
    //prompt: Processing how to check if two rectangles overlap?
    //prompt: Java collision detection between two boxes?
    //prompt: Why does my game think objects are colliding when they're not? Fix my code.
    /**
     * Check if another rectangle overlaps with this character's rectangle
     * Commonly used for overlap detection between character and obstacles or enemies
     */
    public boolean overlapsRect(float otherX, float otherY, float otherWidth, float otherHeight) {
        return x < otherX + otherWidth
                && x + width > otherX
                && y < otherY + otherHeight
                && y + height > otherY;
    }
    //end

    /**
     * Check if another character overlaps with this character's rectangle
     */
    public boolean overlapsActor(Actor other) {
        return overlapsRect(other.x, other.y, other.width, other.height);
    }

    /**
     * Animation timer
     * Call once per update to advance frame
     */
    protected void animTick() {
        frameTimer++;
        if (frameTimer >= frameDelay) {
            frameTimer = 0;
            frame++;
        }
    }

    //ai:chatgpt
    //prompt: How to safely get an image from a 2D array in Java without crashing?
    //prompt: My game crashes when I try to draw sprites. Fix my array code.
    //prompt: How to handle null sprite arrays in Processing?
    /**
     * Safely get current frame from 2D animation array
     * If array is empty or out of bounds return null so draw does not crash
     */
    protected PImage pickFrame(PImage[][] set) {
        if (set == null) return null;
        if (dir < 0 || dir >= set.length) return null;
        if (set[dir] == null || set[dir].length == 0) return null;
        int idx = frame % set[dir].length;
        return set[dir][idx];
    }

    /**
     * Update logic each frame
     */
    public abstract void update();

    /**
     * Draw character each frame
     */
    public abstract void draw();
}