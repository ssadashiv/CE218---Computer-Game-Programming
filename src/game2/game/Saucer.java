package game2.game;

import game2.utilities.controllers.Controller;
import game2.utilities.SoundManager;
import game2.utilities.Sprite;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by el16035 on 27/02/2018.
 */
public class Saucer extends Ship {
    private static final int RADIUS = 15;
    private static final Clip DEATH_SOUND = SoundManager.saucerSmall;
    private static final Image IMAGE = Sprite.MEDIUM_SAUCER;

    Saucer(Controller ctrl){
        super(ctrl, Color.red, new Vector2D(0,0), new Vector2D(0,0), new Vector2D(1,0), RADIUS, DEATH_SOUND, IMAGE);
    }

    public boolean canHit(GameObject other) {
        return false;
    }
}
