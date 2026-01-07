/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
public abstract class Skill {

    // 属性
    protected String name;
    protected String description;
    protected int cooldown; // 冷却时间，单位可自行定义

    // 构造函数
    public Skill(String name, String description, int cooldown) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
    }

    // ===== 抽象方法 =====
    // 每个技能必须实现 activate，作用于 GameObject
    public abstract void activate(GameObject target);

    // ===== Getter / Setter 可选 =====
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldown() {
        return cooldown;
    }
}
