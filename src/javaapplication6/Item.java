/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

/**
 *
 * @author aiden f
 */
public class Item {

    private String name;
    private String description;
    private int effectValue;
    private String itemType;

    public Item(String name, String description, int effectValue, String itemType) {
        this.name = name;
        this.description = description;
        this.effectValue = effectValue;
        this.itemType = itemType;
    }

    // 使用物品
    public void use(GameObject target) {
        // 具体效果逻辑待实现
        // 例如回血、伤害、触发事件
    }

    // ===== Getter =====
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public String getItemType() {
        return itemType;
    }
}
