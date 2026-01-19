/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

// Main game loop
// Uses Processing PApplet
// Handles game state updates drawing input and enemy spawning
public class MySketch extends PApplet {

    // Game states: what screen we're currently showing
    public static final int STATE_MENU = 0;
    public static final int STATE_INTRO = 1;
    public static final int STATE_PLAY = 2;
    public static final int STATE_OVER = 3;
    public static final int STATE_HISTORY = 4;

    // Current game state (starts at menu)
    private int state = STATE_MENU;

    // World/map size in pixels (larger than window for future camera if needed)
    private final int worldW = 1600;
    private final int worldH = 900;

    // Only one difficulty mode is used in this version
    private static final int DIFFICULTY_FIXED = 2;

    // How long the player must survive to win (in seconds)
    private int defendSeconds = 80;

    // Timer tracking for gameplay
    private long elapsedMs = 0;      // Total time played this run
    private long lastMillis = 0;     // Last frame timestamp

    // Enemy spawning control
    private int spawnTimer = 0;      // Countdown until next enemy can spawn
    private int spawnDelay = 65;     // Frames between spawns (slower = easier)
    private int enemyTarget = 6;     // Max enemies allowed on screen at once

    // Graphics assets
    private PImage bg;               // Background image
    private SpriteSet playerSprites;  // Player animation frames
    private SpriteSet enemySprites;   // Melee enemy animation frames
    private SpriteSet rangedSprites;  // Ranged enemy animation frames

    // Projectile images
    private PImage enemyBulletImg;   // Bullet fired by ranged enemies

    // Main game characters
    private Player player;           // The player character
    private Lantern lantern;         // The object to protect

    // Dynamic game objects (lists grow and shrink during play)
    private final ArrayList<Enemy> enemies = new ArrayList<>();        // All active enemies
    private final ArrayList<Arrow> arrows = new ArrayList<>();         // Player's arrows
    private final ArrayList<EnemyBullet> eBullets = new ArrayList<>(); // Enemy bullets
    private final ArrayList<Obstacle> obs = new ArrayList<>();         // Static map obstacles

    // Road layout parameter (width of the open cross-shaped path in the center)
    private final int ROAD_W = 440;

    // Player input state (updated in keyPressed/keyReleased)
    private boolean up, down, left, right;
    private boolean sprint;

    // Intro sequence assets
    private PImage[] introImgs;      // Background images for each intro page
    private String[] introText;      // Story text for each intro page
    private int introIndex = 0;      // Which intro page we're on

    // Ending screen images
    private PImage endWinImg;        // Image shown on victory
    private PImage endFailImg;       // Image shown on defeat

    // Result of the current run (used in game over screen)
    private boolean win = false;

    // Random number generator for spawning
    private Random r = new Random();

    /**
     * Set window size
     */
    public void settings() {
        size(1600, 900);
    }

    /**
     * Initialize game resources
     */
    public void setup() {
        frameRate(60);
        imageMode(CORNER);

        playerSprites = new SpriteSet();
        playerSprites.loadPlayer(this, 32, 32,"images/player_walk_sheet.png","images/player_sword_sheet.png",3);//PApplet app, int cellW, int cellH,String walkSheet, String swordAttackSheet,int idleFrameIndex

        enemySprites = new SpriteSet();
        // Melee enemy sprites (now using two separate files)
        enemySprites.loadEnemy(this, 32, 32,"images/green_slime_walk.png","images/green_slime_attck.png");//PApplet app, int cellW, int cellH,String sheetFile, int xOffset,int walkRow, int attackRow

        rangedSprites = new SpriteSet();
        rangedSprites.loadEnemy(this, 32, 36,"images/wizard.png");//PApplet app, int cellW, int cellH,String sheetFile,int attackStart, int attackCount,int walkStart, int walkCount

        enemyBulletImg = loadImage("images/enemy_bullet.png");
        Arrow.setSprite(loadImage("images/player_arrow.png"));

        introImgs = new PImage[]{
            loadImage("images/intro1.png"),
            loadImage("images/intro2.png"),
            loadImage("images/intro3.png"),
            loadImage("images/intro4.png")
        };

        endWinImg = loadImage("images/ending_win.png");
        endFailImg = loadImage("images/ending_fail.png");

        introText = new String[]{
            "Long ago, the world was nothing but endless water.\nThere was no land to stand on - only waves beneath a silent sky.",
            "From the Sky World, Skywoman fell through the open air.\nA flock of birds rushed below her and carried her gently,\nso she would not vanish into the sea.",
            "The Great Turtle rose from the deep and offered its back.\nOtter, beaver, fish, and muskrat dove into the dark ocean\nto search for the first soil.",
            "They returned with a handful of mud. Skywoman spread it across the turtle's back\nand planted the seed of the Tree of Life. And so, land was born.\nThis place became known as Turtle Island.\n\nTonight, balance breaks and restless spirits wander. Your magic is not to destroy -\nit is to guide lost souls back into the order of life."
        };

        state = STATE_MENU;
        DialogueBox.clear();
    }

    /**
     * Main draw loop
     */
   public void draw() {
    // Update dialogue box every frame (handles auto-close timer)
    DialogueBox.update();

    // Draw the correct screen based on current game state
    if (state == STATE_MENU) {
        MainMenu.draw(this, SaveManager.hasHistory());
        return;
    }
    if (state == STATE_HISTORY) {
        HistoryScreen.draw(this, SaveManager.hasHistory());
        return;
    }
    if (state == STATE_INTRO) {
        drawIntro();
        return;
    }
    if (state == STATE_OVER) {
        drawOver();
        return;
    }

    // Main gameplay: update logic then draw everything
    updatePlay();
    drawPlay();
}

/**
 * Handle key press events
 */
    public void keyPressed() {

        // ESC always goes back to menu (or quits from menu)
        if (key == ESC) {
            key = 0;  // Prevent Processing from closing window

            if (state == STATE_MENU) {
                exit();  // Quit from main menu
            } else {
                // Record as a loss if quitting during gameplay or intro
                if (state == STATE_PLAY || state == STATE_INTRO) {
                    SaveManager.record(elapsedMs, false);
                }
                state = STATE_MENU;

                // Reset movement so player doesn't keep moving after returning
                up = down = left = right = false;

                // Clear any active dialogue
                DialogueBox.clear();
            }
            return;
        }

        // Menu navigation
        if (state == STATE_MENU) {
            int action = MainMenu.handleKey(key, keyCode, SaveManager.hasHistory());

            if (action == MainMenu.START_GAME) {
                startNewGame();
            } else if (action == MainMenu.HISTORY) {
                try {
                    SaveManager.loadHistory();
                } catch (Exception e) {
                    // Ignore if history file is missing or corrupted
                }
                state = STATE_HISTORY;
            } else if (action == MainMenu.QUIT) {
                exit();
            }
            return;
        }

        // History screen: no keys do anything (ESC handled above)
        if (state == STATE_HISTORY) {
            return;
        }

        // Game over screen: J returns to menu
        if (state == STATE_OVER) {
            if (key == 'j' || key == 'J') {
                state = STATE_MENU;
            }
            return;
        }

        // Intro screen: only J advances to next page
        if (state == STATE_INTRO) {
            if (key == 'j' || key == 'J') {
                nextIntro();
            }
            return;
        }

        // During gameplay, if dialogue is showing, ENTER clears it
        if (state == STATE_PLAY && DialogueBox.hasText()) {
            if (keyCode == ENTER || keyCode == RETURN) {
                DialogueBox.clear();
            }
            return;
        }

        // Player movement controls
        if (key == 'w' || key == 'W') up = true;
        if (key == 's' || key == 'S') down = true;
        if (key == 'a' || key == 'A') left = true;
        if (key == 'd' || key == 'D') right = true;

        // Hold SHIFT to sprint
        if (keyCode == SHIFT) sprint = true;

        // K toggles between sword and bow
        if (key == 'k' || key == 'K') {
            player.setWeapon(player.weapon == Player.WEAPON_SWORD ? Player.WEAPON_BOW : Player.WEAPON_SWORD);
        }

        // Hold J to attack (direction locks on first press)
        if (key == 'j' || key == 'J') {
            player.startHoldAttack();
        }
    }

/**
 * Handle key release events
 */
public void keyReleased() {
    // Stop movement when keys are released
    if (key == 'w' || key == 'W') up = false;
    if (key == 's' || key == 'S') down = false;
    if (key == 'a' || key == 'A') left = false;
    if (key == 'd' || key == 'D') right = false;

    // Stop sprinting when SHIFT is released
    if (keyCode == SHIFT) sprint = false;

    // Stop holding attack when J is released
    if (key == 'j' || key == 'J') {
        if (state == STATE_PLAY && player != null) player.stopHoldAttack();
    }
}

/**
 * Handle mouse press events
 */
public void mousePressed() {
    // No mouse interaction during intro
    if (state == STATE_INTRO) {
        return;
    }

    // Click to close dialogue during gameplay
    if (state == STATE_PLAY && DialogueBox.hasText()) {
        DialogueBox.clear();
    }
}

    //ai:chatgpt
    //prompt: Processing how to make enemy shoot bullets toward player?
    //prompt: Why do my enemy bullets fly faster when farther away? Fix my code.
    /**
     * Spawn one enemy from a random edge
     */
    private void spawnOneEnemy() {
        float x = 0, y = 0;
        int side = r.nextInt(4);

        if (side == 0) {
            x = worldW / 2f + r.nextInt(81) - 40;
            y = 10;
        } else if (side == 1) {
            x = worldW / 2f + r.nextInt(81) - 40;
            y = worldH - 60;
        } else if (side == 2) {
            x = 10;
            y = worldH / 2f + r.nextInt(81) - 40;
        } else {
            x = worldW - 60;
            y = worldH / 2f + r.nextInt(81) - 40;
        }

        int roll = r.nextInt(20);
        
        boolean allowRangedHere = side != 3;
        //ai:chatgpt
        //prompt:How to generate certain monsters with probability?
        if (allowRangedHere && roll < 6) {
            int rhp = 18;
            RangedEnemy re = new RangedEnemy(this, x, y, rangedSprites, rhp, enemyBulletImg);
            enemies.add(re);
            //end
        } else {
            Enemy e = new Enemy(this, x, y, enemySprites, 44);
            e.speed = 0.95f;
            e.atk = 3;
            e.playerCooldownMax = 30;
            enemies.add(e);
        }
    }
    //end

/**
 * Go to next intro page or start gameplay
 */
private void nextIntro() {
    // Move to the next story page
    introIndex++;

    // If we've shown all intro pages, start the actual game
    if (introIndex >= introText.length) {
        DialogueBox.clear();
        state = STATE_PLAY;
        return;
    }

    // Show the next story text (0 means stay until player presses J again)
    DialogueBox.show(introText[introIndex], 0);
}

/**
 * Draw intro screen with background image and dialogue
 */
private void drawIntro() {
    // Black background in case no image loads
    background(0);

    // Draw the background image for the current intro page (if available)
    if (introImgs != null && introIndex < introImgs.length) {
        PImage img = introImgs[introIndex];
        if (img != null) image(img, 0, 0, width, height);
    }

    // Overlay the story text on top
    DialogueBox.draw(this);
}

/**
 * Start a new game session
 */
private void startNewGame() {
    // Reset game settings to default balanced values
    defendSeconds = 80;
    spawnDelay = 65;
    enemyTarget = 6;

    // Clear all previous game objects
    enemies.clear();
    arrows.clear();
    eBullets.clear();
    obs.clear();

    // Create player near bottom center of screen
    player = new Player(this, width / 2f, height / 2f + 160, playerSprites);
    player.applyDifficulty(DIFFICULTY_FIXED);
    player.setWeapon(Player.WEAPON_SWORD);

    // Place lantern at world center (not screen center)
    lantern = new Lantern(this, worldW / 2f - 24, worldH / 2f - 24);

    // Build the cross-shaped road layout
    makeObstacles();

    // Reset timers
    elapsedMs = 0;
    lastMillis = 0;

    // Start with first intro page
    introIndex = 0;
    state = STATE_INTRO;
    DialogueBox.show(introText[0], 0);
}

/**
 * Create obstacles to form cross-shaped road
 */
private void makeObstacles() {
    obs.clear();

    float cx = worldW / 2f;
    float cy = worldH / 2f;
    float half = ROAD_W / 2f;

    // Four corner blocks create a "+" shaped open area in the center
    obs.add(new Obstacle(0, 0, cx - half, cy - half));
    obs.add(new Obstacle(cx + half, 0, worldW - (cx + half), cy - half));
    obs.add(new Obstacle(0, cy + half, cx - half, worldH - (cy + half)));
    obs.add(new Obstacle(cx + half, cy + half, worldW - (cx + half), worldH - (cy + half)));
}

/**
 * Update all gameplay logic
 */
private void updatePlay() {
    // Delta time tracking for consistent timing
    long now = millis();
    if (lastMillis == 0) lastMillis = now;
    long delta = now - lastMillis;
    lastMillis = now;

    // Freeze game when dialogue is showing (timer stops, input disabled)
    boolean freeze = DialogueBox.hasText();
    if (!freeze) elapsedMs += delta;

    if (freeze) {
        player.setInput(false, false, false, false);
        player.setSprint(false);
        return;
    }

    // Apply current input to player
    player.setInput(up, down, left, right);
    player.setSprint(sprint);

    // Give player world info for boundary and collision checks
    player.setWorld(worldW, worldH, obs);

    // Update player movement and combat
    player.update();
    player.updateCombat(enemies, arrows);

    // Spawn enemies if under limit and timer allows
    if (spawnTimer > 0) spawnTimer--;
    //ai:chatgpt
    //prompt: How to randomly spawn enemies from map edges in Processing?
    if (enemies.size() < enemyTarget && spawnTimer == 0) {
        spawnOneEnemy();
        spawnTimer = spawnDelay;
    }
    //end

    // Update all enemies (melee and ranged use different AI)
    for (int i = enemies.size() - 1; i >= 0; i--) {
        Enemy e = enemies.get(i);
        if (e instanceof RangedEnemy) {
            ((RangedEnemy) e).updateRanged(lantern, player, obs, worldW, worldH, eBullets);
        } else {
            e.updateAI(lantern, player, obs, worldW, worldH);
        }
    }

    // Update player arrows and check enemy hits
    for (int i = arrows.size() - 1; i >= 0; i--) {
        Arrow a = arrows.get(i);
        a.update(worldW, worldH);

        for (int j = enemies.size() - 1; j >= 0; j--) {
            Enemy e = enemies.get(j);
            if (a.x >= e.x && a.x <= e.x + e.width && a.y >= e.y && a.y <= e.y + e.height) {
                e.hp -= a.dmg;
                a.alive = false;

                if (e.hp <= 0) {
                    enemies.remove(j);
                    player.kills++;
                }
                break;
            }
        }

        if (!a.alive) arrows.remove(i);
    }

    // Update enemy bullets and check collisions
    for (int i = eBullets.size() - 1; i >= 0; i--) {
        EnemyBullet b = eBullets.get(i);
        b.update(worldW, worldH);

        // Hit player
        if (b.x >= player.x && b.x <= player.x + player.width && b.y >= player.y && b.y <= player.y + player.height) {
            player.hp -= b.dmg;
            b.alive = false;
        }

        // Hit lantern
        if (b.alive && b.x >= lantern.x && b.x <= lantern.x + lantern.width && b.y >= lantern.y && b.y <= lantern.y + lantern.height) {
            lantern.damage(b.dmg);
            b.alive = false;
        }

        // Stop on obstacles
        if (b.alive) {
            for (Obstacle o : obs) {
                if (o.containsPoint(b.x, b.y)) {
                    b.alive = false;
                    break;
                }
            }
        }

        if (!b.alive) eBullets.remove(i);
    }

    // Check lose condition: either player or lantern dies
    if (lantern.hp <= 0 || player.hp <= 0) {
        win = false;
        state = STATE_OVER;
        SaveManager.record(elapsedMs, false);
        return;
    }

    // Check win condition: survived long enough
    if (elapsedMs >= defendSeconds * 1000L) {
        win = true;
        state = STATE_OVER;
        SaveManager.record(elapsedMs, true);
        return;
    }
}

   /**
 * Draw gameplay screen
 */
private void drawPlay() {
    // Black background
    background(0);

    // Draw static obstacles first (so characters appear on top)
    for (Obstacle o : obs) o.draw(this);

    // Draw all game objects in order: lantern, enemies, projectiles, player
    lantern.draw();
    for (Enemy e : enemies) e.draw();
    for (Arrow a : arrows) a.draw(this);
    for (EnemyBullet b : eBullets) b.draw(this);
    player.draw();

    // HUD background panel (top-left corner)
    fill(0, 170);
    rect(10, 10, 560, 150, 8);

    // HUD text styling
    fill(255);
    textAlign(LEFT, TOP);
    textSize(16);

    // Calculate remaining time (never go below zero)
    int secLeft = defendSeconds - (int) (elapsedMs / 1000L);
    if (secLeft < 0) secLeft = 0;

    // Display game info
    text("Time Left: " + secLeft + "s", 20, 12);
    text("Player HP: " + player.hp + "/" + player.maxHp + "  ATK: " + player.atk, 20, 32);
    text("Lantern HP: " + lantern.hp + "/" + lantern.maxHp, 20, 54);

    String wpn = (player.weapon == Player.WEAPON_SWORD) ? "Sword" : "Bow";
    text("Weapon: " + wpn, 20, 76);
    text("Press K to switch weapon", 20,98);
    text("WASD Move | Hold SHIFT Sprint | Hold J Attack | ESC Menu", 20, 120);

    // Overlay dialogue box if active
    DialogueBox.draw(this);
}

/**
 * Draw game over or victory screen
 */
private void drawOver() {
    // Black background as fallback
    background(0);

    // Show ending image based on win/lose result
    PImage img = win ? endWinImg : endFailImg;
    if (img != null) image(img, 0, 0, width, height);

    // Text panel at bottom (semi-transparent black)
    noStroke();
    fill(0, 170);
    rect(0, height - 240, width, 240);

    // Story text styling
    fill(255);
    textAlign(LEFT, TOP);
    textSize(18);

    // Choose narrative based on game outcome
    String msg = win ?
        """
        The lights did not go out.
        
        The resentful spirits faded in the glow of your magic, and the sea wind grew calm again.
        Skywoman's seed has sprouted once more, and life continues on the turtle's back.
        The animals' sacrifice was not in vain. The world was not reset.
        
        You did not conquer this island - you simply protected it.
        Turtle Island still exists, and you have become its new guardian.""" :
        """
        The lights went out.
        
        When the last glow vanished, the land on the turtle's back began to crumble.
        The spirits no longer returned, and the order of life was torn apart.
        Skywoman's seed fell into the endless water.
        
        Turtle Island lost its shape. The world returned to the first darkness.
        And you became part of the sea.""";

    // Draw wrapped story text with padding
    text(msg, 30, height - 220, width - 60, 190);

    // Return instruction at bottom center
    textAlign(CENTER, BOTTOM);
    textSize(14);
    text("Press J to return to menu", width / 2f, height - 18);
}
}