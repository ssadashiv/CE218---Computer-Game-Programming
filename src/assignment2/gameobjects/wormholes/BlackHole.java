package assignment2.gameobjects.wormholes;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by eriklange on 18.03.2018.
 */

//Class for black wormholes. If an object steps into a black hole, they get teleported to a white hole in the same room.
public class BlackHole extends Wormhole {
    private static final Image IMAGE = Sprite.BLACK_HOLE;
    private static final double FACTOR = 1.0;
    private WhiteHole whiteHole;

    public BlackHole(Vector2D position){
        super(position, IMAGE);
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

    //
    @Override
    public double getFactor() {
        return FACTOR;
    }
}
