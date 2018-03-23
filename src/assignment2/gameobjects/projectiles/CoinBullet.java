package assignment2.gameobjects.projectiles;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.gravity.ForceFieldGravity;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 20/03/2018.
 */

//Class for the bullets the CoinMoneyMan is firing.
public class CoinBullet extends Projectile {
    private static final Image IMAGE = Sprite.COIN_BULLET;
    private static final int RADIUS = 12;

    //Lifetime of every bullet in milliseconds
    private static final int LIFE_TIME = 5000;


    public CoinBullet(GameObject parent, ForceFieldGravity field, Vector2D position, Vector2D velocity, Vector2D direction) {
        super(parent, field, LIFE_TIME, position, velocity, direction, RADIUS*2, IMAGE);
    }

    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip ||  super.canHit(other);
    }
}
