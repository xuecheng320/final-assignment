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


public class ShopMenu {

    // 画升级界面（波次结束后显示）
    public static void drawShop(PApplet p) {
        p.background(20);
        p.fill(255);
        p.textAlign(PApplet.CENTER);
        p.textSize(40);
        p.text("Upgrade Time", p.width / 2, 140);

        p.textSize(18);
        p.text("Press 1: +2 Attack", p.width / 2, 260);
        p.text("Press 2: +20 Max HP (Heal Full)", p.width / 2, 300);
        p.text("Press 3: Repair Lantern +40 HP", p.width / 2, 340);

        p.textSize(14);
        p.text("Choose ONE upgrade to start the next wave.", p.width / 2, 420);
    }

    // 处理升级按键（直接调用 MySketch）
    public static void keyPressed(PApplet p, char key) {

        if (key == '1') {
            MySketch.upgradeAttack();   // 你在 MySketch 写这个静态方法
            MySketch.nextWave();        // 进入下一波
        }
        else if (key == '2') {
            MySketch.upgradeHp();       // +20 Max HP and heal full
            MySketch.nextWave();
        }
        else if (key == '3') {
            MySketch.repairLantern();   // +40 lantern HP
            MySketch.nextWave();
        }
    }
}
