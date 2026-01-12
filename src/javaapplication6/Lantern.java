/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *
 * @author 350266246
 */
public class Lantern extends Actor{
    private boolean live =true;

    public Lantern(PApplet app, int x, int y,SpriteSet sprites){
        super(app, 220, 0, 0, x, y,sprites.walk,sprites.stand); //传画图，血量，攻击，防守，x坐标，y坐标，走路图，战力图
    }
    public void takeDmage(int d){
        if(!live) return;
        hp-= d;
    }
    public boolean islive(){
        if(hp <= 0){live =false;}
        return live;
    }
    public void update(){};
    
    
}
