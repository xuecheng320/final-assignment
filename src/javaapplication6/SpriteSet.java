/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

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
    
}
