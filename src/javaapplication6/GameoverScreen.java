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

public class GameoverScreen {

    private static boolean win = false;

    public static final int NO_ACTION = 0;
    public static final int RETURN_TO_MENU = 1;

    public static void setResult(boolean isWin) {
        win = isWin;
    }

    public static void draw(PApplet p) {
        p.background(0);
        p.fill(255);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);

        p.textSize(48);
        if (win) {
            p.text("You Win!", p.width / 2, 180);
        } else {
            p.text("Game Over", p.width / 2, 180);
        }

        p.textSize(18);
        p.text("Press ENTER to return to menu", p.width / 2, 300);
    }

    // 返回用户意图，由 MySketch 决定如何响应
    public static int handleKey(int keyCode) {
        if (keyCode == PApplet.ENTER || keyCode == PApplet.RETURN) {
            return RETURN_TO_MENU;
        }
        return NO_ACTION;
    }
}
