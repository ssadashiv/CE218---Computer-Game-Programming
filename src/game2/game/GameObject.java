package game2.game;

import game2.utilities.Vector2D;

import java.awt.*;

import static game2.game.Constants.DT;
import static game2.game.Constants.FRAME_HEIGHT;
import static game2.game.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 05/02/2018.
 */
abstract class GameObject {
    Vector2D position;
    Vector2D velocity;
    boolean dead;
    private int radius;

    GameObject(Vector2D position, Vector2D velocity, int radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void hit() {
        dead = true;
    }

    void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D g);

    public boolean overlap(GameObject other) {
        //TODO: simple overlap detection based on bounding circles
        double length = position.dist(other.position);

        return length < radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (getClass() != other.getClass() && overlap(other)) {
            this.hit();
            other.hit();
        }
    }
}
