package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by el16035 on 18/03/2018.
 */
public abstract class Enemy extends GameObject {
    public Enemy(Vector2D position, Vector2D velocity, Vector2D direction, int width, int height, Clip deathSound, Image image) {
        super(position, velocity, direction, width, height, deathSound, image);
    }

    public void hitDetected(GameObject other) {
        HitDetection.ContactHit(this, other);
    }
}
