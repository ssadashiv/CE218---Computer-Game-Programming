package assignment2.gameobjects.enemies;

import assignment2.gameobjects.*;
import assignment2.gameobjects.wormholes.BlackHole;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.projectiles.CoinBullet;
import assignment2.gameobjects.projectiles.Projectile;
import assignment2.utilities.controllers.enemycontrollers.CoinMoneyManCtrl;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static assignment2.other.Constants.DT;
import static assignment2.other.Constants.FRAME_WIDTH;
import static assignment2.other.SharedValues.lvlDifficulty;

/**
 * Created by el16035 on 20/03/2018.
 */

//A Boss. Throws coins at the Player in 4 directions.
public class CoinMoneyMan extends Enemy implements Turret {
    private static final Vector2D VELOCITY = new Vector2D(0, 0);
    private static final Vector2D DIRECTION = new Vector2D(0, -1);
    private static final int MAX_SPEED = 100;

    private static final Image IMAGE = Sprite.COIN_MONEY_MAN;
    private static final Clip DEATH_SOUND = SoundManager.bangLarge;

    private static final int SIZE = FRAME_WIDTH / 4;
    //STATS
    private static final int INIT_ARMOUR = 800;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 4000;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 200;
    private static final int BULLET_DAMAGE = 30;
    private static final int CONTACT_DAMAGE = 40;
    private static final int SCRAP_ON_DEATH = 550;
    //END OF STATS

    private List<Projectile> coinBullets = new ArrayList<>();


    public CoinMoneyMan(Vector2D position) {
        super(position, VELOCITY, DIRECTION, 0, SIZE, SIZE, DEATH_SOUND, IMAGE);
        setStats();
    }

    @Override
    public void setStats() {
        super.setStats((int) (INIT_ARMOUR * lvlDifficulty), INIT_LIVES, FIRE_RATE, (int) (BULLET_SPEED * lvlDifficulty), (int) (BULLET_DAMAGE * lvlDifficulty), (int) (CONTACT_DAMAGE * lvlDifficulty), (int) (SCRAP_ON_DEATH * lvlDifficulty));
    }


    public void update() {
        field.update(this, DT);
        if (velocity.mag() > MAX_SPEED) {
            setMaxSpeed();
        }
        super.update();
    }

    @Override
    public void setPlayerShip(PlayerShip ship) {
        super.setPlayerShip(ship);
        new CoinMoneyManCtrl(this).aiAction();
    }

    private void setMaxSpeed() {
        velocity = new Vector2D(velocity).normalise().mult(MAX_SPEED);
    }

    public void dropScrapMetal(int amount) {
        playerShip.bossKilled();
        super.dropScrapMetal(amount);
    }

    @Override
    public void hitDetected(GameObject other) {
        if (other instanceof BlackHole) {
            other.hitDetected(this);
            return;
        }
        HitDetection.ContactHit(this, other);
    }


    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof Bullet || other instanceof BlackHole || other instanceof PlayerShip;
    }

    @Override
    public void makeBullet(double angle) {
        Vector2D startPos = new Vector2D(position.x + (width / 2) * Math.cos(angle), position.y + (width / 2) * Math.sin(angle));
        Vector2D bulVel = new Vector2D(startPos).subtract(position).normalise().mult(stats.getBulletSpeed());
        CoinBullet coinBullet = new CoinBullet(this, field, startPos, bulVel, new Vector2D(direction));
        coinBullets.add(coinBullet);
    }


    @Override
    public List<Projectile> getProjectiles() {
        return coinBullets;
    }

    @Override
    public void clearProjectiles() {
        coinBullets.clear();
    }
}