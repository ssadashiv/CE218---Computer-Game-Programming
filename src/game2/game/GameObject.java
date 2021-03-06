package game2.game;

import game2.utilities.SoundManager;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static game2.Constants.DT;

/**
 * Created by el16035 on 05/02/2018.
 */
public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;
    boolean dead;
    int radius;
    private Clip deathSound;
    private Image image;

    public boolean isTarget = false;

    GameObject(Vector2D position, Vector2D velocity, Vector2D direction, int radius, Clip deathSound, Image image) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
        this.radius = radius;
        this.deathSound = deathSound;
        this.image = image;
    }

    public void hit() {
        SoundManager.play(deathSound);
        dead = true;
    }

    void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }


    void draw(Graphics2D g) {
        /*double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();

        t.rotate(direction.angle(), 0, 0);
        t.scale(radius * 2 / imW, radius * 2 / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
        if (isTarget) drawInvincibilityOval(g);*/
    }


    public void targetOval(Graphics2D g){
        g.setColor(Color.GREEN);
        g.drawOval((int) position.x - radius / 2, (int) position.y - radius / 2, radius, radius);

    }

    public abstract boolean canHit(GameObject other);

    private boolean overlap(GameObject other) {
        double length = position.dist(other.position);
        return length < radius + other.radius;
    }

    public int collisionHandling(GameObject other) {
        if (getClass() != other.getClass() && overlap(other)) {
            this.hit();
            other.hit();
            return 1;
        }
        return 0;
    }
}
