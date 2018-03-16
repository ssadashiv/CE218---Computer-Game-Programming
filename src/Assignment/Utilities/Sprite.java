package Assignment.Utilities;

import java.awt.*;
import java.io.IOException;

/**
 * Created by el16035 on 13/02/2018.
 */
public class Sprite {
    public static Image ASTEROID1, MILKYWAY1, PLAYER_SHIP, MEDIUM_SAUCER, LARGE_SAUCER, BULLET, BASIC_BULLET, DOOR_BUTTON_NOT_PRESSED, DOOR_BUTTON_PRESSED;

    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            PLAYER_SHIP = ImageManager.loadImage("playerShip");
            MEDIUM_SAUCER = ImageManager.loadImage("smallSaucer");
            LARGE_SAUCER = ImageManager.loadImage("mediumSaucer");
            BULLET = ImageManager.loadImage("bullet");
            BASIC_BULLET = ImageManager.loadImage("basicBullet");
            DOOR_BUTTON_NOT_PRESSED = ImageManager.loadImage("doorbutton_not_pressed");
            DOOR_BUTTON_PRESSED = ImageManager.loadImage("doorbutton_pressed");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
