package game2.game;

import game2.utilities.controllers.Controller;
import game2.utilities.SoundManager;
import game2.utilities.Sprite;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 27/02/2018.
 */
public class Saucer extends Ship {
    private static final int RADIUS = 30;
    private static final Clip DEATH_SOUND = SoundManager.saucerSmall;
    private static final Image IMAGE = Sprite.MEDIUM_SAUCER;

    private static Vector2D initPos = new Vector2D(FRAME_WIDTH/5, FRAME_HEIGHT/5);
    private static Vector2D initVel = new Vector2D(0,0);
    private static Vector2D initDir = new Vector2D(-1, -1);

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Constant speed loss factor
    private static final double DRAG = 0.01;


    Saucer(Controller ctrl){
        super(ctrl, initPos, initVel, initDir, RADIUS, DEATH_SOUND, IMAGE);
        setInfo(STEER_RATE, MAG_ACC, DRAG);

    }

    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip || (other instanceof Bullet && ((Bullet) other).parent instanceof PlayerShip);
    }

    public boolean canShoot(GameObject other) {
        return other instanceof PlayerShip;
    }

    public int collisionHandling(GameObject other) {
        if (canHit(other)){
            if (super.collisionHandling(other) != 0){
                return 0;
            }
        }
        return 0;
    }

}
