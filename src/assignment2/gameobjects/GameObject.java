package assignment2.gameobjects;

import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.utilities.*;
import assignment2.utilities.gravity.ForceFieldGravity;
import assignment2.utilities.objectassist.CollisionHandling;
import assignment2.utilities.objectassist.HealthBar;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

import static assignment2.other.Constants.*;
import static assignment2.other.SharedValues.cellSize;
import static assignment2.other.SharedValues.gridSize;

/**
 * Created by el16035 on 05/02/2018.
 */

//Superclas for all GameObjects
public abstract class GameObject implements CollisionHandling {
    public ForceFieldGravity field;
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
    //public map<Vector2D, Integer> hitDetectionBubbles = new HashMap<>();

    //The gravitationalpull of the current object
    Vector2D gravitationalPull = new Vector2D(0, 0);

    //The game uses scrapmetal as currency.
    public ScrapMetal metal = null;

    public GameObject(Vector2D position, Vector2D velocity, Vector2D direction, int width, int height, Clip deathSound, Image image) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
        this.radius = width / 2;
        this.width = width;
        this.height = height;
        this.deathSound = deathSound;
        this.image = image;
    }


    protected void setStats(int armour, int livesRemaining, int fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath) {
        stats = new ObjectStats(this, armour, livesRemaining, fireRate, bulletSpeed, bulletDamage, contactDamage, scrapOnDeath);
        healthBar = new HealthBar(this);
    }

    public ObjectStats getStats() {
        return stats;
    }

    //Sets the field of a gameObject
    public void setField(ForceFieldGravity field) {
        this.field = field;
    }

    //A method being applied to GameObjects that has a timeinterval of invincibility between being damaged.
    public void timeOutInvincible() {
    }

    //Updates the position of the gameobject.
    public void update() {
        if (canMove) position.addScaled(velocity, DT);
    }


    //draw the image of the gameobject
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

        if (TESTING) drawHitBox(g);
        if (healthBar != null) healthBar.draw(g);

        if (isInvincible) {
            drawInvincibilityOval(g);
        }
    }

    //method which gets called when the object dies. Drops a certain amount of scrapmetal which the player can pick up.
    public void dropScrapMetal(int amount) {
        if (amount != 0) {
            metal = new ScrapMetal(position, amount);
        }
        dead = true;
        SoundManager.play(deathSound);
    }

    //Method to draw hitboxes during testing
    private void drawHitBox(Graphics2D g) {
        Rectangle r = getBounds();
        g.setColor(Color.RED);
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    //If the gameobject is invinvible, draw an oval around it
    private void drawInvincibilityOval(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 50));
        g.fillOval((int) position.x - (height / 2), (int) position.y - (height / 2), width, height);
    }

    public void setImage(Image newImage) {
        this.image = newImage;
    }

    public abstract boolean canHit(GameObject other);

    protected boolean overlap(GameObject other) {
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
