/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import processing.core.PApplet;
public class Player extends Actor{
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private int speed = 3;
     public Player(PApplet app, int x, int y) {
        super(app, 120, 15, 5, x, y); 
    }
     public void setInput(boolean up,boolean down,boolean left,boolean right){
         this.up = up;
         this.down = down;
         this.left = left;
         this.right = right;
     }
     public void update(){
         int nx = 0;
         int ny = 0;
         if(up) ny -= speed;
         if(down) ny += speed;
         if(left) nx -= speed;
         if(right) nx += speed;
         x+=nx;
         y+=ny;//边缘修正没写if写
     }
     public void draw(){
         app.image(sprite, x, y, w, h);//改成用人物组
     }
     
}
