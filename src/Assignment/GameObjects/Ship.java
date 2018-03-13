package Assignment.GameObjects;

import Assignment.Utilities.Controllers.NewAction;
import Assignment.Utilities.Controllers.Controller;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.Constants.DT;

/**
 * Created by el16035 on 27/02/2018.
 */
public abstract class Ship extends GameObject {
    private NewAction action;
    private double drag;
    private double steerRate;
    private double magAcc;
    private Vector2D prevDirection = new Vector2D(0,0);

    private Vector2D turretVec = new Vector2D(0,0);
    //Bullet bullet = null;

    Ship(Controller ctrl, Vector2D pos, Vector2D vel, Vector2D direction, int radius, Clip deathSound, Image image) {
        super(pos, vel, direction, radius, deathSound, image);
        this.action = ctrl.newAction();

    }

    public abstract boolean canShoot(GameObject other);

    void setInfo(double steerRate, double magAcc, double drag) {
        this.steerRate = steerRate;
        this.magAcc = magAcc;
        this.drag = drag;
    }

    public void update() {
        int[] dir = {action.directionX, action.directionY};
        if (!(dir[0] == 0 && dir[1] == 0)){
            prevDirection = new Vector2D(dir[0], dir[1]);
        }

        direction = prevDirection;
        //direction.rotate(steerRate * action.turn * DT);
        velocity.addScaled(new Vector2D(action.thrustWest, action.thrustNorth), magAcc * DT);
        velocity.mult(1 - drag);

        //turretVec.set(position.x + radius * 3 * Math.cos(direction.angle()), position.y + radius * 3 * Math.sin(direction.angle()));
        super.update();

       /* if (action.shoot) {
            mkBullet();
            action.shoot = false;
        }*/
    }

    /*private void mkBullet() {
        //init the bullet just outside the turret.

        Vector2D bulVel = new Vector2D(velocity);
        bulVel.addScaled(direction, Bullet.MUZZLE_VEL);
        bullet = new Bullet(new Vector2D(turretVec), bulVel, new Vector2D(direction), this);
        SoundManager.fire();
    }*/
}
