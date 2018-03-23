package assignment2.gameobjects.enemies;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

import static assignment2.other.SharedValues.cellSize;

/**
 * Created by el16035 on 18/03/2018.
 */

//The chargestation of the ChargeBot
public class ChargeStation extends Obstacle{
    private static final Color SECTOR_COLOR = new Color(255, 255, 0, 30);
    private static final int CHARGING_RADIUS = 50;
    private static final int RADIUS = cellSize / 4;
    private static final Image IMAGE = Sprite.CHARGE_STATION;


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
}

