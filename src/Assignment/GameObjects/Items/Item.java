package Assignment.GameObjects.Items;

import Assignment.GameObjects.GameObject;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by el16035 on 15/03/2018.
 */
public abstract class Item extends GameObject{
    public Item(Vector2D position, Vector2D velocity, Vector2D direction, int width, int height, Clip deathSound, Image image) {
        super(position, velocity, direction, width, height, deathSound, image);
    }
}
