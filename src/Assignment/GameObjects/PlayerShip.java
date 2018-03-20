package Assignment.GameObjects;

import Assignment.GameObjects.Enemies.Enemy;
import Assignment.GameObjects.Enemies.Saucer;
import Assignment.GameObjects.Enemies.Zapper;
import Assignment.GameObjects.Projectiles.Bullet;
import Assignment.GameObjects.Projectiles.Projectile;
import Assignment.GameObjects.Projectiles.Rocket;
import Assignment.Utilities.*;
import Assignment.Map.MapHelper;
import Assignment.Utilities.Controllers.PlayerControllers.Action;
import Assignment.Utilities.Controllers.PlayerControllers.Controller;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static Assignment.Other.Constants.*;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 29/01/2018.
 */

//TODO: Make controlling the ship smoother. OR! Make items which make the handling smoother
public class PlayerShip extends GameObject implements Turret {
    private static final Image IMAGE = Sprite.PLAYER_SHIP;

    private Action action;
    private static int MARGIN = 15;

    private Vector2D prevDirection;


    private static final int HEIGHT = 30;
    private static final int WIDTH = (int) (HEIGHT * 1.26);
    private static final Clip DEATH_SOUND = SoundManager.bangMedium;

    //Initial vectors. Position, Velocity, Direction.
    private static final Vector2D INIT_POS = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final Vector2D INIT_DIR = new Vector2D(0, -1);

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 400;

    //Max speed
    public static final double MAX_SPEED = 300;


    //STATS
    private static final int INIT_ARMOUR = 500;
    private static final int INIT_LIVES = 3;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 500;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 300;
    private static final int BULLET_DAMAGE = 15;
    private static final int CONTACT_DAMAGE = 0;
    private static final int SCRAP_ON_DEATH = 0;
    //END OF STATS

    //Te
    private static final long DMG_INTERVAL = 1500;
    private boolean chargingBullet = false;

    private MapHelper mapHelper;
    private int[] mapPos;

    //ZAPPERS
    private static final int DIR_SWITCH_FOR_ZAPPERS_TO_DETACH = 10;
    private List<Zapper> attachedZappers = new CopyOnWriteArrayList<>();
    private boolean dealingWithZappers = false;
    private int zapperDamage = 0;


    //Vector of where the coinBullets are fired
    private Vector2D turretVec = new Vector2D(0, 0);

    private List<Projectile> bullets = new ArrayList<>();

    public PlayerShip(Controller ctrl) {
        super(INIT_POS, INIT_VEL, INIT_DIR, WIDTH, HEIGHT, DEATH_SOUND, IMAGE);
        action = ctrl.action();
        setStats();
        prevDirection = new Vector2D(direction);
        //timeOutInvincible();
    }

    public void addZapper(Zapper zapper) {
        attachedZappers.add(zapper);
        calcZapperDamage();
    }

    public void removeAllZappers() {
        attachedZappers.forEach(Zapper::detachFromShip);
        attachedZappers.clear();
        calcZapperDamage();
    }

    private void calcZapperDamage() {
        int count = 0;
        for (Zapper z : attachedZappers) count -= z.getStats().getBulletDamage();
        zapperDamage = count;
    }

    public void removeZapper(Zapper zapper) {
        attachedZappers.remove(zapper);
        calcZapperDamage();
    }

    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }

    public void resetPosition() {
        mapPos = mapHelper.getMapPos();
        position = new Vector2D(initPos);
        velocity = new Vector2D(initVel);
        direction = new Vector2D(initDir);
    }


    public void setMapHelper(MapHelper mh) {
        this.mapHelper = mh;
        mapPos = mapHelper.getMapPos();
    }

    public int[] getMapPos() {
        return mapPos;
    }

    public void update() {

        turretVec.set(position.x + radius * 0.8 * Math.cos(direction.angle()), position.y + radius * 0.8 * Math.sin(direction.angle()));

        if (zapperDamage != 0 && !dealingWithZappers) {
            System.out.println("dealing with zappers");
            dealWithZappers();
        }

        if (!switchingRooms) {
            if (position.x <= MARGIN) {
                if (!canSwitchRooms(1, -1)) {
                    position.x = 0;
                    velocity.x *= WALL_REFLECT;
                }
            } else if (position.x >= FRAME_WIDTH - MARGIN) {
                if (!canSwitchRooms(1, 1)) {
                    position.x = FRAME_WIDTH;
                    velocity.x *= WALL_REFLECT;
                }
            } else if (position.y <= MARGIN) {
                if (!canSwitchRooms(0, -1)) {
                    position.y = 0;
                    velocity.y *= WALL_REFLECT;
                }
            } else if (position.y >= FRAME_HEIGHT - MARGIN) {
                if (!canSwitchRooms(0, 1)) {
                    position.y = FRAME_HEIGHT;
                    velocity.y *= WALL_REFLECT;
                }
            }
        }

        int[] dir = {action.directionX, action.directionY};
        if (!(dir[0] == 0 && dir[1] == 0)) {
            prevDirection = new Vector2D(dir[0], dir[1]);
        }

        direction = prevDirection;
        velocity.addScaled(new Vector2D(action.thrustWest, action.thrustNorth), MAG_ACC * DT);
        if (velocity.mag() > MAX_SPEED) {
            setMaxSpeed();
        }
        velocity.addScaled(gravitationalPull, DT);


        if (action.shoot && !chargingBullet) {
            makeBullet(0);
        }
        field.update(this, DT);
        super.update();
    }

    private void setMaxSpeed() {
        velocity = new Vector2D(velocity).normalise().mult(MAX_SPEED);
    }

    public void draw(Graphics2D g) {
        if (zapperDamage != 0) {
            g.setColor(Color.RED);
            g.drawString("!!SHAKE IT OFF!!", (int) position.x - 30, (int) position.y - height - 10);
        }

        super.draw(g);
    }

    private void dealWithZappers() {
        dealingWithZappers = true;


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getStats().addArmour(zapperDamage);
            }
        }, 0, 2000);


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (dealingWithZappers) {

                    //Checking how many direction-switches the player does in a second
                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        int count = 0;
                        int clickCount = 0;
                        Vector2D prevDir = new Vector2D(direction);

                        @Override
                        public void run() {
                            if (count * DELAY >= 1000) {
                                if (clickCount >= DIR_SWITCH_FOR_ZAPPERS_TO_DETACH) {
                                    removeAllZappers();
                                    dealingWithZappers = false;
                                }
                                cancel();
                            }

                            if (!prevDir.equals(direction)) {
                                clickCount++;
                                System.out.println("click count=" + clickCount);
                                prevDir = new Vector2D(direction);
                            }
                            count++;
                        }
                    }, 0, DELAY);
                }
            }
        }, 0, 1000);
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
        switchingRooms = true;

        mapHelper.setMapPos(mapPos);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        Vector2D newPos = new Vector2D(position);


        if (position.x <= MARGIN) {
            newPos.x = FRAME_WIDTH - cellSize - radius;
            position.x = FRAME_WIDTH - 1;
            direction.set(-1, 0);
        } else if (position.x >= FRAME_WIDTH - MARGIN) {
            newPos.x = cellSize + radius;
            position.x = 1;
            direction.set(1, 0);
        } else if (position.y <= MARGIN) {
            newPos.y = FRAME_HEIGHT - cellSize - radius;
            position.y = FRAME_HEIGHT - 1;
            direction.set(0, -1);
        } else {
            newPos.y = cellSize + radius;
            position.y = 1;
            direction.set(0, 1);
        }

        Animation.moveObject(this, newPos, SWITCH_ROOM_ANIMATION_DURATION);
    }


    @Override
    public void timeOutInvincible() {
        isInvincible = true;
        new Timer().schedule(new TimerTask() {
            public void run() {
                isInvincible = false;
            }
        }, DMG_INTERVAL);

    }


    @Override
    public void hitDetected(GameObject other) {
        if (other instanceof BlackHole) {
            other.hitDetected(this);
            return;
        }
        HitDetection.ContactHit(this, other);
    }

    private void bulletTimer() {
        chargingBullet = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                chargingBullet = false;
            }
        }, stats.getFireRate());
    }

    public void mkBullet() {
        //init the bullet just outside the turret.
    }

    @Override
    public List<Projectile> getProjectiles() {
        return bullets;
    }
    @Override
    public void clearProjectiles() {
        bullets.clear();
    }
    @Override
    public void makeBullet(double angle) {
        SoundManager.fire();
        Bullet bullet = new Bullet(this, new Vector2D(turretVec), VEC_PLACEHOLDER, new Vector2D(direction));
        bullet.velocity = new Vector2D(velocity).addScaled(direction, stats.getBulletSpeed());

        bullet.setField(field);
        bullets.add(bullet);
        SoundManager.fire();
        bulletTimer();

    }

    public boolean canShoot(GameObject other) {
        return other instanceof Enemy || other instanceof Saucer;
    }

    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof DoorButton || other instanceof Enemy || other instanceof Saucer || (other instanceof Projectile && !(other instanceof Bullet));
    }
}
