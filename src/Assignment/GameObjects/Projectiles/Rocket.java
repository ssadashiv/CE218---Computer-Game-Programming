package Assignment.GameObjects.Projectiles;

import Assignment.GameObjects.Enemies.Saucer;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Gravity.ForceFieldGravity;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;
import java.awt.*;

import static Assignment.Other.Constants.DT;

/**
 * Created by el16035 on 19/03/2018.
 */
public class Rocket extends Projectile {
    private static final Image IMAGE = Sprite.BASIC_ROCKET;

    private static final int RADIUS = 6;
    private static final long LIFE_TIME = 5000;
    private GameObject target;
    private int speed;

    public Rocket(Saucer parent, GameObject target, Vector2D position, Vector2D velocity, Vector2D direction) {
        super(parent, LIFE_TIME, position, velocity, direction, RADIUS*2, IMAGE);
        this.target = target;
        speed = parent.getStats().getBulletSpeed();
    }

    @Override
    public void update() {
        //Autoaims to the target
        direction.addScaled(new Vector2D(target.position).subtract(position).normalise(), DT * 20);
        velocity = new Vector2D(direction).mult(speed / direction.mag());
        super.update();
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip || super.canHit(other);
    }

}
