package Assignment.GameObjects.Enemies;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by el16035 on 18/03/2018.
 */
public class ChargeStation extends Obstacle{
    private static final Vector2D INIT_DIR = new Vector2D(0, 1);
    private static final Vector2D INIT_VEL = new Vector2D(0, 0);
    private static final Color SECTOR_COLOR = new Color(255, 255, 0, 30);
    private static final int CHARGING_RADIUS = 50;

    private static final int RADIUS = cellSize / 4;

    private static final Clip DEATH_SOUND = SoundManager.bangLarge;
    private static final Image IMAGE = Sprite.CHARGE_STATION;

    //private Vector2D chargingPos = new Vector2D(0,0);

    private boolean isChargingBot = false;

    public ChargeStation(Vector2D position) {
        super(position, IMAGE, RADIUS, RADIUS);

    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(SECTOR_COLOR);
        g.fillOval((int)position.x - CHARGING_RADIUS, (int)position.y - CHARGING_RADIUS, CHARGING_RADIUS * 2 , CHARGING_RADIUS * 2);


        super.draw(g);
    }

    public int getChargingRadius(){
        return CHARGING_RADIUS;
    }

    @Override
    public boolean canHit(GameObject other) {
        return true;
    }
/*
    @Override
    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (overlap(other)){
                if (other instanceof Bullet && isChargingBot){
                    super.collisionHandling(other);
                }
                else{
                    HitDetection.HitObstacle(this, other);
                }
            }
        }
    }*/

}

