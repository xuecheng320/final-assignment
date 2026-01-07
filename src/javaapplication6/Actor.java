package javaapplication6;

import processing.core.PApplet;

public abstract class Actor {

    protected PApplet app;

    // 基础属性（所有角色都有）
    protected int hp;
    protected int attack;
    protected int defense;

    // 位置和大小
    protected int x, y;
    protected int w = 64, h = 64;

    public Actor(PApplet app, int hp, int attack, int defense, int x, int y) {
        this.app = app;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.x = x;
        this.y = y;
    }

    // 通用移动（可选用）
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    // 通用碰撞检测“我”和“另一个角色”的矩形有没有撞在一起。
    public boolean intersects(Actor other) {
        return x < other.x + other.w && x + w > other.x &&
               y < other.y + other.h && y + h > other.y;
    }

    // 每个角色必须自己实现的逻辑
    public abstract void update();
}
