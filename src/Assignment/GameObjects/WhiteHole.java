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

    public WhiteHole(PlayerShip ship, Vector2D position) {
        super(position, IMAGE);
        new Timer().schedule(new GravitationalPull(ship, -1.0, "White hole"), 0, DELAY);
    }

    @Override
    public void hitDetected(GameObject other) {
    }

    @Override
    public boolean canHit(GameObject other) {
        return false;
    }
}
