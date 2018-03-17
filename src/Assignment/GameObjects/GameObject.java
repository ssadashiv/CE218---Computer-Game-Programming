package Assignment.GameObjects;

import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static Assignment.Other.Constants.*;
import static Assignment.Other.SharedValues.cellSize;
import static Assignment.Other.SharedValues.gridSize;

/**
 * Created by el16035 on 05/02/2018.
 */
public abstract class GameObject {

    Vector2D initPos;
    Vector2D initVel;
    Vector2D initDir;

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;

    public boolean dead;
    public int radius;
    private Clip deathSound;
    private Image image;

    public boolean isTarget = false;

    public boolean canMove = true;

    ObjectStats stats;

    public List<int[]> gridCoordinates = new ArrayList<>();
    //public boolean[][] grid;

    //Stores the last updated coordinates of the currenct object.
    //public List<int[]> gridPositions = new ArrayList<>();

    public GameObject(Vector2D position, Vector2D velocity, Vector2D direction, int radius, Clip deathSound, Image image) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
        this.radius = radius;
        this.deathSound = deathSound;
        this.image = image;


        initPos = new Vector2D(position);
        initVel = new Vector2D(velocity);
        initDir = new Vector2D(direction);
        //updateGrid();

    }


    void setStats(int armour, int livesRemaining, int fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath) {
        stats = new ObjectStats(armour, livesRemaining, fireRate, bulletSpeed, bulletDamage, contactDamage, scrapOnDeath);
    }

    public void updateGrid() {
        gridCoordinates.clear();

        Rectangle bounds = getBounds();
        //int gridSize = gridSize-1;
        double fSize = FRAME_HEIGHT;

        System.out.println("CELLSIZE="+cellSize);
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

    private boolean withinArrRange(int[] intArr) {
        for (int i = 0; i < intArr.length; i++) {
            if (intArr[i] < 0 || intArr[i] >= gridSize) return false;
        }
        return true;
    }

    public ObjectStats getStats() {
        return stats;
    }

    private void hit() {
        SoundManager.play(deathSound);
        dead = true;
    }

    public void update() {
        if (canMove) position.addScaled(velocity, DT);
        //updateGrid();
        //position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }


    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();

        t.rotate(direction.angle(), 0, 0);
        t.scale(radius * 2 / imW, radius * 2 / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);

        if (isTarget) targetOval(g);

        drawHitBox(g);
    }

    void drawHitBox(Graphics2D g){
        Rectangle r = getBounds();
        g.setColor(Color.RED);
        g.drawRect(r.x, r.y, r.width, r.height);
    }


    public void targetOval(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawOval((int) position.x - radius / 2, (int) position.y - radius / 2, radius, radius);

    }

    void setImage(Image newImage){
        this.image = newImage;
    }

    public abstract boolean canHit(GameObject other);

    public boolean overlap(GameObject other) {
        if (this instanceof Obstacle || other instanceof Obstacle) {
            return getBounds().intersects(other.getBounds());
        }
        double length = position.dist(other.position);
        return length < radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (overlap(other)) {
            if (other instanceof Obstacle) {
                HitDetection.HitObstacle(this, other);
            } else if (other instanceof DoorButton) {
                HitDetection.HitDoorButton(this, other);
            } else {
                this.hit();
                other.hit();
            }


        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
    }
}
