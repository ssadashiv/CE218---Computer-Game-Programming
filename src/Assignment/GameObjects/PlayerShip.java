package Assignment.GameObjects;

import Assignment.MainGame.MapHelper;
import Assignment.Utilities.Vector2D;
import Assignment.MainGame.Game;
import Assignment.Utilities.Controllers.Controller;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.*;

/**
 * Created by el16035 on 29/01/2018.
 */
public class PlayerShip extends Ship{
    private static final int RADIUS = 15;
    private static final Clip DEATH_SOUND = SoundManager.bangMedium;
    private static final Image IMAGE = Sprite.PLAYER_SHIP;

    private static final Vector2D INIT_POS = new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2);
    private static final Vector2D INIT_VEL = new Vector2D(0,0);
    private static final Vector2D INIT_DIR = new Vector2D(-1, -1);

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Constant speed loss factor
    private static final double DRAG = 0.01;

    boolean invincible = true;
    private int countDown = 3;

    private MapHelper mapHelper;
    private int[] mapPos;

    public PlayerShip(Controller ctrl) {
        super(ctrl, INIT_POS, INIT_VEL, INIT_DIR, RADIUS, DEATH_SOUND, IMAGE);
        setInfo(STEER_RATE, MAG_ACC, DRAG);
        timeOutInvincible();
    }


    public void setMapHelper(MapHelper mh){
        this.mapHelper = mh;
        mapPos = mapHelper.getMapPos();
    }

    public int[] getMapPos(){
        return mapPos;
    }

    public void update(){
        if (position.x < 0){
            switchMapPos(1, -1);
        }else if (position.x > FRAME_WIDTH){
            switchMapPos(1, 1);
        }else if (position.y < 0){
            switchMapPos(0, -1);
        }else if (position.y > FRAME_HEIGHT){
            switchMapPos(0, 1);
        }

        super.update();
    }

    //method to switch map positions. returns true it is a success
    private boolean switchMapPos(int index, int addInt){
        //go to scene to left
        int[] newPos = mapPos;
        newPos[index] += addInt;
        if (mapHelper.getMap(newPos) != null){
            mapHelper.setMapPos(newPos);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
            System.out.println("WRAPPING");
            return true;
        }

        return false;
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

    public boolean canHit(GameObject other) {
        return false;
    }

    public boolean canShoot(GameObject other) {
        return false;
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
