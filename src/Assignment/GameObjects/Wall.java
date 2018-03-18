package Assignment.GameObjects;

import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 18/03/2018.
 */
public class Wall extends Obstacle {
    private static final Image IMAGE = Sprite.WALL;
    public Wall(Vector2D pos, int width, int height) {
        super(pos, IMAGE, width, height);
    }
}
