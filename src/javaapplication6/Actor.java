package javaapplication6;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Actor {

    protected PApplet app;
    //方向常量全局通用
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    // 基础属性（所有角色都有）
    protected int hp;
    protected int attack;
    protected int defense;

    // 位置和大小
    protected int x, y;
    protected int w = 64, h = 64;
    
    //朝向当前朝向
    protected int dir = DOWN;
    protected boolean moving = false;//是否移动了 
    
    //动画，因为是继承所有物体可以传自己的图片组
    protected PImage walk [][];
    protected PImage stand [][];//站立图片
    protected int frameIndex =0;//指针
    protected int frameCounter = 0;//计数器
    protected int frameDelay = 6;//每帧延迟
    //传画图，血量，攻击，防守，x坐标，y坐标，走路图，战力图
    public Actor(PApplet app, int hp, int attack, int defense, int x, int y,PImage walk [][],PImage stand [][]) {
        this.app = app;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.x = x;
        this.y = y;
        this.walk = walk;
        this.stand = stand;
    }
    //每一帧的开始
    protected void beginFrame(){
        moving = false;
    }
    
    
    //处理站立，移动动画
protected void endFrame() {
    frameCounter++;

    if (frameCounter >= frameDelay) {
        frameCounter = 0;

        if (moving) {
            frameIndex = (frameIndex + 1) % walk[dir].length;
        } else {
            frameIndex = (frameIndex + 1) % stand[dir].length; // stand 是二维 idle 动画
        }
    }
}

    
    //根据更新的frameindex找到图片 
    protected PImage currentImage(){
        return moving?walk[dir][frameIndex]:stand[dir][frameIndex];
        }
    // 通用移动
    protected void move(int nx, int ny) {
        if(nx == 0 && ny == 0) return;
        
        moving = true;
        
        //判断方向
        if(nx < 0)dir  = LEFT;
        else if(nx > 0)dir = RIGHT;
        else if(ny < 0)dir = UP;
        else if(ny > 0)dir = DOWN;
        
        x += nx;
        y += ny;
        
    }

    // 通用碰撞检测“我”和“另一个角色”的矩形有没有撞在一起。
    public boolean intersects(Actor other) {
        return x < other.x + other.w && x + w > other.x &&
               y < other.y + other.h && y + h > other.y;
    }

    // 每个角色必须自己实现的逻辑
    public abstract void update();
}
