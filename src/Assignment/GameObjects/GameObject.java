package Assignment.GameObjects;

import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import static Assignment.Other.Constants.*;

/**
 * Created by el16035 on 05/02/2018.
 */
public abstract class GameObject {
    Vector2D initPos;
    Vector2D initVel;
    Vector2D initDir;

    public Vector2D position;
    public Vector2D velocity;
    Vector2D direction;
    public boolean dead;
    public int radius;
    private Clip deathSound;
    private Image image;

    public boolean isTarget = false;

    ObjectStats stats;

    GameObject(Vector2D position, Vector2D velocity, Vector2D direction, int radius, Clip deathSound, Image image) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
        this.radius = radius;
        this.deathSound = deathSound;
        this.image = image;


        initPos = new Vector2D(position);
        initVel = new Vector2D(velocity);
        initDir = new Vector2D(direction);


    }

    void setStats(int armour, int livesRemaining, long fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath){
        stats = new ObjectStats(armour, livesRemaining, fireRate, bulletSpeed, bulletDamage, contactDamage, scrapOnDeath);
    }

    public ObjectStats getStats(){
        return stats;
    }

    private void hit() {
        SoundManager.play(deathSound);
        dead = true;
    }

    public void update() {
        position.addScaled(velocity, DT);
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

        Rectangle r = getBounds();
        g.setColor(Color.RED);
        g.drawRect(r.x, r.y, r.width, r.height);
    }


    public void targetOval(Graphics2D g){
        g.setColor(Color.GREEN);
        g.drawOval((int) position.x - radius / 2, (int) position.y - radius / 2, radius, radius);

    }

    public abstract boolean canHit(GameObject other);

    private boolean overlap(GameObject other) {
        if (this instanceof Obstacle || other instanceof Obstacle){
            return getBounds().intersects(other.getBounds());
        }
        double length = position.dist(other.position);
        return length < radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (getClass() != other.getClass() && overlap(other)) {
            if (this instanceof Obstacle || other instanceof Obstacle) {
                hitObstacle(other);
            }else{
                this.hit();
                other.hit();
            }

        }
    }

    private void hitObstacle(GameObject o){
        GameObject ship;
        GameObject obstacle;

        if (this instanceof Obstacle){
            ship = o;
            obstacle = this;
        }else{
            ship = this;
            obstacle = o;
        }

        Vector2D obsPos = new Vector2D(obstacle.position);
        double obsRad = obstacle.radius;

        String direction = HitDetection.whichDirection(ship, obstacle);

        switch (direction){
            case "north":
                System.out.println("hit from north");
                ship.position.y = obsPos.y - ship.radius;
                ship.velocity.y *= WALL_REFLECT;
                break;
            case "south":
                System.out.println("hit from south");
                ship.position.y = obsPos.y + obsRad + ship.radius;
                ship.velocity.y *= WALL_REFLECT;
                break;
            case "west":
                System.out.println("hit from west");
                ship.position.x = obsPos.x - ship.radius;
                ship.velocity.x *= WALL_REFLECT;
                break;
            case "east":
                System.out.println("hit from east");
                ship.position.x = obsPos.x + obsRad + ship.radius;
                ship.velocity.x *= WALL_REFLECT;
                break;

        }
    }

    public Rectangle getBounds(){
        if (this instanceof Obstacle){
            return new Rectangle((int)position.x, (int) position.y, radius, radius);
        }
        return new Rectangle((int)position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
    }
}
