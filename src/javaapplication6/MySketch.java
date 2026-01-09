/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.awt.Point;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *
 * @author aiden f
 * 还没画背景，在setup加载背景图
 * 然后在draw使用image
 */
public class MySketch extends PApplet{
    private Player player;
    private NPC npc1;
    private SpriteSet spritesPlayer;
    private SpriteSet spritesNpc1;
    private boolean up,down,left,right;
    String text [] = {"hello"};
    public void settings(){
        size(1500,1200);
        }
    public void setup(){
    frameRate(60);
    imageMode(CORNER);
    spritesPlayer = new SpriteSet();
//    spritesNpc1 = new SpriteSet();
//    spritesNpc1.load(this, 40, 48,null,null);//走路，站立，攻击
    spritesPlayer.load(this, 40, 48,"images/Character_Walk.png","images/Character_Idle.png");
    player = new Player(this,200,200,spritesPlayer);
    npc1 = new NPC(this,300,300,spritesNpc1,text);
    }
    public void draw(){
        background(0);
        player.setInput(up, down, left, right);
        player.update();
        player.draw();
    }
    @Override
     public void keyPressed() {
        if (key == 'w' || key == 'W') up = true;
        if (key == 's' || key == 'S') down = true;
        if (key == 'a' || key == 'A') left = true;
        if (key == 'd' || key == 'D') right = true;
        if(key == 'e' || key == 'E'){
            if(player.intersects(npc1))
                if(npc1.isTalking())npc1.startTalking();
                else npc1.nextTalk();
    }
     }

    @Override
    public void keyReleased() {
        if (key == 'w' || key == 'W') up = false;
        if (key == 's' || key == 'S') down = false;
        if (key == 'a' || key == 'A') left = false;
        if (key == 'd' || key == 'D') right = false;
    }
    

}
