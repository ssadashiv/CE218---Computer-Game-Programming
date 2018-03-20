package Assignment.Utilities.Controllers.AIControllers;

import Assignment.GameObjects.Enemies.Saucer;
import Assignment.GameObjects.PlayerShip;
import Assignment.Other.SharedValues;
import Assignment.Utilities.Vector2D;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;

/**
 * Created by el16035 on 20/03/2018.
 */
public class SaucerCtrl implements AIController {
    private AIAction action = new AIAction();
    private Saucer saucer;
    private PlayerShip ship;

    public SaucerCtrl(Saucer saucer, PlayerShip ship) {
        this.saucer = saucer;
        this.ship = ship;
    }


    @Override
    public AIAction aiAction() {
        new Timer().schedule(new SaucerMovement(saucer, ship), 0, DELAY);
        new Timer().schedule(new SaucerFireRocket(saucer), 0, saucer.getStats().getFireRate());
        return action;
    }


    private class SaucerMovement extends TimerTask {
        private Saucer saucer;
        private PlayerShip ship;

        private SaucerMovement(Saucer saucer, PlayerShip ship) {
            this.saucer = saucer;
            this.ship = ship;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (saucer.dead) cancel();
                //The saucer always has the opposite velocity as the ship.
                action.velocity = new Vector2D(ship.velocity).mult(-1);
                action.direction.set(new Vector2D(ship.position).subtract(saucer.position).normalise());

            }
        }
    }

    //Controls for firing the rockets of the ship
    private class SaucerFireRocket extends TimerTask {
        private Saucer saucer;
        private Random r = new Random();
        private SaucerFireRocket(Saucer saucer) {
            this.saucer = saucer;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (saucer.dead) cancel();
                if (r.nextBoolean()){
                    saucer.makeBullet(0);
                }
            }
        }
    }



}
