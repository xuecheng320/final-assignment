/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *
 * @author 350266246
 */
public class Lantern extends Actor{
    private boolean live =true;
    private int maxhp = 250;

    public Lantern(PApplet app, int x, int y,SpriteSet sprites){
        super(app, 220, 0, 0, x, y,sprites.walk,sprites.stand); //传画图，血量，攻击，防守，x坐标，y坐标，走路图，战力图
    }
    public void takeDmage(int d){
        if(!live) return;
        hp-= d;
    }
    public boolean islive(){
        if(hp <= 0){live =false;hp=0;}
        return live;
    }
    public int gethp(){
        return hp;
    }
    public void update(){};
    
    public void draw(){
        File file = new File("images/lanter.png");//查看是否有文件路径
        if(file.exists()){//如果文件存在的话
        PImage img = app.loadImage("images/lantern.png");//加载图片
        app.image(img, x, y, app.width, app.height);
    }
        int ratio = (hp == 0)? 0 : (int)hp/maxhp;
        app.noStroke();
        app.fill(0,160,0);
        app.rect(x,y,w,y - 10);
        app.fill(0,255,0);
        app.rect(x,y, w * ratio, y - 10);
    }
    
}
