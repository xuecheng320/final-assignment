/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// SaveManager
// Requirement: only save the last game run data for History screen display
// Save location: history.txt in the same directory (plain text for easy inspection)
// Save content: time seconds win difficulty
public class SaveManager {

    private static final String FILE = "history.txt";

    public static String saveTime = "";
    public static int lastSeconds = 0;
    public static boolean lastWin = false;

    /**
     * Check if history file exists (used by History menu to determine if clickable)
     * @return 
     */
    public static boolean hasHistory() {
        return new File(FILE).exists();
    }

   
    /**
     * Record the last game run
     * @param elapsedMs duration of this run in milliseconds (calculated using millis() in MySketch)
     * @param win whether won
     */
    public static void record(long elapsedMs, boolean win) {
        try {
             //ai:gemini
            //prompt: how do u get current time in java
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            saveTime = now.format(TIME_FMT);
            //end
            lastSeconds = (int) (elapsedMs / 1000L);
            lastWin = win;

            FileWriter fw = new FileWriter(FILE);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("time=" + saveTime);
            pw.println("seconds=" + lastSeconds);
            pw.println("win=" + lastWin);

            pw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    /**
     * Load last game run from h
     * @throws java.lang.Exception
     */
    public static void loadHistory() throws Exception {
        FileReader fr = new FileReader(FILE);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("time=")) {
                saveTime = line.substring(5);
            } else if (line.startsWith("seconds=")) {
                lastSeconds = Integer.parseInt(line.substring(8));
            } else if (line.startsWith("win=")) {
                lastWin = Boolean.parseBoolean(line.substring(4));
            }
        }

        br.close();
        fr.close();
    }
}