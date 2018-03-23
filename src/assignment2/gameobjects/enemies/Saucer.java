package assignment2.gameobjects.enemies;

import assignment2.gameobjects.*;
import assignment2.gameobjects.wormholes.BlackHole;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.projectiles.Projectile;
import assignment2.gameobjects.projectiles.Rocket;
import assignment2.other.SharedValues;
import assignment2.utilities.controllers.enemycontrollers.AIAction;
import assignment2.utilities.controllers.enemycontrollers.SaucerCtrl;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.*;
import java.util.List;

import static assignment2.other.Constants.DT;
import static assignment2.other.Constants.VEC_PLACEHOLDER;
import static assignment2.other.SharedValues.lvlDifficulty;

/**
 * Created by el16035 on 19/03/2018.
 */

/*
* Enemy which mimics the velocity of the player's ship.
* */
public class Saucer extends Enemy implements Turret {
    private static final Image IMAGE = Sprite.SAUCER;
    private static final Clip DEATH_SOUND = SoundManager.saucerBig;

    //Size of the object
    private static final int SIZE = 40;

    //Initial vectors. Position, Velocity, Direction.
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final Vector2D INIT_DIR = new Vector2D(0, 1);


    //STATS
    private static final int INIT_ARMOUR = 150;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 1500;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 200;
    private static final int BULLET_DAMAGE = 35;
    private static final int CONTACT_DAMAGE = 25;
    private static final int SCRAP_ON_DEATH = 100;
    //END OF STATS

    private AIAction action;

    private List<Projectile> rockets = new ArrayList<>();

    public Saucer(Vector2D pos) {
        super(pos, INIT_VEL, INIT_DIR, 0, SIZE, SIZE, DEATH_SOUND, IMAGE);
        setStats();
    }

    public void update() {
        velocity.addScaled(new Vector2D(action.velocity), DT);
        super.update();
    }


    @Override
    public void setPlayerShip(PlayerShip ship) {
        super.setPlayerShip(ship);
        this.action = new SaucerCtrl(this, ship).aiAction();
    }

    @Override
    public void setStats() {
        super.setStats((int) (INIT_ARMOUR*lvlDifficulty), INIT_LIVES, FIRE_RATE, (int) (BULLET_SPEED*lvlDifficulty), (int)(BULLET_DAMAGE*lvlDifficulty), (int)(CONTACT_DAMAGE*lvlDifficulty), (int)(SCRAP_ON_DEATH*lvlDifficulty));
    }

    @Override
    public void hitDetected(GameObject other) {
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

    //Make a seeking-rocket. The rocket curves towards the player
    @Override
    public void makeBullet(double angle) {
        if (field != null) {
            Rocket rocket = new Rocket(this, field, playerShip, new Vector2D(position), VEC_PLACEHOLDER, new Vector2D(direction));
            //init the bullet just outside the turret.
            rocket.velocity = new Vector2D(velocity).addScaled(direction, stats.getBulletSpeed());
            rockets.add(rocket);
            //bulletTimer();
        }
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip || other instanceof Obstacle || other instanceof BlackHole || other instanceof Bullet;
    }
}
