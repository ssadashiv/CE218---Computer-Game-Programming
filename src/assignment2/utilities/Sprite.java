package assignment2.utilities;

import java.awt.*;
import java.io.IOException;

/**
 * Created by el16035 on 13/02/2018.
 */

//Class for storing the images of the gameObjects. ig to this from the lab sessions
public class Sprite {
    public static Image
            MAIN_MENU_BG,
            BACKGROUND_STARS,
            BACKGROUND_SPACESTATION,
            WALL,
            DOOR,
            PLAYER_SHIP,
            PLAYER_BOOSTER,
            BASIC_BULLET,
            DOOR_BUTTON_NOT_PRESSED,
            DOOR_BUTTON_PRESSED,
            CHARGE_BOT,
            CHARGE_STATION,
            ZAPPER,
            SAUCER,
            BASIC_ROCKET,
            BLACK_HOLE,
            WHITE_HOLE,
            LVL_HOLE,
            COIN_MONEY_MAN,
            COIN_BULLET,
            SCRAP_METAL,
            SHOP_MAN;

    static {
        try {
            MAIN_MENU_BG = ImageManager.loadImage("mainmenu_bg");
            BACKGROUND_STARS = ImageManager.loadImage("background_stars");
            BACKGROUND_SPACESTATION = ImageManager.loadImage("background_spacestation");
            WALL = ImageManager.loadImage("wall");
            DOOR = ImageManager.loadImage("door");
            PLAYER_SHIP = ImageManager.loadImage("player_spaceship");
            PLAYER_BOOSTER = ImageManager.loadImage("player_boost1");
            BASIC_BULLET = ImageManager.loadImage("basic_bullet");
            DOOR_BUTTON_NOT_PRESSED = ImageManager.loadImage("doorbutton_not_pressed");
            DOOR_BUTTON_PRESSED = ImageManager.loadImage("doorbutton_pressed");
            CHARGE_BOT = ImageManager.loadImage("charge_bot");
            CHARGE_STATION = ImageManager.loadImage("charge_station");
            ZAPPER = ImageManager.loadImage("zapper");
            SAUCER = ImageManager.loadImage("saucer");
            BASIC_ROCKET = ImageManager.loadImage("basic_rocket");
            BLACK_HOLE = ImageManager.loadImage("black_hole");
            WHITE_HOLE = ImageManager.loadImage("white_hole");
            LVL_HOLE = ImageManager.loadImage("lvl_hole");
            COIN_MONEY_MAN = ImageManager.loadImage("coin_money_man");
            COIN_BULLET = ImageManager.loadImage("coin_bullet");
            SHOP_MAN = ImageManager.loadImage("shop_man");

            SCRAP_METAL = ImageManager.loadImage("scrap_metal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
