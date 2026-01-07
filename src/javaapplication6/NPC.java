/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.awt.Point;
/**
 *
 * @author aiden f
 */


public class NPC {

    // 属性
    private String name;
    private Point position;
    private String dialogue;
    private String hiddenType; // 用于标记是否是魑魅/魍魉
    private boolean revealed;

    // 构造函数
    public NPC(String name, Point position, String dialogue, String hiddenType) {
        this.name = name;
        this.position = position;
        this.dialogue = dialogue;
        this.hiddenType = hiddenType;
        this.revealed = false;
    }

    // ===== 方法 =====

    public void interact(Player player) {
        // 与玩家互动逻辑待实现
        // 可以触发对话、任务或者战斗
    }

    public void reveal() {
        // 揭示隐藏类型逻辑
        revealed = true;
    }

    // ===== Getter / Setter（可选加分） =====

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getDialogue() {
        return dialogue;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public String getHiddenType() {
        return hiddenType;
    }
}

