package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.*;
import Assignment.GameObjects.Projectiles.Bullet;
import Assignment.GameObjects.Projectiles.CoinBullet;
import Assignment.GameObjects.Projectiles.Projectile;
import Assignment.Utilities.Controllers.AIControllers.CoinMoneyManCtrl;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Assignment.Other.Constants.DT;
import static Assignment.Other.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 20/03/2018.
 */

//A Boss. Throws coins at the Player.
public class CoinMoneyMan extends Enemy implements Turret {
    private static final Vector2D VELOCITY = new Vector2D(0, 0);
    private static final Vector2D DIRECTION = new Vector2D(0, -1);
    private static final int MAX_SPEED = 100;

    private static final Image IMAGE = Sprite.COIN_MONEY_MAN;
    private static final Clip DEATH_SOUND = SoundManager.bangLarge;

    private static final int SIZE = FRAME_WIDTH / 4;
    //STATS
    private static final int INIT_ARMOUR = 1000;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 3000;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 200;
    private static final int BULLET_DAMAGE = 40;
    private static final int CONTACT_DAMAGE = 40;
    private static final int SCRAP_ON_DEATH = 500;
    //END OF STATS

    private List<Projectile> coinBullets = new ArrayList<>();

    public CoinMoneyMan(Vector2D position) {
        super(position, VELOCITY, DIRECTION, 0, SIZE, SIZE, DEATH_SOUND, IMAGE);
        setStats();
        new CoinMoneyManCtrl(this).aiAction();

    }

    @Override
    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }


    public void update() {
        field.update(this, DT);
        if (velocity.mag() > MAX_SPEED) {
            setMaxSpeed();
        }
        super.update();
    }

    private void setMaxSpeed() {
        velocity = new Vector2D(velocity).normalise().mult(MAX_SPEED);
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
        SoundManager.fire();
        Vector2D startPos = new Vector2D(position.x + (width / 2) * Math.cos(angle), position.y + (width / 2) * Math.sin(angle));
        Vector2D bulVel = new Vector2D(startPos).subtract(position).normalise().mult(stats.getBulletSpeed());
        CoinBullet coinBullet = new CoinBullet(this, startPos, bulVel, new Vector2D(direction));
        coinBullet.setField(field);
        coinBullets.add(coinBullet);

        SoundManager.fire();
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