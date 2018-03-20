package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.*;
import Assignment.GameObjects.Projectiles.Bullet;
import Assignment.Utilities.Controllers.AIControllers.AIAction;
import Assignment.Utilities.Controllers.AIControllers.ZapperCtrl;
import Assignment.Utilities.Gravity.ForceFieldGravity;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DT;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 19/03/2018.
 */

//Class for enemy which attaches themselves to a player and damages them. The player has to quickly change the direction of the ship to throw them off.
public class Zapper extends Enemy {
    private static final int DETACH_SPEED = 200;


    private static final Vector2D INIT_DIR = new Vector2D(0, 1);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final int SIZE = cellSize / 3 * 2;
    private static final int SPEED = 140;


    private static final Clip DEATH_SOUND = SoundManager.bangLarge;
    private static final Image IMAGE = Sprite.ZAPPER;

    //STATS
    private static final int INIT_ARMOUR = 35;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 0;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 0;
    private static final int BULLET_DAMAGE = 10;
    private static final int CONTACT_DAMAGE = 0;
    private static final int SCRAP_ON_DEATH = 15;
    //END OF STATS

    public boolean attached = false;
    public boolean detaching = false;
    private AIAction ctrl;

    public Zapper(PlayerShip ship, Vector2D position) {
        super(position, INIT_VEL, INIT_DIR, SPEED, SIZE, SIZE, DEATH_SOUND, IMAGE);
        setStats();
        ctrl = new ZapperCtrl(this, ship).aiAction();
        ctrl.direction = INIT_DIR;
        ctrl.velocity = INIT_VEL;
    }

    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof Bullet || other instanceof Hole;
    }

    @Override
    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }

    public void update() {
        field.update(this, DT);
        if (!detaching) {
            velocity = ctrl.velocity;
            direction = ctrl.direction;
        }
        super.update();
    }

    public void detachFromShip() {
        detaching = true;
        attached = false;
        isInvincible = false;
        double randomAngle = Math.toRadians(Math.random() * 360);
        velocity = new Vector2D(DETACH_SPEED, DETACH_SPEED).rotate(randomAngle);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                detaching = false;
            }
        }, 2000);
    }
}
