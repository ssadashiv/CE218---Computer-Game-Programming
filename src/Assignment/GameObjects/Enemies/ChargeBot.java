package Assignment.GameObjects.Enemies;

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
public class ChargeBot extends GameObject {
    AIAction ctrl;
    private static final Vector2D INIT_DIR = new Vector2D(0, 1);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final int RADIUS = cellSize / 4;

    private static final Clip DEATH_SOUND = SoundManager.bangLarge;
    private static final Image IMAGE = Sprite.CHARGE_BOT;

    private Vector2D chargingPos = new Vector2D(0,0);

    public double speed = 100;
    private int sectorRadius = 150;

    public ChargeBot(PlayerShip ship, Vector2D position) {
        super(position,INIT_VEL, INIT_DIR, RADIUS, DEATH_SOUND, IMAGE);
        ctrl = new ChargeBotCtrl(this, ship).aiAction();
        ctrl.velocity = new Vector2D(INIT_VEL);
        ctrl.direction = new Vector2D(INIT_DIR);

        chargingPos.set(100,100);
        System.out.println("pos+"+position.toString());
        System.out.println("chargingPos+"+ chargingPos.toString());
    }

    public Vector2D getChargingStationPos() {
        System.out.println("chargingPos="+ chargingPos);
        return chargingPos;
    }

    public int getSectorRadius(){
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

        g.setColor(new Color(255,255,0, 50));
        g.fillOval((int)position.x - sectorRadius, (int)position.y - sectorRadius, sectorRadius * 2 , sectorRadius * 2);


        super.draw(g);
    }



    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Obstacle || other instanceof PlayerShip;
    }

    @Override
    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (overlap(other)) {
                String dir = HitDetection.whichDirection(this, other);

                if (dir.equals(HitDetection.NORTH) || dir.equals(HitDetection.SOUTH)){
                    ctrl.velocity.y *= -1;
                }else{
                    ctrl.velocity.x *= -1;
                }
            }
        }
    }

}
