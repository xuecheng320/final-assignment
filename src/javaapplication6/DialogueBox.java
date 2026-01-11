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


public class DialogueBox {

    // 当前要显示的文字
    private static String text = "";

    // 剩余显示帧数
    private static int timer = 0;

    // 显示一段文字（frames = 显示多久）
    public static void show(String msg, int frames) {
        text = msg;
        timer = frames;
    }

    // 每帧调用，用来减少计时
    public static void update() {
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                text = "";
            }
        }
    }

    // 绘制对话框（底部）
    public static void draw(PApplet p) {
        if (text == null || text.equals("")) return;

        int boxH = 80;

        p.fill(0, 180);
        p.noStroke();
        p.rect(0, p.height - boxH, p.width, boxH);

        p.fill(255);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.textSize(16);
        p.text(text, p.width / 2, p.height - boxH / 2);
    }
}

