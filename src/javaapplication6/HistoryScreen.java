/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
// HistoryScreen shows the result of the last game run

import processing.core.PApplet;

// Keeps it simple for high school project level
// Only displays the most recent saved data from SaveManager.last...
public class HistoryScreen {

    /**
     * Draw the history screen
     * @param p
     * @param hasHistory
     */
public static void draw(PApplet p, boolean hasHistory) {
    // Dark background for the history screen
    p.background(8);

    // White text, centered on screen
    p.fill(255);
    p.textAlign(PApplet.CENTER, PApplet.CENTER);

    // Title at the top
    p.textSize(48);
    p.text("History", p.width / 2f, 140);

    // Main content size
    p.textSize(18);
    if (!hasHistory) {
        // Show message when no game has been played yet
        p.text("No history yet.", p.width / 2f, 280);
    } else {
        // Load saved data from SaveManager
        String res = SaveManager.lastWin ? "WIN" : "FAIL";
        int sec = SaveManager.lastSeconds;
        String time = SaveManager.saveTime;

        // Display last run details (vertically spaced)
        p.text("Saved Time: " + time, p.width / 2f, 220);
        p.text("Last Run Result: " + res, p.width / 2f, 260);
        p.text("Time Survived: " + sec + "s", p.width / 2f, 300);
    }

    // Small instruction at the bottom
    p.textSize(14);
    p.fill(220);  // Slightly dimmer than main text
    p.text("Press ESC to go back", p.width / 2f, 520);
    }
}