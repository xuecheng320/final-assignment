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


public class MainMenu {
    public static void drawMenu(PApplet p) {
        p.background(15);
        p.fill(255);
        p.textAlign(PApplet.CENTER);
        p.textSize(48);
        p.text("Lone Village Vigil", p.width/2, 140);
        p.textSize(18);
        p.text("Press 1: New Game", p.width/2, 270);
        
        // 直接判断文件是否存在（硬编码）
        if (new java.io.File("save.txt").exists()) {
            p.text("Press 2: Continue", p.width/2, 310);
        } else {
            p.text("Press 2: No Save", p.width/2, 310);
        }
        
        p.text("Press 3: Quit", p.width/2, 350);
    }

    // 直接在菜单里处理逻辑（不返回状态）
    public static void keyPressed(PApplet p, char key) {
        if (key == '1') {
            MySketch.startNewGame(); // 假设 MySketch 有这个静态方法
        } else if (key == '2') {
            if (new java.io.File("save.txt").exists()) {
                MySketch.loadGame();
            }
        } else if (key == '3') {
            p.exit();
        }
    }
}
