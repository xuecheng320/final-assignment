/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import processing.core.PApplet;

/**
 *
 * @author aiden f
 */

public class HudUI {

    // 所有数据通过参数传入，不再依赖 MySketch
    public static void drawHUD(
        PApplet p,
        int wave,
        int playerHP, int playerMaxHP,
        int lanternHP, int lanternMaxHP,
        int enemiesAlive,
        int killed, int totalToSpawn
    ) {
        p.fill(0, 160);
        p.noStroke();
        p.rect(10, 10, 260, 120);

        p.fill(255);
        p.textAlign(PApplet.LEFT, PApplet.TOP);
        p.textSize(14);

        p.text("Wave: " + wave, 20, 20);
        p.text("Player HP: " + playerHP + " / " + playerMaxHP, 20, 45);
        p.text("Lantern HP: " + lanternHP + " / " + lanternMaxHP, 20, 70);
        p.text("Enemies: " + killed + " / " + totalToSpawn + "  (Alive: " + enemiesAlive + ")", 20, 95);
    }

    public static void drawCenterMessage(PApplet p, String msg) {
        if (msg == null || msg.isEmpty()) return;

        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.textSize(28);
        p.fill(0, 180);
        p.rect(p.width / 2 - 220, p.height / 2 - 50, 440, 100);

        p.fill(255);
        p.text(msg, p.width / 2, p.height / 2);
    }
}
