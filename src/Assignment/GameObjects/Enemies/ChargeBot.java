package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.EnergyBar;
import Assignment.Utilities.Controllers.AIControllers.AIAction;
import Assignment.Utilities.Controllers.AIControllers.ChargeBotCtrl;
import Assignment.Utilities.Gravity.ForceFieldGravity;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.Constants.DT;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 17/03/2018.
 */
public class ChargeBot extends Enemy {
    private static final Clip DEATH_SOUND = SoundManager.bangLarge;
    private static final Image IMAGE = Sprite.CHARGE_BOT;

    private static final Vector2D INIT_DIR = new Vector2D(0, 1);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final int RADIUS = cellSize / 2;
    private static final int SPEED = 100;



    //STATS
    private static final int INIT_ARMOUR = 50;
    private static final int INIT_LIVES = 1;

    //The fire rate of the bullet in milliseconds
    private static final int FIRE_RATE = 0;

    //the bullet speed. x pixels in a second
    private static final int BULLET_SPEED = 0;
    private static final int BULLET_DAMAGE = 0;
    private static final int CONTACT_DAMAGE = 30;
    private static final int SCRAP_ON_DEATH = 20;
    //END OF STATS

    //Energy
    private static final int MAX_ENERGY = 2500;
    private int currentEnergy = 2500;
    private boolean runOutOfEnergy = false;

    private ChargeStation chargeStation;
    private Vector2D chargingPos;



    private int sectorRadius = 150;

    private EnergyBar energyBar;

    private AIAction ctrl;


    public ChargeBot( PlayerShip ship, Vector2D position) {
        super(position, INIT_VEL, INIT_DIR, SPEED, RADIUS, RADIUS, DEATH_SOUND, IMAGE);
        setStats();
        ctrl = new ChargeBotCtrl(this, ship).aiAction();
        ctrl.velocity = new Vector2D(INIT_VEL);
        ctrl.direction = new Vector2D(INIT_DIR);
        this.energyBar = new EnergyBar(this);
        isInvincible = true;
    }

    public void setChargeStation(ChargeStation chargeStation) {
        this.chargeStation = chargeStation;
        this.chargingPos = chargeStation.position;
        moveBotNextToStation();
    }

    @Override
    public void setStats() {
        super.setStats(INIT_ARMOUR, INIT_LIVES, FIRE_RATE, BULLET_SPEED, BULLET_DAMAGE, CONTACT_DAMAGE, SCRAP_ON_DEATH);
    }

    public boolean hasRunOutOfEnergy() {
        return runOutOfEnergy;
    }

    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
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

    private boolean isInChargingSector() {
        return position.dist(chargingPos) <= chargeStation.getChargingRadius();
    }

    @Override
    public void update() {
        field.update(this, DT);
        if (isInChargingSector() && currentEnergy < MAX_ENERGY) {
            currentEnergy+= 10;

            if (runOutOfEnergy) {
                if (currentEnergy >= MAX_ENERGY){
                    currentEnergy = MAX_ENERGY;
                    isInvincible = true;
                    runOutOfEnergy = false;
                }else{
                    isInvincible = false;
                }
            }
        } else if (!isInChargingSector()) {
            if (currentEnergy > 0) {
                currentEnergy -= 10;
            }
            if (currentEnergy == 0) {
                runOutOfEnergy = true;
                System.out.println("EMPTY");
            }

        }

        velocity = ctrl.velocity;
        direction = ctrl.direction;

        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(255, 255, 0, 60));
        g.drawOval((int) position.x - sectorRadius, (int) position.y - sectorRadius, sectorRadius * 2, sectorRadius * 2);
        energyBar.draw(g);

        if (runOutOfEnergy){
           drawExclamationMark(g);
        }
        super.draw(g);
    }

    private void drawExclamationMark(Graphics2D g){
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2f));
        g.drawString("!!", (int) energyBar.position.x + (energyBar.WIDTH/2), (int) energyBar.position.y - 10);
    }
}
