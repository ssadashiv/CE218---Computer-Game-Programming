package Assignment.GameObjects;

import Assignment.Utilities.Controllers.NewAction;
import Assignment.Utilities.Controllers.Controller;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.*;

/**
 * Created by el16035 on 27/02/2018.
 */
public abstract class Ship extends GameObject {
    private NewAction action;
    private double drag;
    private double steerRate;
    private double magAcc;
    private Vector2D prevDirection;

    private Vector2D turretVec = new Vector2D(0, 0);
    public Bullet bullet = null;

    private boolean chargingBullet = false;

    Ship(Controller ctrl, Vector2D pos, Vector2D vel, Vector2D direction, int radius, Clip deathSound, Image image) {
        super(pos, vel, direction, radius, deathSound, image);
        this.action = ctrl.newAction();
        prevDirection = new Vector2D(direction);

    }

    public abstract boolean canShoot(GameObject other);

    void setInfo(double steerRate, double magAcc, double drag) {
        this.steerRate = steerRate;
        this.magAcc = magAcc;
        this.drag = drag;
    }

    public void resetPos(){
        stats.newLife();

        position = new Vector2D(initPos);
        velocity = new Vector2D(initVel);
        direction = new Vector2D(initDir);
    }

    public void update() {
        int[] dir = {action.directionX, action.directionY};
        if (!(dir[0] == 0 && dir[1] == 0)) {
            prevDirection = new Vector2D(dir[0], dir[1]);
        }

        direction = prevDirection;
        //direction.rotate(steerRate * action.turn * DT);
        velocity.addScaled(new Vector2D(action.thrustWest, action.thrustNorth), magAcc * DT);
        velocity.mult(1 - drag);

        turretVec.set(position.x + radius * 3 * Math.cos(direction.angle()), position.y + radius * 3 * Math.sin(direction.angle()));
        super.update();

        if (action.shoot && !chargingBullet) {
            mkBullet();
            chargingBullet = true;
            bulletTimer();
        }
    }

    private void bulletTimer(){
        Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                chargingBullet = false;
            }
        }, stats.getFireRate());

    }

    private void mkBullet() {
        //init the bullet just outside the turret.

        System.out.println("making bullet");
        Vector2D bulVel = new Vector2D(velocity);
        System.out.println("dir.x=" + direction.x);
        System.out.println("dir.y=" + direction.y);
        System.out.println("vel.x=" + velocity.x);
        System.out.println("vel.y=" + velocity.y);
        bulVel.addScaled(direction, Bullet.MUZZLE_VEL);
        bullet = new Bullet(new Vector2D(turretVec), bulVel, new Vector2D(direction), this);
        SoundManager.fire();
    }
}
