package game2.utilities.controllers;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import game2.game.Game;
import game2.game.GameObject;
import game2.game.Ship;
import game2.utilities.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 10/03/2018.
 */

//TODO: THE ANGLE IS WRONG
    // https://stackoverflow.com/questions/23692077/rotate-object-to-face-point?rq=1

public class AimNShoot implements Controller {
    private static final long INTERVAL = 1000;

    private Game game;
    private Action action = new Action();

    private GameObject target = null;
    private Ship parent;

    private boolean targeting = false;

    public AimNShoot(Game game) {
        this.game = game;
    }

    public void setParent(Ship parent) {
        this.parent = parent;
    }


    /*
getTarget
    aim
    shoot
    repeat
 * */
    @Override
    public Action action() {


        Timer t = new Timer();
        t.schedule(new Target(), 0, INTERVAL);

        return action;
    }

    private class Target extends TimerTask {

        @Override
        public void run() {
            if (!targeting && parent != null) {
                System.out.println("finding new target");
                targeting = true;
                findTarget();

                if (target != null) aim();
            }
        }

        private void processTarget() {
        }

        private void aim() {
            if (target == null){
                action.turn = 0;
                return;
            }

            action.turn = getAngle() > 0 ? 1 : -1;
            while (targeting){
                if (hasAimed()) {
                    System.out.println("Shooting");
                    action.turn = 0;
                    action.shoot = true;
                    target.isTarget = false;
                    targeting = false;
                    break;
                }
            }

        }

        private double getAngle() {

            if (target != null) {
                Vector2D thisDir = new Vector2D(parent.direction).normalise();
                Vector2D otherDir = new Vector2D(target.position).subtract(parent.position).normalise();

                return Math.toDegrees(thisDir.angle(otherDir));
            }
            return 0;

        }

        private void findTarget() {
            GameObject temp = null;
            double minLength = Double.MAX_VALUE;
            for (GameObject o : game.objects) {
                if (parent.canShoot(o)) {
                    System.out.println("found possible target");
                    if (parent.position.dist(o.position) < minLength) {
                        temp = o;
                    }
                }

            }
            if (temp != null){
                temp.isTarget = true;
                target = temp;
            }

        }

        private boolean hasAimed() {
            return getAngle() > -7 && getAngle() < 7;
        }
    }
}
