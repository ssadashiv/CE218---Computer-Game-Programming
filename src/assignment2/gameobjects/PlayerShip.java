package assignment2.gameobjects;

import assignment2.gameobjects.enemies.Enemy;
import assignment2.gameobjects.enemies.Zapper;
import assignment2.gameobjects.wormholes.WhiteHole;
import assignment2.gameobjects.wormholes.Wormhole;
import assignment2.gameobjects.obstacles.DoorButton;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.projectiles.Projectile;
import assignment2.utilities.*;
import assignment2.map.MapHelper;
import assignment2.utilities.controllers.playercontrollers.Action;
import assignment2.utilities.controllers.playercontrollers.Controller;
import assignment2.utilities.objectassist.Animation;
import assignment2.utilities.objectassist.HitDetection;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static assignment2.other.Constants.*;
import static assignment2.other.SharedValues.cellSize;

/**
 * Created by el16035 on 29/01/2018.
 */

//The class for the PlayerShip.
public class PlayerShip extends GameObject implements Turret {
    private static final Image IMAGE = Sprite.PLAYER_SHIP;
    private static final Image BOOSTER_IMG = Sprite.PLAYER_BOOSTER;

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
    private static final double MAG_ACC = 500;

    //Max speed
    private static final double MAX_SPEED = 250;

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

    //Interval between when the player can take damage.
    private static final long DMG_INTERVAL = 800;
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

    private int scrapMetal = 0;
    private int score = 0;

    public boolean bossKilled = false;

    public PlayerShip(Controller ctrl) {
        super(INIT_POS, INIT_VEL, INIT_DIR, WIDTH, HEIGHT, DEATH_SOUND, IMAGE);
        action = ctrl.action();
        setStats();
        prevDirection = new Vector2D(direction);
        //timeOutInvincible();
    }

    //when a zapper attaches itself to the ship.
    public void addZapper(Zapper zapper) {
        attachedZappers.add(zapper);
        calcZapperDamage();
    }

    //Remove all the zappers which is attached to the ship.
    public void removeAllZappers() {
        attachedZappers.forEach(Zapper::detachFromShip);
        attachedZappers.clear();
        zapperDamage = 0;
    }

    //Method which gets called when the player buys upgrades in the shop.
    public void useScrapMetal(int amount){
        scrapMetal -= amount;
    }

    //getter methods
    public int getScrapMetal() {
        return scrapMetal;
    }
    public int getScore() {
        return score;
    }

    public void bossKilled(){
        bossKilled = true;
    }

    //Increase the Zapper damage for every zapper that is attached to the player.
    private void calcZapperDamage() {
        zapperDamage = 0;
        for (Zapper z : attachedZappers) zapperDamage -= z.getStats().getBulletDamage();
    }


    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }

    public void newGame(){
        scrapMetal = 0;
        score = 0;
        dead = false;
        stats.resetStats();
        resetPosition(false);
    }

    public void newLife(){
        stats.newLife();
        dead = false;
        resetPosition(false);
        timeOutInvincible();
    }

    //resets the position of the ship to the initial starting point of the map.
    public void resetPosition(boolean newLevel) {
        int[] newPos = mapHelper.getInitPos();
        mapPos = new int[]{newPos[0], newPos[1]};
        mapHelper.updateMap(newLevel);
        position = new Vector2D(INIT_POS);
        velocity = new Vector2D(INIT_VEL);
        direction = new Vector2D(INIT_DIR);
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
        if (!attachedZappers.isEmpty()) {
            g.setColor(Color.RED);
            g.drawString("!!SHAKE IT OFF!!", (int) position.x - 30, (int) position.y - height - 10);
        }

        super.draw(g);
    }

    //A method which deals with the zappers. If the player changes directions X times during 1 second, all the zappers fall off.
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
        for (Zapper z : attachedZappers) z.dead = true;
        attachedZappers.clear();
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

    //The player has an interval of X seconds between hits when they cant be damaged.
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
        if (other instanceof ShopMan){
            other.hitDetected(this);
            return;
        }

        if (other instanceof Wormhole) {
            other.hitDetected(this);
            return;
        }

        if (other instanceof ScrapMetal){
            pickUpScrapMetal(((ScrapMetal) other).getAmount());
            ((ScrapMetal) other).setAmount(0);
            other.dead = true;
            return;
        }
        HitDetection.ContactHit(this, other);
        timeOutInvincible();
    }

    //Setting a timer for the fire rate of the ship.
    private void bulletTimer() {
        chargingBullet = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                chargingBullet = false;
            }
        }, stats.getFireRate());
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
        Bullet bullet = new Bullet(this, field,  new Vector2D(turretVec), VEC_PLACEHOLDER, new Vector2D(direction));
        bullet.velocity = new Vector2D(velocity).addScaled(direction, stats.getBulletSpeed());
        bullets.add(bullet);
        bulletTimer();

    }

    public boolean canHit(GameObject other) {
        return other instanceof ShopMan || other instanceof Obstacle || other instanceof DoorButton || other instanceof Enemy || (other instanceof Projectile && !(other instanceof Bullet) || (other instanceof Wormhole && !(other instanceof WhiteHole)));
    }


    void pickUpScrapMetal(int amount){
        scrapMetal += amount;
        score += amount*5;
    }
}
