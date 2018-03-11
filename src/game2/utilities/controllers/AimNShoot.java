package game2.utilities.controllers;

import game2.game.Game;
import game2.game.GameObject;
import game2.utilities.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 10/03/2018.
 */
public class AimNShoot implements Controller {
    private static final long INTERVAL = 1000;
    private static final int SHOOT_INTERVAL = 1500;

    private Game game;
    private Action action = new Action();

    private GameObject target = null;
    private GameObject parent;

    private boolean targeting = false;

    public AimNShoot(Game game) {
        this.game = game;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }


    @Override
    public Action action() {


        Timer t = new Timer();
        t.schedule(new Target(), 0, INTERVAL);

        /*
        Timer bulletT = new Timer();
        bulletT.schedule(new TimerTask() {
            @Override
            public void run() {
                if (canFire) {
                    action.turn = 0;
                    action.shoot = true;
                }
            }
        }, 0, SHOOT_INTERVAL);*/

        return action;
    }

    private class Target extends TimerTask {

        @Override
        public void run() {
            if (!targeting && parent != null) {
                targeting = true;
                if (target == null){
                    findTarget();
                }
                processTarget();
            }
        }

        private void processTarget() {
            aim();
            while (action.turn != 0 || target != null){
                if (hasAimed()) {
                    action.turn = 0;
                    action.shoot = true;
                    targeting = false;
                    break;
                }
            }
        }

        private void aim() {
            action.turn = getAngle() > 0 ? 1 : -1;
        }

        private double getAngle() {

            if (target != null) {
                Vector2D thisDis = new Vector2D(parent.direction).normalise();
                Vector2D otherDis = new Vector2D(target.position).normalise();

                return Math.toDegrees(thisDis.dot(otherDis));
            }

            action.turn = 0;
            return 0;

        }

        private void findTarget() {
            GameObject temp = null;
            double minLength = Double.MAX_VALUE;
            for (GameObject o : game.objects) {
                if (parent.canHit(o)) {
                    if (parent.position.dist(o.position) < minLength) {
                        temp = o;
                        temp.isTarget = true;
                    }
                }

            }
            target = temp;
        }

        private boolean hasAimed() {
            return getAngle() > -10 && getAngle() < 10;
        }
    }
}
