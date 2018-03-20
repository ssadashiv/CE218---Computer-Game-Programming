package Assignment.GameObjects;

import Assignment.Other.SharedValues;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;
import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.VEC_PLACEHOLDER;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by eriklange on 19.03.2018.
 */
//TODO: the holes'
public abstract class Hole extends GameObject {
    private static final int RADIUS = cellSize;


    Hole(Vector2D position, Image image) {
        super(position, VEC_PLACEHOLDER, new Vector2D(0, 1), RADIUS, RADIUS, null, image);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                direction.rotate(0.03);
            }
        }, 0, DELAY);
    }

    public abstract double getFactor();


}
