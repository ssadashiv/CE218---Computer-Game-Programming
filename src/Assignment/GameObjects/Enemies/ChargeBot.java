package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Controllers.AIControllers.AIAction;
import Assignment.Utilities.Controllers.AIControllers.ChargeBotCtrl;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 17/03/2018.
 */
public class ChargeBot extends Enemy {
    AIAction ctrl;
    private static final Vector2D INIT_DIR = new Vector2D(0, 1);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final int RADIUS = cellSize / 2;

    private static final Clip DEATH_SOUND = SoundManager.bangLarge;
    private static final Image IMAGE = Sprite.CHARGE_BOT;


    //Stats
    private static final int INIT_ARMOUR = 50;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 0;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 0;
    private static final int BULLET_DAMAGE = 0;
    private static final int CONTACT_DAMAGE = 30;
    private static final int SCRAP_ON_DEATH = 20;

    private ChargeStation chargeStation;
    private Vector2D chargingPos;


    public double speed = 100;
    private int sectorRadius = 150;


    public ChargeBot(PlayerShip ship, ChargeStation chargeStation, Vector2D position) {
        super(position, INIT_VEL, INIT_DIR, RADIUS, RADIUS, DEATH_SOUND, IMAGE);
        ctrl = new ChargeBotCtrl(this, ship).aiAction();
        ctrl.velocity = new Vector2D(INIT_VEL);
        ctrl.direction = new Vector2D(INIT_DIR);

        this.chargeStation = chargeStation;
        this.chargingPos = chargeStation.position;

        moveBotNextToStation();
        setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);

    }

    private void moveBotNextToStation() {
        position.x -= chargeStation.width;
    }

    public Vector2D getChargingStationPos() {
        return chargingPos;
    }

    public int getSectorRadius() {
        return sectorRadius;
    }

    @Override
    public void update() {
        velocity = ctrl.velocity;
        direction = ctrl.direction;

        super.update();
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(new Color(255, 255, 0, 50));
        g.fillOval((int) position.x - sectorRadius, (int) position.y - sectorRadius, sectorRadius * 2, sectorRadius * 2);
        super.draw(g);
    }


/*

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof PlayerShip;
    }
*/

    @Override
    public boolean canHit(GameObject other) {
        if (other instanceof Bullet) {
            if (((Bullet) other).getParent() instanceof PlayerShip) {
                return true;
            }
        }

        return other instanceof Obstacle || other instanceof PlayerShip;
    }
}
