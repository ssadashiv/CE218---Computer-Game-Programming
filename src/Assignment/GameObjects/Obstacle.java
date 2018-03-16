package Assignment.GameObjects;

import Assignment.Utilities.HitDetection;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Created by el16035 on 14/03/2018.
 */
public class Obstacle extends GameObject {
    private Color color = Color.BLACK;
    int width;
    int height;

    public Obstacle(Vector2D pos, Color color, int width, int height){
        super(pos, new Vector2D(0,0), new Vector2D(0,0), width, null, null);
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int) position.x, (int) position.y, width, height);

        super.drawHitBox(g);
    }
    public Rectangle getBounds() {
        return new Rectangle((int) position.x, (int) position.y, width, height);
    }

    @Override
    public boolean canHit(GameObject other) {
        return true;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)){
            if (overlap(other)) HitDetection.HitObstacle(this, other);
        }
    }
}
