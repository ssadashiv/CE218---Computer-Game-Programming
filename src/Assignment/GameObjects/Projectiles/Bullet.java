package Assignment.GameObjects.Projectiles;

import Assignment.GameObjects.Enemies.Enemy;
import Assignment.GameObjects.Enemies.Saucer;
import Assignment.GameObjects.GameObject;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;
import java.awt.*;

/**
 * Created by el16035 on 05/02/2018.
 */
public class Bullet extends Projectile {
    private static final Image IMAGE = Sprite.BASIC_BULLET;
    private static final int RADIUS = 10;

    //Lifetime of every bullet in milliseconds
    private static final int LIFE_TIME = 2000;

    public Bullet(GameObject parent, Vector2D position, Vector2D velocity, Vector2D direction) {
        super(parent, LIFE_TIME, position, velocity, direction, RADIUS*2, IMAGE);
    }

    public boolean canHit(GameObject other) {
        return other instanceof Enemy || super.canHit(other);
    }
}
