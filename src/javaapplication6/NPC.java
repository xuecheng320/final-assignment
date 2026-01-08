package javaapplication6;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 350266246
 */
public class NPC extends Actor{
    
    private String [] text;
    private boolean talking = false;//检测npc有没有开始说话
    private int dx;//用来检测和玩家的x距离
    private int dy;//用来检测和玩家的y距离
    private int w;
    private int h;
    private int lineIndex;
    public NPC(PApplet app, int x, int y,SpriteSet sprites,String [] text,int w,int h){
        super(app,1,0,0,x,y,sprites.walk,sprites.stand);
        this.w = w;
        this.h = h;
        this.text = text;
    }
    public void starttalking(){
        talking = true;
        lineIndex = 0;
    }
    public boolean istalking(){
        if(talking) return true;
    }
    public void nextTalk(){
        if(!talking) return;
        
        lineIndex ++;
        if(lineIndex > text.length){
            lineIndex = 0;
            talking = false;
        }
    }
        
     }
