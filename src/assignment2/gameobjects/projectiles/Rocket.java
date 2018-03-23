package assignment2.gameobjects.projectiles;

import assignment2.gameobjects.enemies.Saucer;
import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.gravity.ForceFieldGravity;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;
import java.awt.*;

import static assignment2.other.Constants.DT;

/**
 * Created by el16035 on 19/03/2018.
 */
//Class for the bullets the Saucer is firing.

public class Rocket extends Projectile {
    private static final Image IMAGE = Sprite.BASIC_ROCKET;

    private static final int RADIUS = 6;
    private static final long LIFE_TIME = 5000;
    private GameObject target;
    private int speed;

    public Rocket(Saucer parent,ForceFieldGravity field, GameObject target, Vector2D position, Vector2D velocity, Vector2D direction) {
        super(parent,field,  LIFE_TIME, position, velocity, direction, RADIUS*2, IMAGE);
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
