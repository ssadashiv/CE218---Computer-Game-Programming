package Assignment.GameObjects;

import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.Constants.VEC_PLACEHOLDER;

/**
 * Created by el16035 on 20/03/2018.
 */
public class ScrapMetal extends GameObject {
    private static final Image IMAGE = Sprite.SCRAP_METAL;
    private static final Clip DEATH_SOUND = null;
    private static final int HEIGHT = 35;
    private static final int WIDTH = 50;
    private int amount;

    public ScrapMetal(Vector2D position, int amount) {
        super(new Vector2D(position), VEC_PLACEHOLDER, VEC_PLACEHOLDER, WIDTH,HEIGHT, DEATH_SOUND, IMAGE);
        this.amount = amount;
        System.out.println("scrap metal dropped. amount="+amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void hitDetected(GameObject other) {
        if (other instanceof PlayerShip){
            ((PlayerShip) other).pickUpScrapMetal(amount);
            amount = 0;
            dead = true;
        }
    }
    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }
}
