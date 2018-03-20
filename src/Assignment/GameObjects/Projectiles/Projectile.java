package Assignment.GameObjects.Projectiles;

import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DT;

/**
 * Created by el16035 on 19/03/2018.
 */
public class Projectile extends GameObject {
    private static final Clip DEATH_SOUND = SoundManager.fire;

    GameObject parent;
    public int damage;
    public long lifeTime;

    //TODO: Increase the size of the bullet when the damage gets greater.
    public Projectile(GameObject parent, long lifeTime, Vector2D position, Vector2D velocity, Vector2D direction, int size, Image image) {
        super(position, velocity, direction, size, size, DEATH_SOUND, image);
        this.parent = parent;
        this.lifeTime = lifeTime;
        damage = this.parent.getStats().getBulletDamage();
        startTimer();
    }

    @Override
    public void update() {
        field.update(this, DT);
        super.update();
    }

    public GameObject getParent() {
        return parent;
    }

    @Override
    public void hitDetected(GameObject other) {
        HitDetection.ProjectileHitSomething(this, other);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Obstacle;
    }


    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                dead = true;
            }
        }, lifeTime);
    }
}
