package Assignment.GameObjects;

import Assignment.Utilities.HitDetection;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Created by el16035 on 14/03/2018.
 */
public class Obstacle extends GameObject {
    private static final double MARGIN = 0.96;

    public Obstacle(Vector2D pos, Image image, int width, int height) {
        super(pos, new Vector2D(0, 0), new Vector2D(0, 0), width, height, null, image);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (position.x - (width / 2) * MARGIN), (int) (position.y - (height /2) * MARGIN), (int) (width * MARGIN), (int) (height * MARGIN));
    }

    @Override
    public boolean canHit(GameObject other) {
        return true;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (overlap(other)) {
                HitDetection.HitObstacle(this, other);
            }
        }
    }

    @Override
    public void hitDetected(GameObject other) {
        HitDetection.HitObstacle(this, other);
    }
}
