package Assignment.Utilities;

import java.awt.*;
import java.io.IOException;

/**
 * Created by el16035 on 13/02/2018.
 */
public class Sprite {
    public static Image
            BACKGROUND,
            WALL,
            DOOR,
            PLAYER_SHIP,
            BASIC_BULLET,
            DOOR_BUTTON_NOT_PRESSED,
            DOOR_BUTTON_PRESSED,
            CHARGE_BOT,
            CHARGE_STATION,
            BLACK_HOLE;

    static {
        try {
            BACKGROUND = ImageManager.loadImage("background");
            WALL = ImageManager.loadImage("wall");
            DOOR = ImageManager.loadImage("door");
            PLAYER_SHIP = ImageManager.loadImage("playerShip");
            BASIC_BULLET = ImageManager.loadImage("basicBullet");
            DOOR_BUTTON_NOT_PRESSED = ImageManager.loadImage("doorbutton_not_pressed");
            DOOR_BUTTON_PRESSED = ImageManager.loadImage("doorbutton_pressed");
            CHARGE_BOT = ImageManager.loadImage("charge_bot");
            BLACK_HOLE = ImageManager.loadImage("black_hole");
            CHARGE_STATION = ImageManager.loadImage("charge_station");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
