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
    private boolean up,down,left,right;
    public void settings(){
        size(800,600);
        }
    public void setup(){
    frameRate(60);
    imageMode(CORNER);
    player = new Player(this,200,200);
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
    }

    @Override
    public void keyReleased() {
        if (key == 'w' || key == 'W') up = false;
        if (key == 's' || key == 'S') down = false;
        if (key == 'a' || key == 'A') left = false;
        if (key == 'd' || key == 'D') right = false;
    }

}
