package game2.game;

import game2.utilities.controllers.Controller;
import game2.utilities.SoundManager;
import game2.utilities.Sprite;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static game2.Constants.*;

/**
 * Created by el16035 on 29/01/2018.
 */
class PlayerShip extends Ship{
    private static final int RADIUS = 15;
    private static final Clip DEATH_SOUND = SoundManager.bangMedium;
    private static final Image IMAGE = Sprite.PLAYER_SHIP;

    private static final Color SHIP_COLOR = Color.cyan;
    private static Vector2D initPos = new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2);
    private static Vector2D initVel = new Vector2D(0,0);
    private static Vector2D initDir = new Vector2D(-1, -1);

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Constant speed loss factor
    private static final double DRAG = 0.01;

    private Vector2D turretVec;

    boolean invincible = true;
    private int countDown = 3;

    Bullet bullet = null;

    PlayerShip(Controller ctrl) {
        super(ctrl, SHIP_COLOR, initPos, initVel, initDir, RADIUS, DEATH_SOUND, IMAGE);
        turretVec = new Vector2D(0, 0);
        timeOutInvincible();
    }
    void update() {
        System.out.println("TURN: " + action.turn);
        direction.rotate(STEER_RATE * action.turn * DT);
        velocity.addScaled(direction, MAG_ACC * DT * action.thrust);
        velocity.mult(1-DRAG);

        setTurretPos(position.x + RADIUS * 3 * Math.cos(direction.angle()), position.y + RADIUS * 3 * Math.sin(direction.angle()));
        super.update();

        if (action.shoot){
            mkBullet();
            action.shoot = false;
        }
    }
    private void timeOutInvincible(){
        int delay = 1000;
        int period = 1000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                countDown --;
                if (countDown== 0) invincible = false;
            }
        }, delay, period);
    }

    private void setTurretPos(double x, double y) {
        turretVec.set(x, y);
    }

    private void mkBullet(){
        //init the bullet just outside the turret.

        Vector2D bulVel = new Vector2D(velocity);
        bulVel.addScaled(direction, Bullet.MUZZLE_VEL);
        bullet = new Bullet(new Vector2D(turretVec), bulVel, new Vector2D(direction), this);
        action.shoot = false;

        SoundManager.fire();
    }

    public boolean canHit(GameObject other) {
        return (other instanceof Asteroid) && !invincible;
    }

    public int collisionHandling(GameObject other) {
        if (canHit(other)){
            if (super.collisionHandling(other) != 0){
                return 10;
            }
        }
        return 0;
    }
}
