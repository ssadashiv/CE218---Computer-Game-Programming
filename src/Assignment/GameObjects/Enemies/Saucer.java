package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.*;
import Assignment.GameObjects.Projectiles.Bullet;
import Assignment.GameObjects.Projectiles.Projectile;
import Assignment.GameObjects.Projectiles.Rocket;
import Assignment.Utilities.Controllers.AIControllers.AIAction;
import Assignment.Utilities.Controllers.AIControllers.SaucerCtrl;
import Assignment.Utilities.Gravity.ForceField;
import Assignment.Utilities.Gravity.ForceFieldGravity;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.*;
import java.util.List;

import static Assignment.Other.Constants.DT;
import static Assignment.Other.Constants.VEC_PLACEHOLDER;

/**
 * Created by el16035 on 19/03/2018.
 */

//TODO: Make this guy's controller better.
public class Saucer extends Enemy implements Turret {
    private static final Image IMAGE = Sprite.SAUCER;
    private static final Clip DEATH_SOUND = SoundManager.saucerBig;

    //Size of the object
    private static final int SIZE = 40;

    //Initial vectors. Position, Velocity, Direction.
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final Vector2D INIT_DIR = new Vector2D(0, 1);


    //STATS
    //private static final int INIT_ARMOUR = 150;
    private static final int INIT_ARMOUR = 1;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    ///private static final int FIRE_RATE = 1000;
    private static final int FIRE_RATE = 100000;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 200;
    private static final int BULLET_DAMAGE = 35;
    private static final int CONTACT_DAMAGE = 25;
    private static final int SCRAP_ON_DEATH = 100;
    //END OF STATS

    private AIAction action;
    private PlayerShip player;

    private List<Projectile> rockets = new ArrayList<>();

    private boolean canShoot = false;


    public Saucer(PlayerShip player, Vector2D pos) {
        super(pos, INIT_VEL, INIT_DIR, 0, SIZE, SIZE, DEATH_SOUND, IMAGE);
        setStats();
        this.player = player;
        this.action = new SaucerCtrl(this, player).aiAction();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                canShoot = true;
            }
        }, 0, 1000);
    }

    public void update() {
        velocity.addScaled(new Vector2D(action.velocity), DT);
        super.update();
    }


    @Override
    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }

    @Override
    public void hitDetected(GameObject other) {
        if (other instanceof Enemy || other instanceof Saucer || other instanceof PlayerShip) {
            HitDetection.ContactNoDamage(this, other);
        }

        if (other instanceof BlackHole) {
            other.hitDetected(this);
            return;
        }
        HitDetection.ContactHit(this, other);
    }

    @Override
    public List<Projectile> getProjectiles() {
        return rockets;
    }

    @Override
    public void clearProjectiles() {
        rockets.clear();
    }

    @Override
    public void makeBullet(double angle) {
        if (field != null) {
            SoundManager.fire();
            Rocket rocket = new Rocket(this, player, new Vector2D(position), VEC_PLACEHOLDER, new Vector2D(direction));
            //init the bullet just outside the turret.
            rocket.velocity = new Vector2D(velocity).addScaled(direction, stats.getBulletSpeed());
            rocket.setField(field);
            rockets.add(rocket);
            SoundManager.fire();
            //bulletTimer();
        }
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip || other instanceof Obstacle || other instanceof BlackHole || other instanceof Bullet;
    }
}
