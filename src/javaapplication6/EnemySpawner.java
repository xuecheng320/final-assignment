/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author aiden f
 */


public class EnemySpawner {

    // 四个固定刷怪点（屏幕四角）
    private static int[][] spawnPoints = {
        {40, 40},
        {920, 40},
        {40, 600},
        {920, 600}
    };

    private static Random r = new Random();

    // 根据当前波次刷一个敌人
    // enemies 是 MySketch 里的敌人列表
    public static void spawnEnemy(
            int wave,
            ArrayList<Enemy> enemies
    ) {
        int idx = r.nextInt(spawnPoints.length);
        int x = spawnPoints[idx][0];
        int y = spawnPoints[idx][1];

        Enemy e;

        // 简单波次规则
        if (wave == 1) {
            e = new SlimeEnemy(x, y);
        }
        else if (wave == 2) {
            if (r.nextBoolean()) {
                e = new SlimeEnemy(x, y);
            } else {
                e = new ImpEnemy(x, y);
            }
        }
        else if (wave == 3) {
            e = new ImpEnemy(x, y);
        }
        else if (wave == 4) {
            if (r.nextBoolean()) {
                e = new ImpEnemy(x, y);
            } else {
                e = new BoneWalkerEnemy(x, y);
            }
        }
        else {
            // Final wave：只刷一次 Boss
            if (!hasBoss(enemies)) {
                e = new BossFiend(x, y);
            } else {
                return;
            }
        }

        enemies.add(e);
    }

    // 检查 Boss 是否已经存在
    private static boolean hasBoss(ArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e instanceof BossFiend) {
                return true;
            }
        }
        return false;
    }
}
