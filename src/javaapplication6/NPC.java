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
    private int lineIndex;
    private boolean near = false;
    public NPC(PApplet app, int x, int y,SpriteSet sprites,String [] text){
        super(app,1,0,0,x,y,sprites.walk,sprites.stand);
        this.text = text;
    }
    public void startTalking(){
        talking = true;
        lineIndex = 0;
    }
    public boolean isTalking(){
        return talking;
    }
    public void nextTalk(){
        if(!talking) return;
        
        lineIndex ++;
        if(lineIndex >= text.length){
            lineIndex = 0;
            talking = false;
        }
    }
    public String currentLine(String [] text){
        if(!talking || text == null || text.length == 0) return "";
        else return text[lineIndex];
    }
    public void update(){
        beginFrame();
        endFrame();
     }
    public boolean isNear(Actor other,int Range){
        dx = (x + w/2) - (other.x + other.x/2);
        dy = (y + h/2) - (other.y + other.y/2);
        double dist = Math.sqrt( dx * dx + dy * dy);
        if(dist < Range) near = true;
        return near;
    }
}