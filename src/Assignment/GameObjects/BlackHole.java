package Assignment.GameObjects;

import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;

import static Assignment.Other.Constants.DELAY;
import static Assignment.Other.Constants.VEC_PLACEHOLDER;

/**
 * Created by eriklange on 18.03.2018.
 */

//Class for black holes. The black holes' gravitational pull are never strong enough to not let a player move

public class BlackHole extends Hole {
    private static final Image IMAGE = Sprite.BLACK_HOLE;

    private WhiteHole whiteHole;
    public BlackHole(PlayerShip ship, Vector2D position){
        super(position, IMAGE);
        new Timer().schedule(new GravitationalPull(ship, 1.0, "Black Hole"), 0, DELAY);

    }

    public void setWhiteHole(WhiteHole whiteHole) {
        this.whiteHole = whiteHole;
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }

    @Override
    public void hitDetected(GameObject other) {
        //TODO: Teleport to the corresponding white hole.
        other.position = new Vector2D(whiteHole.position);
    }
}
