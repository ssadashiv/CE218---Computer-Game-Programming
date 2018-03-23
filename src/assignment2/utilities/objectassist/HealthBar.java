package assignment2.utilities.objectassist;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.ObjectStats;
import assignment2.utilities.Vector2D;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by el16035 on 18/03/2018.
 */
//Class which displays the health remaining of a GameObject
public class HealthBar {
    private static final int BAR_HEIGHT = 5;
    private static final double WIDTH_PROP_TO_OBJ = 1.2;
    private static final Color BORDER_COLOR = Color.BLACK;


    private static final Map<Double, Color> HEALTH_PERCENT_COLOR = new LinkedHashMap<>();


    static {
        HEALTH_PERCENT_COLOR.put(0.26, Color.RED);
        HEALTH_PERCENT_COLOR.put(0.51, Color.YELLOW);
        HEALTH_PERCENT_COLOR.put(1.01, Color.GREEN);

    }

    private Color currentColor = Color.BLACK;

    private GameObject object;
    private ObjectStats stats;
    private int barWidth;
    private Vector2D barPos;

    public HealthBar(GameObject object){
        this.object = object;
        stats = object.getStats();

        barWidth = (int) (object.width * WIDTH_PROP_TO_OBJ);
    }


    private void setPos(){
        barPos = new Vector2D(object.position).subtract(barWidth/2, (object.height/2) +(BAR_HEIGHT *2));
    }
    public void draw(Graphics2D g) {
        setPos();

        g.setColor(currentColor);
        g.fillRect((int) barPos.x, (int) barPos.y, (int) (barWidth * getHealthPropToMax()), BAR_HEIGHT);

        g.setColor(BORDER_COLOR);
        g.drawRect((int) barPos.x, (int) barPos.y, barWidth, BAR_HEIGHT);


    }

    //Get the proportion between the current health/armour compared to the max health
    private double getHealthPropToMax(){
        double prop = (double) stats.getArmour() / stats.getMaxArmour();
        setHealthColor(prop);
        return prop;
    }

    private void setHealthColor(double prop){
        for (Double d: HEALTH_PERCENT_COLOR.keySet()){
            if (prop <= d){
                currentColor = HEALTH_PERCENT_COLOR.get(d);
                return;
            }
        }
    }
}
