/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.awt.Point;

/**
 *
 * @author aiden f
 */

public class Enemy {

    // 属性
    protected int hp;
    protected int attack;
    protected int defense;
    protected Point position;

    // 构造函数
    public Enemy(int hp, int attack, int defense, Point startPosition) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.position = startPosition;
    }

    // ===== 方法 =====

    public void move() {
        // 移动逻辑待实现
    }

    public void attack(Player target) {
        // 攻击玩家逻辑待实现
    }

    public void useSpecial(Player target) {
        // 特殊技能逻辑待实现
    }

    // ===== Getter / Setter （可选，加分） =====

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
