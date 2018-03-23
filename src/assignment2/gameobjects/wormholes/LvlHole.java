package assignment2.gameobjects.wormholes;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.PlayerShip;
import assignment2.maingame.Game;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 21/03/2018.
 */

//Wormhole which appears when the player defeats the current lvl's boss. Teleports the player to the next level/
public class LvlHole extends Wormhole {
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
