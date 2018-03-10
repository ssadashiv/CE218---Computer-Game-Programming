package game2.utilities.controllers;

import game2.game.Game;
import game2.game.GameObject;
import game2.utilities.Vector2D;

import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 10/03/2018.
 */
public class AimNShoot implements Controller {
    private static final long INTERVAL = 1000 / 60;
    private static final int SHOOT_INTERVAL = 1500;

    private Game game;
    private Action action = new Action();

    private GameObject target = null;
    private GameObject ctrlUser;

    private boolean canFire = false;

    public AimNShoot(Game game) {
        this.game = game;
    }

    public void setParent(GameObject parent) {
        ctrlUser = parent;
    }


    @Override
    public Action action() {
        Timer t = new Timer();
        t.schedule(new Target(), 0, INTERVAL);

        Timer bulletT = new Timer();
        bulletT.schedule(new TimerTask() {
            @Override
            public void run() {
                action.shoot = true;
            }
        }, 0, SHOOT_INTERVAL);


        return action;
    }

    private class Target extends TimerTask {



        @Override
        public void run() {
            if (target == null) {
                findTarget();
            }

            processTarget();
        }

        private void processTarget() {
            if (!hasAimed()){
                target.isTarget = true;
                System.out.println("angle " + getAngle());
                aim();
            }
            canFire = hasAimed();
        }

        private void aim() {
            action.turn = getAngle() > 180 ? -1 : 1;
        }

        private double getAngle() {

            if (target != null) {
                /*Vector2D thisDir = new Vector2D(ctrlUser.direction);
                Vector2D otherDir = new Vector2D(target.position);
                otherDir.subtract(ctrlUser.position).normalise();*/
                Vector2D dist = new Vector2D(target.position);
                dist.subtract(ctrlUser.position);
                return Math.toDegrees(dist.angle());
            }

            return 0;

        }

        private void findTarget() {
            GameObject temp = null;
            double minLength = Double.MAX_VALUE;
            for (GameObject o : game.objects) {
                if (ctrlUser.canHit(o)) {
                    if (ctrlUser.position.dist(o.position) < minLength) temp = o;
                }

            }
            /*while (it.hasNext()){
                GameObject o = it.next();
                if (ctrlUser.canHit(o)) {
                    if (ctrlUser.position.dist(o.position) < minLength) temp = o;
                }

            }
            */
            target = temp;
            if (target != null) System.out.println("target: " + target.position);
        }

        private boolean hasAimed() {
            return getAngle() > -10 && getAngle() < 10;
        }
    }
}
