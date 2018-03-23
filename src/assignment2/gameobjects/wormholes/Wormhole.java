package assignment2.gameobjects.wormholes;

import assignment2.gameobjects.GameObject;
import assignment2.utilities.Vector2D;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static assignment2.other.Constants.DELAY;
import static assignment2.other.Constants.VEC_PLACEHOLDER;
import static assignment2.other.SharedValues.cellSize;

/**
 * Created by eriklange on 19.03.2018.
 */
//Superclass for all wormholes.
public abstract class Wormhole extends GameObject {
    private static final int RADIUS = cellSize;


    Wormhole(Vector2D position, Image image) {
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
