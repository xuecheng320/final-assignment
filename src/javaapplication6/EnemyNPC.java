/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
import java.awt.Point;

public class EnemyNPC extends NPC {

    private Enemy hiddenEnemy;

    public EnemyNPC(String name, Point position, String dialogue, Enemy hiddenEnemy) {
        super(name, position, dialogue, hiddenEnemy.getClass().getSimpleName());
        this.hiddenEnemy = hiddenEnemy;
    }

    // 揭示身份，切换到战斗逻辑
    public void revealEnemy() {
        this.reveal(); // 父类方法，把 revealed = true
        // 可以触发隐藏 Enemy 的战斗状态
    }

    public Enemy getHiddenEnemy() {
        return hiddenEnemy;
    }
}
