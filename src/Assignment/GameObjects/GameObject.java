package Assignment.GameObjects;

import Assignment.Utilities.*;
import Assignment.Utilities.Gravity.ForceFieldGravity;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

import static Assignment.Other.Constants.*;
import static Assignment.Other.SharedValues.cellSize;
import static Assignment.Other.SharedValues.gridSize;

/**
 * Created by el16035 on 05/02/2018.
 */
public abstract class GameObject implements CollisionHandling{
    public ForceFieldGravity field;

    Vector2D initPos;
    Vector2D initVel;
    Vector2D initDir;

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;

    public boolean dead;
    public int radius;
    public int width;
    public int height;
    private Clip deathSound;
    private Image image;

    public boolean canMove = true;
    public boolean switchingRooms = false;


    public boolean isInvincible = false;
    public ObjectStats stats;
    private HealthBar healthBar;

    public List<int[]> gridCoordinates = new ArrayList<>();
    //public Map<Vector2D, Integer> hitDetectionBubbles = new HashMap<>();

    public Vector2D gravitationalPull = new Vector2D(0, 0);

    //public boolean[][] grid;
    //Stores the last updated coordinates of the currenct object.
    //public List<int[]> gridPositions = new ArrayList<>();

    public GameObject(Vector2D position, Vector2D velocity, Vector2D direction, int width, int height, Clip deathSound, Image image) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
        this.radius = width / 2;
        this.width = width;
        this.height = height;
        this.deathSound = deathSound;
        this.image = image;
        //updateGrid();

        initPos = new Vector2D(position);
        initVel = new Vector2D(velocity);
        initDir = new Vector2D(direction);
    }


    public void setStats(int armour, int livesRemaining, int fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath) {
        stats = new ObjectStats(this, armour, livesRemaining, fireRate, bulletSpeed, bulletDamage, contactDamage, scrapOnDeath);
        healthBar = new HealthBar(this);
    }
    public ObjectStats getStats() {
        return stats;
    }

    public void setField(ForceFieldGravity field) {
        this.field = field;
    }

    public void updateGrid() {
        gridCoordinates.clear();

        Rectangle bounds = getBounds();
        //int gridSize = gridSize-1;
        double fSize = FRAME_HEIGHT;

        int[][] pos = {
                {(int) (bounds.x / fSize * cellSize), (int) (bounds.y / fSize * cellSize)}, // TOP LEFT
                {(int) ((bounds.x + bounds.width) / fSize * cellSize), (int) (bounds.y / fSize * cellSize)}, //
                {(int) (bounds.x / fSize * cellSize), (int) ((bounds.y + bounds.height) / fSize * cellSize)},
                {(int) ((bounds.x + bounds.width) / fSize * cellSize), (int) ((bounds.y + bounds.height) / fSize * cellSize)}
        };

        for (int[] co : pos) {
            if (withinArrRange(co)) {
                gridCoordinates.add(co);
            }
        }

        gridCoordinates = new ArrayList<>(new LinkedHashSet<>(gridCoordinates));

/*
        if (this instanceof PlayerShip){
            for (int[] arr : gridCoordinates) System.out.println(Arrays.toString(arr));
        }*/

    }
    public void timeOutInvincible() {
    }

    private boolean withinArrRange(int[] intArr) {
        for (int i = 0; i < intArr.length; i++) {
            if (intArr[i] < 0 || intArr[i] >= gridSize) return false;
        }
        return true;
    }



    public void hit() {
        if (stats.getLivesRemaining() == 0){
            SoundManager.play(deathSound);
            dead = true;
            //TODO: Animation.explosion(this);
        }else{

        }
    }

    public void update() {
        if (stats != null) {
            if (stats.getArmour() <= 0) {
                dead = true;
                System.out.println("updating");
                System.out.println("lives before="+stats.getLivesRemaining());
                stats.addLivesRemaining(-1);
                System.out.println("lives after="+stats.getLivesRemaining());

            }
        }
        if (canMove) position.addScaled(velocity, DT);
        //updateGrid();
        //position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }


    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();

        t.rotate(direction.angle(), 0, 0);
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);

        if (TESTING && this instanceof PlayerShip) drawHitBox(g);
        if (healthBar != null) healthBar.draw(g);

        if (isInvincible){
            drawInvincibilityOval(g);
        }

        //if (!(this instanceof Obstacle) && TESTING && this instanceof PlayerShip) drawHitBubbles(g);
    }

    void drawHitBox(Graphics2D g) {
        Rectangle r = getBounds();
        g.setColor(Color.RED);
        g.drawRect(r.x, r.y, r.width, r.height);
    }


    public void drawInvincibilityOval(Graphics2D g) {
        g.setColor(new Color(255,0,0, 50));
        g.fillOval((int) position.x - (height / 2), (int) position.y - (height / 2), width, height);
    }

    void setImage(Image newImage) {
        this.image = newImage;
    }

    public abstract boolean canHit(GameObject other);

    public boolean overlap(GameObject other) {
        if (this instanceof Obstacle || other instanceof Obstacle) {
            return getBounds().intersects(other.getBounds());
        }
        return position.dist(other.position) < radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (overlap(other)) {
                this.hitDetected(other);
            }
        }
    }

    public Rectangle getBounds() {
        if (this instanceof PlayerShip)
            return new Rectangle((int) position.x - (height / 2), (int) position.y - (height / 2), height, height);
        return new Rectangle((int) position.x - (width / 2), (int) position.y - (height / 2), width, height);
    }
}
