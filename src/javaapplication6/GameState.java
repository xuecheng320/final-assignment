package javaapplication6;
import java.util.List;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aiden f
 */
public class GameState {
    private int faithValue;
    private Area currentArea;
    private Player player;
    private List<Enemy> enemies;
    private List<NPC> npcList;

    public GameState(Player player, Area startArea) {
        this.player = player;
        this.currentArea = startArea;
        this.faithValue = 100; 
    }

    public int getFaithValue() {
        return faithValue;
    }

    public void changeFaith(int amount) {
        faithValue += amount;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area area) {
        this.currentArea = area;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<NPC> getNpcList() {
        return npcList;
    }

    public void setNpcList(List<NPC> npcList) {
        this.npcList = npcList;
    }
}
