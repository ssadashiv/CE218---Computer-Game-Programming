package assignment2.gameobjects.projectiles;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.utilities.gravity.ForceFieldGravity;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static assignment2.other.Constants.DT;

/**
 * Created by el16035 on 19/03/2018.
 */
//Superclass for all projectiles.
public class Projectile extends GameObject {
    private static final Clip DEATH_SOUND = SoundManager.fire;

    GameObject parent;
    public int damage;
    public long lifeTime;

    //TODO: Increase the size of the bullet when the damage gets greater.
    public Projectile(GameObject parent, ForceFieldGravity field, long lifeTime, Vector2D position, Vector2D velocity, Vector2D direction, int size, Image image) {
        super(position, velocity, direction, size, size, DEATH_SOUND, image);
        this.parent = parent;
        this.lifeTime = lifeTime;
        setField(field);
        damage = this.parent.getStats().getBulletDamage();
        SoundManager.play(DEATH_SOUND);
        startTimer();
    }

    @Override
    public void update() {
        if (field != null){
            field.update(this, DT);
        }
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


    //Sets the lifetime of the projectile.
    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                dead = true;
            }
        }, lifeTime);
    }
}
