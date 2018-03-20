package Assignment.GameObjects;

import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.util.Timer;

import static Assignment.Other.Constants.DELAY;

/**
 * Created by eriklange on 19.03.2018.
 */
public class WhiteHole extends Hole {
    private static final Image IMAGE = Sprite.WHITE_HOLE;
    private static final double FACTOR = -1.0;

    public WhiteHole(Vector2D position) {
        super(position, IMAGE);
    }

    @Override
    public void hitDetected(GameObject other) {
    }

    @Override
    public boolean canHit(GameObject other) {
        return false;
    }

    @Override
    public double getFactor() {
        return FACTOR;
    }
}
