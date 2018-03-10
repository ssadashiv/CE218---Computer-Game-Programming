package game2.utilities.controllers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 27/02/2018.
 */
public class RandomAction implements Controller {
    private static final long INTERVAL = 2000;

    private Action action = new Action();
    private Random r = new Random();

    @Override
    public Action action() {
        Timer t = new Timer();
        t.schedule(new randomActions(), 0, INTERVAL);

        return action;
    }


    private class randomActions extends TimerTask {
        @Override
        public void run() {
            //Turn
            action.turn = r.nextInt(3) - 1;

            //Thrust
            action.thrust = r.nextInt(2) - 1;

            //Shoot
            action.shoot = r.nextBoolean();
        }
    }
}
