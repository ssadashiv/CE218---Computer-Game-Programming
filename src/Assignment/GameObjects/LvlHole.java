package Assignment.GameObjects;

import Assignment.MainGame.Game;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 21/03/2018.
 */
public class LvlHole extends Hole {
    private static final Image IMAGE = Sprite.LVL_HOLE;
    private Game game;

    public LvlHole(Vector2D position, Game game) {
        super(position, IMAGE);
        this.game = game;
    }

    @Override
    public void hitDetected(GameObject other) {
        game.nextLevelConfirmation();
    }

    @Override
    public double getFactor() {
        return 0;
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }
}
