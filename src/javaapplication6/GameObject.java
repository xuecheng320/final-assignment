package javaapplication6;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aiden f
 */
import java.awt.Point;

public abstract class GameObject {

    // 公共属性
    protected int hp;
    protected Point position;

    // 构造函数
    public GameObject(int hp, Point position) {
        this.hp = hp;
        this.position = position;
    }

    // ===== 公共方法（空实现，可由子类覆盖） =====

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getHp() {
        return hp;
    }

    // 可以在子类中覆盖 move、attack、interact 等
}

