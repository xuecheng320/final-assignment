/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import processing.core.PApplet;

/**
 *
 * @author aiden f
 */
public class Task {
    public String title = "Quest";
    public String objective = "";
    public int found = 0;
    public int total = 0;
    public boolean show = true;

    public Task(String title, String objective, int total) {
        this.title = title;
        this.objective = objective;
        this.total = total;
    }

    public void setProgress(int found, int total) {
        this.found = found;
        this.total = total;
    }

    public void addFound() {
        if (found < total) found++;
    }

    public boolean isComplete() {
        return found >= total;
    }

    public void draw(PApplet app) {
        if (!show) return;

        int x = 20, y = 20, w = 420, h = 90;

        app.pushStyle();
        app.noStroke();
        app.fill(0, 170);
        app.rect(x, y, w, h, 12);

        app.fill(255);
        app.textAlign(PApplet.LEFT, PApplet.TOP);

        app.textSize(18);
        app.text(title, x + 14, y + 10);

        app.textSize(14);
        app.text(objective, x + 14, y + 38);

        String prog = "Progress: " + found + " / " + total;
        if (isComplete()) prog += "  (Complete)";
        app.text(prog, x + 14, y + 60);

        app.popStyle();
    }

}
