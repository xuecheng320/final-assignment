/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author aiden f
 */
public class Player {

    private Point position;
    private int hp;
    private int attack;
    private List<Item> inventory;
    private List<Skill> skills;

    public Player(Point startPosition) {
        this.position = startPosition;
        this.hp = 100;
        this.attack = 10;
        this.inventory = new ArrayList<>();
        this.skills = new ArrayList<>();
    }

    public void move(String direction) {
        // movement logic to be implemented
    }

    public void attack(Enemy target) {
        // combat logic to be implemented
    }

    public void useItem(Item item) {
        // item usage logic to be implemented
    }

    public void useSkill(Skill skill, GameObject target) {
        // skill logic to be implemented
    }
}

