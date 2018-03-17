package Assignment.GameObjects;

import Assignment.Utilities.Animation;
import Assignment.Utilities.Map.MapHelper;
import Assignment.Utilities.Vector2D;
import Assignment.Utilities.Controllers.PlayerControllers.Controller;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.*;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 29/01/2018.
 */
public class PlayerShip extends Ship {
    private static final int RADIUS = 15;
    private static final Clip DEATH_SOUND = SoundManager.bangMedium;
    private static final Image IMAGE = Sprite.PLAYER_SHIP;

    //Initial vectors. Position, Velocity, Direction.
    private static final Vector2D INIT_POS = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final Vector2D INIT_DIR = new Vector2D(1, 0);

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Stats
    private static final int INIT_ARMOUR = 100;
    private static final int INIT_LIVES = 3;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 500;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 10;
    private static final int BULLET_DAMAGE = 15;
    private static final int CONTACT_DAMAGE = 30;
    private static final int SCRAP_ON_DEATH = 0;

    boolean invincible = true;
    private int countDown = 3;

    private MapHelper mapHelper;
    private int[] mapPos;

    public PlayerShip(Controller ctrl) {
        super(ctrl, INIT_POS, INIT_VEL, INIT_DIR, RADIUS, DEATH_SOUND, IMAGE);
        setInfo(STEER_RATE, MAG_ACC, DRAG);
        setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);

        timeOutInvincible();
    }


    public void resetPosition() {
        mapPos = mapHelper.getMapPos();
        super.resetPosition();
    }


    public void setMapHelper(MapHelper mh) {
        this.mapHelper = mh;
        mapPos = mapHelper.getMapPos();
    }


    public int[] getMapPos() {
        return mapPos;
    }

    public void update() {
        if (position.x < 0) {
            if (!canSwitchRooms(1, -1)) {
                position.x = 0;
                velocity.x *= WALL_REFLECT;
            }
        } else if (position.x > FRAME_WIDTH) {
            if (!canSwitchRooms(1, 1)) {
                position.x = FRAME_WIDTH;
                velocity.x *= WALL_REFLECT;
            }
        } else if (position.y < 0) {
            if (!canSwitchRooms(0, -1)) {
                position.y = 0;
                velocity.y *= WALL_REFLECT;
            }
        } else if (position.y > FRAME_HEIGHT) {
            if (!canSwitchRooms(0, 1)) {
                position.y = FRAME_HEIGHT;
                velocity.y *= WALL_REFLECT;
            }

        }

        super.update();
    }

    //method to switch map positions. returns true if it is a success
    private boolean canSwitchRooms(int index, int addInt) {
        //go to scene to left
        mapPos[index] += addInt;
        if (mapHelper.doesRoomExist(mapPos)) {
            switchRoom();
            return true;
        }
        //reset if not valid map position
        mapPos[index] -= addInt;

        return false;
    }

    //Method to make the ship switch maps
    private void switchRoom() {
        mapHelper.setMapPos(mapPos);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        Vector2D newPos = new Vector2D(position);


        if (position.x > FRAME_WIDTH / 4 * 3) {
            newPos.x = FRAME_WIDTH - cellSize - radius;
            direction.set(-1, 0);
        } else if (position.x < FRAME_WIDTH / 4) {
            newPos.x = cellSize + radius;
            direction.set(1, 0);
        } else if (position.y > FRAME_HEIGHT / 4 * 3) {
            newPos.y = FRAME_HEIGHT - cellSize - radius;
            direction.set(0, -1);
        } else {
            newPos.y = cellSize + radius;
            direction.set(0, 1);
        }

        Animation.moveObject(this, newPos, SWITCH_ROOM_ANIMATION_DURATION);
    }

    private void timeOutInvincible() {
        int delay = 1000;
        int period = 1000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                countDown--;
                if (countDown == 0) invincible = false;
            }
        }, delay, period);
    }

    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof DoorButton;
    }

    public boolean canShoot(GameObject other) {
        return false;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) super.collisionHandling(other);

        /* if (canHit(other)){
            if (super.collisionHandling(other) != 0){
                return 10;
            }
        }
        return 0;*/
    }


}
