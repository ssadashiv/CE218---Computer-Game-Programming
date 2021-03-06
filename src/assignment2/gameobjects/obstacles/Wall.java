package assignment2.gameobjects.obstacles;

import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 18/03/2018.
 */

//Class for the walls that surrounds the rooms in the game.
public class Wall extends Obstacle {
    private static final Image IMAGE = Sprite.WALL;
    public Wall(Vector2D pos, int width, int height) {
        super(pos, IMAGE, width, height);
    }
}
