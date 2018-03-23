package assignment2.utilities.controllers.enemycontrollers;

import assignment2.gameobjects.enemies.Zapper;
import assignment2.gameobjects.PlayerShip;
import assignment2.other.SharedValues;
import assignment2.utilities.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

import static assignment2.other.Constants.DELAY;

/**
 * Created by el16035 on 19/03/2018.
 */
//The controller for the Zapper
public class ZapperCtrl implements AIController {
    private static final int ATTACH_SECTOR = 30;
    private static final long STUN_DURATION = 2000;

    private AIAction action = new AIAction();
    private PlayerShip ship;
    private Zapper zapper;


    public ZapperCtrl(Zapper zapper, PlayerShip ship) {
        this.zapper = zapper;
        this.ship = ship;
    }


    @Override
    public AIAction aiAction() {
        new Timer().schedule(new ZapperControls(zapper, ship), 0, DELAY);
        return action;
    }


    private class ZapperControls extends TimerTask {
        Zapper zapper;
        PlayerShip ship;

        private ZapperControls(Zapper zapper, PlayerShip ship) {
            this.zapper = zapper;
            this.ship = ship;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (zapper.dead) cancel();

                if (!zapper.detaching){
                    if (isInAttachSector()) {
                        attachToShip();
                    }
                    action.direction.set(new Vector2D(ship.position).subtract(zapper.position).normalise());
                    action.velocity = new Vector2D(action.direction).mult(zapper.speed / action.direction.mag());
                }
            }
        }

        private boolean isInAttachSector() {
            return zapper.position.dist(ship.position) <= ATTACH_SECTOR;
        }

        private void attachToShip() {
            action.velocity = new Vector2D(0, 0);
            zapper.position = new Vector2D(ship.position.x - ship.width / 2 * Math.cos(ship.direction.angle()), ship.position.y - Math.sin(ship.direction.angle()));

            if (!zapper.attached) {
                ship.addZapper(zapper);
                zapper.attached = true;
                zapper.isInvincible = true;
            }
        }
    }
}
