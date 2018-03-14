package Assignment.GameObjects;

import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 05/02/2018.
 */
public class Bullet extends GameObject {
    private static final Clip DEATH_SOUND = SoundManager.fire;
    private static final Image IMAGE = Sprite.BASIC_BULLET;

    private static final int RADIUS = 7;
    static final double MUZZLE_VEL = 300;

    private Ship parent;
    //Lifetime of every bullet in milliseconds
    private int lifeTime = 2000;


    Bullet(Vector2D position, Vector2D velocity, Vector2D direction, Ship parent) {
        super(position, velocity, direction, RADIUS, DEATH_SOUND, IMAGE);
        this.parent = parent;
        startTimer();
    }
    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                dead = true;
            }
        }, lifeTime);


    }

    public boolean canHit(GameObject other) {
        return false;

    }

    public int collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (super.collisionHandling(other) != 0) {
                return 10;
            }
        }


        return 0;
    }
}
