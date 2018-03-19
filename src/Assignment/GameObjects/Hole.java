package Assignment.GameObjects;

import Assignment.Other.SharedValues;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;
import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.VEC_PLACEHOLDER;
import static Assignment.Other.SharedValues.cellSize;

/**
 * Created by eriklange on 19.03.2018.
 */
//TODO: the holes'
abstract class Hole extends GameObject {
    private static final int MIN_GRAV_PULL = 55;
    private static final int MAX_GRAV_PULL = 80;
    private static final int RADIUS = cellSize;

    private static final int GRAV_DIFF = MAX_GRAV_PULL - MIN_GRAV_PULL;

    Hole(Vector2D position, Image image) {
        super(position, VEC_PLACEHOLDER, new Vector2D(0, 1), RADIUS, RADIUS, null, image);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                direction.rotate(0.03);
            }
        }, 0, DELAY);
    }


    class GravitationalPull extends TimerTask {
        private PlayerShip ship;
        private Vector2D vecToShip;
        private double factor;
        private String name;

        //
        GravitationalPull(PlayerShip ship, double factor, String name) {
            this.ship = ship;
            this.factor = factor;
            this.name = name;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (ship != null) {
                    vecToShip = new Vector2D(position).subtract(ship.position).normalise().mult(factor);
                    ship.gravitationalPull = new Vector2D(vecToShip.mult(calcGravPull()));

                   /* System.out.println("VELO="+action.velocity.toString());
                    System.out.println("DIR="+action.direction.toString());*/
                }
            }
        }

        //Calculates the strength of the pull. The closer the ship is, the stronger the holes will be.
        private double calcGravPull() {
            double dist = position.dist(ship.position);
            double l = MIN_GRAV_PULL + (GRAV_DIFF * ( 1 - (dist / FRAME_HEIGHT)));
            System.out.println(name +" dist=" +dist);
            System.out.println(name +" pull=" +l);

            return l;
        }
    }
}
