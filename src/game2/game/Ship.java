package game2.game;

import game2.utilities.SoundManager;
import game2.utilities.controllers.Action;
import game2.utilities.controllers.Controller;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static game2.Constants.DT;

/**
 * Created by el16035 on 27/02/2018.
 */
public abstract class Ship extends GameObject {
    private Action action;
    private double drag;
    private double steerRate;
    private double magAcc;

    private Vector2D turretVec = new Vector2D(0,0);
    Bullet bullet = null;

    Ship(Controller ctrl, Vector2D pos, Vector2D vel, Vector2D direction, int radius, Clip deathSound, Image image) {
        super(pos, vel, direction, radius, deathSound, image);
        this.action = ctrl.action();

    }

    public abstract boolean canShoot(GameObject other);

    void setInfo(double steerRate, double magAcc, double drag) {
        this.steerRate = steerRate;
        this.magAcc = magAcc;
        this.drag = drag;
    }

    void update() {
        direction.rotate(steerRate * action.turn * DT);
        velocity.addScaled(direction, magAcc * DT * action.thrust);
        velocity.mult(1 - drag);

        turretVec.set(position.x + radius * 3 * Math.cos(direction.angle()), position.y + radius * 3 * Math.sin(direction.angle()));
        super.update();

        if (action.shoot) {
            mkBullet();
            action.shoot = false;
        }
    }

    private void mkBullet() {
        //init the bullet just outside the turret.

        Vector2D bulVel = new Vector2D(velocity);
        bulVel.addScaled(direction, Bullet.MUZZLE_VEL);
        bullet = new Bullet(new Vector2D(turretVec), bulVel, new Vector2D(direction), this);
        SoundManager.fire();
    }
}
