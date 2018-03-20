package Assignment.GameObjects.Projectiles;

import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 20/03/2018.
 */
public class CoinBullet extends Projectile {
    private static final Image IMAGE = Sprite.COIN_BULLET;
    private static final int RADIUS = 12;

    //Lifetime of every bullet in milliseconds
    private static final int LIFE_TIME = 5000;


    public CoinBullet(GameObject parent, Vector2D position, Vector2D velocity, Vector2D direction) {
        super(parent, LIFE_TIME, position, velocity, direction, RADIUS*2, IMAGE);
    }

    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip ||  super.canHit(other);
    }
}
