/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author 350266246
 */
public class SaveManager {
    private static final String FILE = "save.txt";
    private boolean lastwin = false;//储存上把输还是赢
    private String saveTime = "";//保存上把时间
    private int lastDiffculty;
    public static boolean hasHistory(){
        return (new File(FILE)).exists();
    }
    
    public void Save(boolean win,String time,int diffculty){
        lastwin = win;
        //AI: gemini
        //prompt:how you get current time in java
        LocalDateTime now = LocalDateTime.now();
        //end
        
        //AI：gemini
        //prompt:How to save time as a string
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         saveTime = now.format(formatter);
        //end
        lastDiffculty = diffculty;
        try{
        FileWriter fw = new FileWriter("save.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.println("win=" + lastwin);
        pw.println("time=" + saveTime);
        pw.println("diffculty=" + lastDiffculty);
        }catch(IOException ex){
            System.err.print(ex);
        }
}
    public void loadHistory() throws Exception{
        FileReader fr = new FileReader("save.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br) != null){
        }
    }
}