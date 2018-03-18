package Assignment.GameObjects;

import Assignment.GameObjects.Enemies.Enemy;
import Assignment.Utilities.HitDetection;
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

    private static final int RADIUS = 10;
    static final double MUZZLE_VEL = 300;

    private Ship parent;
    public int damage;
    //Lifetime of every bullet in milliseconds
    private int lifeTime = 2000;


    Bullet(Ship parent,Vector2D position, Vector2D velocity, Vector2D direction) {
        super(position, velocity, direction, RADIUS, RADIUS, DEATH_SOUND, IMAGE);
        this.parent = parent;
        damage = this.parent.getStats().getBulletDamage();
        startTimer();
    }

    public Ship getParent() {
        return parent;
    }

    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                dead = true;
            }
        }, lifeTime);
    }

    public void hitDetected(GameObject other) {
        HitDetection.BulletHitSomething(this, other);
    }
    public boolean canHit(GameObject other) {
        return other instanceof Enemy;

    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            super.collisionHandling(other);
           /* if (other instanceof Obstacle){
                other.hitDetected(this);
            }else{
                hitDetected(other);
            }*/
        }
    }


}
