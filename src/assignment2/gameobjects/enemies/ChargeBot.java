package assignment2.gameobjects.enemies;

import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.objectassist.EnergyBar;
import assignment2.utilities.controllers.enemycontrollers.AIAction;
import assignment2.utilities.controllers.enemycontrollers.ChargeBotCtrl;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static assignment2.other.Constants.DT;
import static assignment2.other.SharedValues.cellSize;
import static assignment2.other.SharedValues.lvlDifficulty;

/**
 * Created by el16035 on 17/03/2018.
 */
/*
* An enemy AI object.
* Has an EnergyBar. The chargeBot charges at the player if the player is within its sector.
* Until the energy of the bot has depleted, this bot is invulnerable. It loses energy when it's outside the ChargeStation's charging sector.
* When the energy is empty, the player can damage the bot. It then travels back to the charging station.
* When fully charged again, the bot will be invulnerable again.
* */
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
    private static final int SCRAP_ON_DEATH = 75;
    //END OF STATS

    //Energy
    private static final int MAX_ENERGY = 2500;
    private int currentEnergy = 2500;
    private boolean runOutOfEnergy = false;

    //Position of the ChargeStation
    private ChargeStation chargeStation;
    private Vector2D chargingPos;

    //The bot charges the player if the player is within this radius of the bot.
    private int sectorRadius = 150;

    private EnergyBar energyBar;

    private AIAction ctrl;


    public ChargeBot(Vector2D position) {
        super(position, INIT_VEL, INIT_DIR, SPEED, RADIUS, RADIUS, DEATH_SOUND, IMAGE);
        setStats();

        this.energyBar = new EnergyBar(this);
        isInvincible = true;
    }

    //Sets the position of the charge station.
    public void setChargeStation(ChargeStation chargeStation) {
        this.chargeStation = chargeStation;
        this.chargingPos = chargeStation.position;
        moveBotNextToStation();
    }

    //Set the player ship as well as the controller of the ChargeBot.
    @Override
    public void setPlayerShip(PlayerShip ship) {
        ctrl = new ChargeBotCtrl(this, ship).aiAction();
        ctrl.velocity = new Vector2D(INIT_VEL);
        ctrl.direction = new Vector2D(INIT_DIR);
    }

    @Override
    public void setStats() {
        super.setStats((int) (INIT_ARMOUR * lvlDifficulty), INIT_LIVES, FIRE_RATE, (int) (BULLET_SPEED * lvlDifficulty), (int) (BULLET_DAMAGE * lvlDifficulty), (int) (CONTACT_DAMAGE * lvlDifficulty), (int) (SCRAP_ON_DEATH * lvlDifficulty));
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

        if (runOutOfEnergy) isInvincible = false;

        //Is in the charging station's sector. Gains energy
        if (isInChargingSector() && currentEnergy < MAX_ENERGY) {
            currentEnergy += 10;
            if (runOutOfEnergy) {
                if (currentEnergy >= MAX_ENERGY) {
                    currentEnergy = MAX_ENERGY;
                    isInvincible = true;
                    runOutOfEnergy = false;
                }
            }

        //Outside the sector of the charging station.
        } else if (!isInChargingSector()) {
            if (currentEnergy > 0) {
                currentEnergy -= 10;
            }
            if (currentEnergy == 0) {
                runOutOfEnergy = true;
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

        if (runOutOfEnergy) {
            drawExclamationMark(g);
        }
        super.draw(g);
    }

    //Draw ! when invulnerable
    private void drawExclamationMark(Graphics2D g) {
        g.setColor(Color.RED);
        Stroke origStroke = g.getStroke();
        g.setStroke(new BasicStroke(2f));
        g.drawString("!!", (int) energyBar.position.x + (energyBar.WIDTH / 2), (int) energyBar.position.y - 10);
        g.setStroke(origStroke);
    }
}
