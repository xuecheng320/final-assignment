 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication6;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

// Player character
// Supports WASD movement
// Supports SHIFT sprint
// Supports K weapon switching
// Supports holding J for continuous attack with locked direction
public class Player extends Actor {

    // How many kills are needed before the player gets a small stat upgrade to make the game stronger over time
    private int upgradeEveryKills = 4;

    // Whether up key is pressed
    private boolean up;

    // Whether down key is pressed
    private boolean down;

    // Whether left key is pressed
    private boolean left;

    // Whether right key is pressed
    private boolean right;

    // Whether sprint key is held
    private boolean sprintHeld;

    // Base movement speed value tuned for good feel
    public float speed = 3.5f;

    // Sword mode
    public static final int WEAPON_SWORD = 1;

    // Bow mode
    public static final int WEAPON_BOW = 2;

    // Current weapon
    public int weapon = WEAPON_SWORD;

    // World width used to keep player within map bounds
    private int worldWidth = 0;

    // World height used to keep player within map bounds
    private int worldHeight = 0;

    // Obstacle list used for overlap detection during movement
    private ArrayList<Obstacle> obstacles = null;

    // Sword attack cooldown
    private int attackCooldown = 0;

    // Sword attack cooldown maximum value larger value means slower attacks
    private int attackCooldownMax = 16;

    // Sword attack range radius in pixels
    private int attackRange = 76;

    // Sword bonus damage for close combat to encourage getting close
    private int swordBonusDamage = 5;

    // Sword knockback distance in pixels
    private float swordKnockback = 12f;

    // Bow shooting cooldown
    private int shootCooldown = 0;

    // Bow shooting cooldown maximum value
    private int shootCooldownMax = 20;

    // Arrow speed in pixels per frame
    private float arrowSpeed = 15.0f;

    // Whether attack key is held
    private boolean attackHeld = false;

    // Direction locked when attack key is first pressed
    private int lockedDir = DOWN;

    // Whether currently playing attack animation
    private boolean attacking = false;

    // Remaining attack animation frames
    private int attackAnimTimer = 0;

    // Maximum attack animation frames
    private int attackAnimMax = 12;

    // Current kill count
    public int kills = 0;

    /**
     * Set world information
     * Allows player to do boundary limits and obstacle overlap checks during update
     */
    public void setWorld(int worldW, int worldH, ArrayList<Obstacle> obstacles) {
        this.worldWidth = worldW;
        this.worldHeight = worldH;
        this.obstacles = obstacles;
    }

    /**
     * Create a player character
     */
    public Player(PApplet app, float x, float y, SpriteSet sprites) {
        super(app, x, y, 48, 48);

        this.walk = sprites.walk;
        this.idle = sprites.idle;
        this.attack = sprites.attack;

        maxHp = 100;
        hp = 100;

        atk = 18;
    }

    /**
     * Set movement input state
     */
    public void setInput(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    /**
     * Set sprint state
     */
    public void setSprint(boolean sprintHeld) {
        this.sprintHeld = sprintHeld;
    }

    /**
     * Set current weapon
     */
    public void setWeapon(int wpn) {
        if (wpn == WEAPON_SWORD || wpn == WEAPON_BOW) {
            weapon = wpn;
        }
    }

    /**
     * Start holding attack
     */
    public void startHoldAttack() {
        if (!attackHeld) {
            attackHeld = true;
            lockedDir = dir;
        }
    }

    /**
     * Stop holding attack
     */
    public void stopHoldAttack() {
        attackHeld = false;
    }

    /**
     * Adjust player stats based on difficulty
     * Called by MySketch startNewGame
     * Directly modifies several key variables to implement difficulty changes
     */
 public void applyDifficulty(int difficulty) {
    // Fixed balanced stats for classroom demo (no real difficulty scaling)
    maxHp = 200;
    hp = 200;

    atk = 18;
    speed = 3.2f;

    attackCooldownMax = 22;

    shootCooldownMax = 12;

    arrowSpeed = 18.0f;
}

/**
 * Update player logic each frame
 */
@Override
public void update() {
    // Skip if world size hasn't been set yet
    if (worldWidth == 0 || worldHeight == 0) {
        return;
    }
    updateMove(worldWidth, worldHeight, obstacles);
}

/**
 * Handle movement and obstacle overlap
 * Move first then check overlap if overlap occurs move back
 */
public void updateMove(int worldW, int worldH, ArrayList<Obstacle> obs) {
    // Decrease cooldowns each frame
    if (attackCooldown > 0) attackCooldown--;
    if (shootCooldown > 0) shootCooldown--;

    // Update attack animation timer
    if (attacking) {
        attackAnimTimer--;
        if (attackAnimTimer <= 0) attacking = false;
    }

    float nx = 0;
    float ny = 0;

    // Apply sprint boost if held
    float curSpeed = speed;
    if (sprintHeld) curSpeed *= 1.45f;

    // Build movement vector from input
    if (up) ny -= curSpeed;
    if (down) ny += curSpeed;
    if (left) nx -= curSpeed;
    if (right) nx += curSpeed;

    // Set moving flag for animation
    moving = (nx != 0 || ny != 0);

    // Lock direction while holding attack, otherwise face movement direction
    if (attackHeld) {
        dir = lockedDir;
    } else {
        if (Math.abs(nx) > Math.abs(ny)) {
            if (nx < 0) dir = LEFT;
            else if (nx > 0) dir = RIGHT;
        } else {
            if (ny < 0) dir = UP;
            else if (ny > 0) dir = DOWN;
        }
    }

    // Move X and check collision with obstacles
    x += nx;
    if (obs != null) {
        for (int i = 0; i < obs.size(); i++) {
            Obstacle o = obs.get(i);
            if (o.overlapsRect(x, y, width, height)) {
                x -= nx;  // Undo movement if blocked
                break;
            }
        }
    }

    // Move Y and check collision with obstacles
    y += ny;
    if (obs != null) {
        for (int i = 0; i < obs.size(); i++) {
            Obstacle o = obs.get(i);
            if (o.overlapsRect(x, y, width, height)) {
                y -= ny;  // Undo movement if blocked
                break;
            }
        }
    }

    // Keep player inside world bounds
    if (x < 0) x = 0;
    if (y < 0) y = 0;
    if (x + width > worldW) x = worldW - width;
    if (y + height > worldH) y = worldH - height;

    // Advance animation frame counter
    animTick();
}

/**
 * Melee attack uses a forward range box to determine if enemies are in range
 */
private void attackSword(ArrayList<Enemy> enemies) {
    // Respect cooldown
    if (attackCooldown > 0) return;
    attackCooldown = attackCooldownMax;

    // Trigger attack animation
    attacking = true;
    attackAnimTimer = attackAnimMax;
    frame = 0;
    
    //ai:chatgpt
    //prompt: How to make sword attack only hit in front of player in Processing?
    // Create a square area around player center
    float cx = cx();
    float cy = cy();

    float ax1 = cx - attackRange;
    float ay1 = cy - attackRange;
    float ax2 = cx + attackRange;
    float ay2 = cy + attackRange;

    // Cut the square in half based on facing direction (only hit in front)
    if (dir == UP) ay2 = cy;
    else if (dir == DOWN) ay1 = cy;
    else if (dir == LEFT) ax2 = cx;
    else if (dir == RIGHT) ax1 = cx;
    //end
    int swordDmg = atk + swordBonusDamage;

    // Check all enemies for hits
    for (int i = enemies.size() - 1; i >= 0; i--) {
        Enemy e = enemies.get(i);
        float ex = e.cx();
        float ey = e.cy();

        if (ex >= ax1 && ex <= ax2 && ey >= ay1 && ey <= ay2) {
            e.hp -= swordDmg;

            // Apply knockback in attack direction
            if (dir == UP) e.y -= swordKnockback;
            else if (dir == DOWN) e.y += swordKnockback;
            else if (dir == LEFT) e.x -= swordKnockback;
            else if (dir == RIGHT) e.x += swordKnockback;

            // Handle enemy death
            if (e.hp <= 0) {
                enemies.remove(i);
                kills++;

                // Small stat upgrade every few kills
                if (kills % upgradeEveryKills == 0) {
                    maxHp += 10;
                    hp += 10;
                    atk += 3;
                    speed += 0.10f;
                }
            }
        }
    }
}

/**
 * Ranged attack shoots an arrow in the given direction
 */
private void shootArrowDir(ArrayList<Arrow> arrows, int shootDir) {
    // Respect cooldown
    if (shootCooldown > 0) return;
    shootCooldown = shootCooldownMax;

    // Trigger short attack animation
    attacking = true;
    attackAnimTimer = 8;
    frame = 0;

    // Shoot from player center
    float sx = cx();
    float sy = cy();

    float vx = 0;
    float vy = 0;

    // Set arrow velocity based on direction
    if (shootDir == UP) vy = -arrowSpeed;
    else if (shootDir == DOWN) vy = arrowSpeed;
    else if (shootDir == LEFT) vx = -arrowSpeed;
    else if (shootDir == RIGHT) vx = arrowSpeed;

    // Calculate damage (half of melee, but never below 6)
    int dmg = (int) (atk * 0.5f);
    if (dmg < 6) dmg = 6;

    arrows.add(new Arrow(sx, sy, vx, vy, dmg));
}

/**
 * Handle continuous attack while holding J each frame (called by MySketch.updatePlay)
 */
public void updateCombat(ArrayList<Enemy> enemies, ArrayList<Arrow> arrows) {
    // Only attack while holding J
    if (!attackHeld) return;

    // Use the direction locked when J was first pressed
    dir = lockedDir;

    if (weapon == WEAPON_SWORD) {
        attackSword(enemies);
    } else {
        shootArrowDir(arrows, lockedDir);
    }
}

/**
 * Draw the player character
 */
@Override
public void draw() {
    PImage img = null;

    // Priority: attack animation > walk animation > idle animation
    if (attacking && weapon == WEAPON_SWORD && attack != null) {
        int idx = frame % attack[dir].length;
        img = attack[dir][idx];
    } else if (moving && walk != null) {
        int idx = frame % walk[dir].length;
        img = walk[dir][idx];
    } else if (idle != null) {
        int idx = frame % idle[dir].length;
        img = idle[dir][idx];
    }

    // Only draw if we have a valid image
    if (img != null) {
        app.image(img, x, y, width, height);
    }
}
}