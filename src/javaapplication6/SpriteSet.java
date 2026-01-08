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
 * SpriteSet 是角色动画的“图片仓库”。
 * 这里集中加载所有角色走路用的图片（上下左右），
 * 图片只在这里 load 一次，避免每个 Player / NPC / Enemy 都重复加载。
 *
 * PImage walk 是一个二维数组：walk[方向][帧]，
 * 比如 walk[DOWN][2] 表示“向下走的第 3 帧”。
 *
 * 使用时，角色类只需要根据当前方向 dir 和动画帧 frameIndex，
 * 在 draw() 里直接画：
 * app.image(SpriteSet.walk[dir][frameIndex], x, y, w, h);
 *
 * 记住：这里只管图片，不管人物怎么动、不管输入、不管 AI。
 * 这个类只在 MySketch.setup() 里调用一次 load()。
 */

public class SpriteSet {
    
    //每个方向的帧数
    public int walkFrames = 4;
    public int standFrames = 4;
    public int attackFrames = 6;
    
    //由load传入数据
    private int CELL_H;
    private int CELL_W;
    
    private PApplet app;
    PImage walk [][];
    PImage stand [][];
    public void load(PApplet app,int CELL_W,int CELL_H){
       this.CELL_H = CELL_H;
       this.CELL_W= CELL_W;
       
       //获取站立图
       walk = cutSheet(app,"images/Character_Walk.png",walkFrames);
       //获取站立图
       stand = cutSheet(app,"images/Character_Idle.png",standFrames);
    }
    private PImage[][] cutSheet(PApplet app,String file,int framesPerDir){
        PImage sheet =app.loadImage(file);
        if(sheet == null)return null;
        
        int cols = sheet.width / CELL_W;
        int rows = sheet.height / CELL_H;
        
        int n = Math.min(framesPerDir, cols);//判断实际帧数和期盼的一样
        PImage[][] out = new PImage[4][n];
        //人物图每行方向
        int rowLEFT = 0;
        int rowRIGHT = 1;
        int rowUP = 2;
        int rowDOWN = 3;
        for(int i = 0;i<n;i++){//(x,y,w,h)以x和y为左上角，切w*h大小的图片
            out[Actor.LEFT][i]  = sheet.get(i * CELL_W, rowLEFT  * CELL_H, CELL_W, CELL_H);
            out[Actor.RIGHT][i] = sheet.get(i * CELL_W, rowRIGHT * CELL_H, CELL_W, CELL_H);
            out[Actor.UP][i]    = sheet.get(i * CELL_W, rowUP    * CELL_H, CELL_W, CELL_H);
            out[Actor.DOWN][i]  = sheet.get(i * CELL_W, rowDOWN  * CELL_H, CELL_W, CELL_H);
        }
        return out;
    }
        
    
}
