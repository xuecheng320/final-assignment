/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author aiden f
 */
public class Area {

    private String name;
    private String description;
    private int width;
    private int height;
    private List<Enemy> enemies;
    private List<NPC> npcList;

    public Area(String name, String description, int width, int height) {
        this.name = name;
        this.description = description;
        this.width = width;
        this.height = height;
        this.enemies = new ArrayList<>();
        this.npcList = new ArrayList<>();
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void addNPC(NPC n) {
        npcList.add(n);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<NPC> getNpcList() {
        return npcList;
    }
}

