/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.io.*;
import java.util.*;

/**
 *
 * @author aiden f
 */
public class SaveManager {

    // 存档数据直接用全局变量（不封装成类）
    public static String saveTime = "";
    public static int wave = 1;
    public static int hp = 100;
    public static int maxHp = 100;
    public static int damage = 15;
    public static int lantern = 200;
    public static int score = 0;

    // 判断有没有存档
    public static boolean hasSave() {
        File f = new File("save.txt");
        return f.exists();
    }

    // 保存游戏
    public static void save() throws Exception {
        // 手动拼时间（不用 SimpleDateFormat）
        Date now = new Date();
        saveTime = now.toString(); // 直接用默认格式

        FileWriter fw = new FileWriter("save.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.println("time=" + saveTime);
        pw.println("wave=" + wave);
        pw.println("hp=" + hp);
        pw.println("maxHp=" + maxHp);
        pw.println("damage=" + damage);
        pw.println("lantern=" + lantern);
        pw.println("score=" + score);
        pw.close(); // 手动关闭，不用 try-with-resources
        fw.close();
    }
     public static void load() throws Exception {
        FileReader fr = new FileReader("save.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("time=")) {
                saveTime = line.substring(5);
            } else if (line.startsWith("wave=")) {
                wave = Integer.parseInt(line.substring(5));
            } else if (line.startsWith("hp=")) {
                hp = Integer.parseInt(line.substring(3));
            } else if (line.startsWith("maxHp=")) {
                maxHp = Integer.parseInt(line.substring(6));
            } else if (line.startsWith("damage=")) {
                damage = Integer.parseInt(line.substring(7));
            } else if (line.startsWith("lantern=")) {
                lantern = Integer.parseInt(line.substring(8));
            } else if (line.startsWith("score=")) {
                score = Integer.parseInt(line.substring(6));
            }
        }
        br.close();
        fr.close();
    }

    // 获取保存时间（简单版）
    public static String getSaveTime() {
        if (saveTime.equals("")) return "N/A";
        return saveTime;
    }

}
