package assignment2.gameobjects.wormholes;

import assignment2.gameobjects.GameObject;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by eriklange on 19.03.2018.
 */
//Opposite of the Blackhole class. pushes the player away from the hole.
public class WhiteHole extends Wormhole {
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
