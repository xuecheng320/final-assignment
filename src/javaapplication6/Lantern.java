/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import processing.core.PApplet;

// Lantern
// Serves as the core objective to protect in this game session
// The lantern does not move and the game ends when its health reaches zero
public class Lantern extends Actor {

    /**
     * Create a lantern at the specified position
     * Lantern size is fixed
     */
    public Lantern(PApplet app, float x, float y) {
        super(app, x, y, 48, 48);
        maxHp = 500;
        hp = 500;
    }

    /**
     * Lantern takes damage
     * Ensures hp never goes below zero
     */
    public void damage(int d) {
        hp -= d;
        if (hp < 0) hp = 0;
    }

    /**
     * Lantern does not need to update position
     */
    @Override
    public void update() {
    }

    /**
     * Draw the lantern and health bar
     * Uses simple shapes instead of sprite images to make classroom project quick to complete
     */
    @Override
public void draw() {
    // Draw lantern body as a yellow rectangle
    app.noStroke();
    app.fill(255, 220, 80);
    app.rect(x, y, width, height);

    // Label in the center so it's clear what this object is
    app.fill(0);
    app.textAlign(PApplet.CENTER, PApplet.CENTER);
    app.textSize(10);
    app.text("LANTERN", cx(), cy());

    // Background of health bar (dark semi-transparent)
    app.fill(0, 160);
    app.rect(x, y - 10, width, 6);

    // Calculate how much of the bar should be filled based on current HP
    float ratio = (maxHp == 0) ? 0 : (hp * 1f / maxHp);
    app.fill(0, 255, 0);  // Green for health
    app.rect(x, y - 10, width * ratio, 6);
}
}
