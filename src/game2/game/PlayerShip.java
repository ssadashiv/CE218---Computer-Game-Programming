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

    private static Vector2D initPos = new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2);
    private static Vector2D initVel = new Vector2D(0,0);
    private static Vector2D initDir = new Vector2D(-1, -1);

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Constant speed loss factor
    private static final double DRAG = 0.01;

    boolean invincible = true;
    private int countDown = 3;

    PlayerShip(Controller ctrl) {
        super(ctrl, initPos, initVel, initDir, RADIUS, DEATH_SOUND, IMAGE);

        setInfo(STEER_RATE, MAG_ACC, DRAG);
    }




    public boolean canHit(GameObject other) {
        return (other instanceof Asteroid) && !invincible;
    }

    public boolean canShoot(GameObject other) {
        return other instanceof Asteroid || other instanceof Saucer;
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
