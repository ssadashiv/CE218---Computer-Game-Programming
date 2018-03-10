package game2.utilities;

import java.awt.*;
import java.io.IOException;

/**
 * Created by el16035 on 13/02/2018.
 */
public class Sprite {
    public static Image ASTEROID1, MILKYWAY1, PLAYER_SHIP, MEDIUM_SAUCER, LARGE_SAUCER, BULLET;

    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            PLAYER_SHIP = ImageManager.loadImage("playerShip");
            MEDIUM_SAUCER = ImageManager.loadImage("smallSaucer");
            LARGE_SAUCER = ImageManager.loadImage("mediumSaucer");
            BULLET = ImageManager.loadImage("bullet");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
