package assignment2.gameobjects;

import assignment2.maingame.ShopFrame;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

import static assignment2.other.Constants.*;
import static assignment2.other.SharedValues.cellSize;

/**
 * Created by el16035 on 21/03/2018.
 */

//Class for the NPC which is in the Shoprooms. opens a shop menu when the player collides with the ShopMan.
public class ShopMan extends GameObject {
    private static final Image IMAGE = Sprite.SHOP_MAN;
    private static final int HEIGHT = 200;
    private static final int WIDTH = (int) (HEIGHT * 1.5);

    private PlayerShip playerShip;

    public ShopMan(PlayerShip playerShip, Vector2D position) {
        super(position, VEC_PLACEHOLDER, VEC_PLACEHOLDER, WIDTH, HEIGHT, null, IMAGE);
        this.playerShip = playerShip;
    }

    public void setPlayerShip(PlayerShip ship) {
        this.playerShip = ship;
    }

    @Override
    public void hitDetected(GameObject other) {
        openShop();
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }

    private void openShop() {
        new ShopFrame(playerShip);
        playerShip.canMove = false;
        playerShip.position.set(cellSize*2, FRAME_HEIGHT/2);
        playerShip.velocity.set(0,0);

    }
}
