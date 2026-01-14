/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author aiden f
 */

public class Enemy extends Actor{
    //传画图，血量，攻击，防守，x坐标，y坐标，走路图，战力图
    public Enemy(PApplet app, int hp, int attack, int x, int y,SpriteSet sprites) {
        super(app,hp,attack,0,x,y,sprites.walk,sprites.stand);
    }
    public void move() {
        if( y + h/2  == app.height / 2 && x == 0) x +=5;
        if( y + h/2 == app.height / 2 && x == app.width) x -= 5;
        if( x + w/2 == app.width / 2 && y == 0) y += 5;
        if( x + w/2 == app.width / 2 && x == app.height) x -= 5;
        
    }

    public void attack(Player target) {
        // 攻击玩家逻辑待实现
    }

//    public boolean isArrive(){
//        return (x == app.width/2 && y == app.height/2);
//        }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    public void update(){}

}