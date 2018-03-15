package Assignment.GameObjects;

import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Created by el16035 on 14/03/2018.
 */
public class Obstacle extends GameObject {
    private static final Color COLOR = Color.BLACK;

    public Obstacle(Vector2D pos, double radius){
        super(pos, new Vector2D(0,0), new Vector2D(0,0), (int) radius, null, null);
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(COLOR);
        g.fillRect((int) position.x, (int) position.y, radius, radius);

        Rectangle r = getBounds();
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }


}
