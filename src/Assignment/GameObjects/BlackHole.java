package Assignment.GameObjects;

import Assignment.Other.SharedValues;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;
import static Assignment.Other.Constants.VEC_PLACEHOLDER;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by eriklange on 18.03.2018.
 */

//Class for black holes. The black holes' gravitational pull are never strong enough to not let a player move
    //TODO: Make it so that the player can always escape from the black hole
    //TODO: Make it stronger for every level
public class BlackHole extends GameObject {
    private static final Image NOT_PRESSED = Sprite.BLACK_HOLE;
    private static final Clip SOUND = SoundManager.beat2;
    private static final int MAX_GRAV_PULL = 4;
    private static final int MIN_GRAV_PULL = 2;

    private static final int RADIUS = cellSize / 4;

    private int gravPullIntensity;

    public BlackHole(PlayerShip ship, Vector2D position){
        super(position, VEC_PLACEHOLDER, VEC_PLACEHOLDER, RADIUS, SOUND, NOT_PRESSED);
        gravPullIntensity = new Random().nextInt(MAX_GRAV_PULL + 1 - MIN_GRAV_PULL) + MIN_GRAV_PULL;

        new Timer().schedule(new GravitationalPull(ship), 0, DELAY);

    }


    @Override
    public boolean canHit(GameObject other) {
        return false;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            super.collisionHandling(other);
        }
    }


    private class GravitationalPull extends TimerTask {
        private PlayerShip ship;
        private Vector2D vecToShip;

        private GravitationalPull(PlayerShip ship) {
            this.ship = ship;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (ship != null) {
                    vecToShip = new Vector2D(ship.position).subtract(position).normalise();
                    ship.gravitationalPull = new Vector2D(vecToShip.mult(gravPullIntensity));

                   /* System.out.println("VELO="+action.velocity.toString());
                    System.out.println("DIR="+action.direction.toString());*/
                }
            }
        }
    }
}
