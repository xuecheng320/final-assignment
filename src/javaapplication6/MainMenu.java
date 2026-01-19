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

// Main menu screen
// Handles drawing start history quit options
// Also handles returning user selection based on key input
public class MainMenu {

    // Return codes for menu navigation
    public static final int NONE = 0;
    public static final int START_GAME = 1;
    public static final int HISTORY = 2;
    public static final int QUIT = 3;

    /**
     * Draw the main menu screen
     * @param p
     * @param hasHistory
     */
    public static void draw(PApplet p, boolean hasHistory) {
        // Dark background to make text stand out
        p.background(10);

        // Title styling
        p.fill(255);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.textSize(54);
        p.text("Turtle Island Vigil", p.width / 2f, 140);

        // Menu options as clickable-looking boxes
        p.textSize(20);
        drawOption(p, p.width / 2f, 290, 640, 44, "[1] Start Game");

        // Show different history label based on whether save data exists
        if (hasHistory) {
            drawOption(p, p.width / 2f, 360, 640, 44, "[H] History (Last Run)");
        } else {
            drawOption(p, p.width / 2f, 360, 640, 44, "[H] History (Empty)");
        }

        drawOption(p, p.width / 2f, 430, 640, 44, "[ESC] Quit");

        // Helpful hints at the bottom
        p.textSize(14);
        p.fill(220);  // Slightly dimmer than main text
        p.text("Goal: Keep the Lantern alive until the timer ends.", p.width / 2f, 560);
        p.text("Controls: WASD move | SHIFT sprint | J attack | K switch weapon | ESC menu",
               p.width / 2f, 585);
    }

    /**
     * Draw a single menu option as a rounded rectangle with centered text
     */
    private static void drawOption(PApplet p, float cx, float y, float w, float h, String text) {
        // Calculate top-left corner from center
        float x = cx - w / 2f;
        float r = 10;  // Corner radius for rounded look

        // Fill the button background
        p.noStroke();
        p.fill(20, 180);
        p.rect(x, y - h / 2f, w, h, r);

        // Add a subtle outline
        p.stroke(80);
        p.noFill();
        p.rect(x, y - h / 2f, w, h, r);

        // Draw white centered text
        p.noStroke();
        p.fill(255);
        p.text(text, cx, y);
    }

    /**
     * Handle key input and return selected action
     */
    public static int handleKey(char key, int keyCode, boolean hasHistory) {
        // Map keys to menu actions
        if (key == '1') return START_GAME;
        if (key == 'h' || key == 'H') return HISTORY;

        // ESC always quits
        if (keyCode == PApplet.ESC) return QUIT;

        // No valid selection
        return NONE;
    }
}
