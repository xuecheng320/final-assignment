package javaapplication6;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aiden f
 */


import processing.core.PApplet;

// Dialogue box utility class

import processing.core.PApplet;

// Used to show hint text at the bottom of the screen
// Can be set to display for a number of frames or stay until cleared
public class DialogueBox {

    // Currently displayed text
    private static String text = "";

    // Remaining display frames 0 means stay until cleared
    private static int timer = 0;

    /**
     * Show a piece of text
     * frames value 0 means stay until cleared
     */
    public static void show(String msg, int frames) {
        text = msg;
        timer = frames;
    }

    /**
     * Clear the dialogue box
     */
    public static void clear() {
        text = "";
        timer = 0;
    }

    /**
     * Check if there is text currently being displayed
     */
    public static boolean hasText() {
        return text != null && !text.equals("");
    }

    /**
     * Update timer each frame
     * Automatically clear when timer ends
     */
    public static void update() {
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                text = "";
            }
        }
    }

    /**
     * Draw the dialogue box
     */
    public static void draw(PApplet p) {
        if (!hasText()) return;

        int boxH = 140;                    // Height of the dialogue box in pixels
        int y = p.height - boxH;           // Y position so the box sticks to the bottom of the screen

        p.noStroke();                      // No outline around shapes
        p.fill(0, 180);                    // Semi-transparent black background
        p.rect(0, y, p.width, boxH);       // Draw the background rectangle

        p.fill(255);                       // White text color
        p.textAlign(PApplet.LEFT, PApplet.TOP); // Align text to top-left
        p.textSize(18);                    // Main message font size

        // Draw wrapped text with padding: 24px left/right, 20px top, and space for hint at bottom
        p.text(text, 24, y + 20, p.width - 48, boxH - 40);

        p.textSize(12);                    // Smaller font for control hint
        p.textAlign(PApplet.RIGHT, PApplet.BOTTOM); // Align to bottom-right corner
        p.text("J to next page", p.width - 18, p.height - 12); // Slightly inset from bottom-right edge
    }
}