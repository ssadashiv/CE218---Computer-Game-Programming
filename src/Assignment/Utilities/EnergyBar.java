package Assignment.Utilities;

import Assignment.GameObjects.Enemies.ChargeBot;

import java.awt.*;

/**
 * Created by el16035 on 19/03/2018.
 */
public class EnergyBar {
    private static final Color BORDER_COLOR = Color.BLACK;
    public static final int WIDTH = 5;
    private static final Color BAR_COLOR = Color.CYAN;
    private static final double HIGHT_PROP_TO_OBJ = 1.3;
    private ChargeBot parent;
    public int height;
    public Vector2D position;

    public EnergyBar(ChargeBot parent) {
        this.parent = parent;
        height = (int) (parent.width * HIGHT_PROP_TO_OBJ);

    }


    private void setPos(){
        position = new Vector2D(parent.position).subtract(parent.width/2 + WIDTH * 2, height /2);
    }

    //Get the proportion between the current health/armour compared to the max health
    private double getEnergyPropToMax() {
        return (double) parent.getCurrentEnergy() / parent.getMaxEnergy();
    }


    public void draw(Graphics2D g) {
        setPos();

        int prop = (int)(height * getEnergyPropToMax());
        int remainder = height - prop;
        g.setColor(BAR_COLOR);
        g.fillRect((int) position.x, (int) position.y + remainder  , WIDTH,prop);

        g.setColor(BORDER_COLOR);
        g.drawRect((int) position.x, (int) position.y, WIDTH, height);
    }
}

