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
    private int speed = 2;
     public Player(PApplet app, int x, int y,SpriteSet sprites) {
        super(app, 120, 15, 5, x, y,sprites.walk,sprites.stand); 
    }
     public void setInput(boolean up,boolean down,boolean left,boolean right){
         this.up = up;
         this.down = down;
         this.left = left;
         this.right = right;
     }
     public void update(){
         beginFrame();
         
         int nx = 0;
         int ny = 0;
         if(up) ny -= speed;
         if(down) ny += speed;
         if(left) nx -= speed;
         if(right) nx += speed;
         
         move(nx, ny);//用父类actor的move自动设置moving和方向
         endFrame();//更新frameIndex
         
         if(x < 0) x = 0;//左边
         if(y < 0) y = 0;//上边
         if(x + w > app.width) x = app.width -w;//右边
         if(y + h > app.height) y = app.height -h;//下边
     }
     public void draw(){
         app.image(currentImage(), x, y, w, h);//改成用人物组
     }
     
}
