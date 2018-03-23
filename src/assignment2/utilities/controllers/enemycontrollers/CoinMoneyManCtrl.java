package assignment2.utilities.controllers.enemycontrollers;

import assignment2.gameobjects.enemies.CoinMoneyMan;
import assignment2.other.SharedValues;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 20/03/2018.
 */

//The controller for the CoinMoneMan
public class CoinMoneyManCtrl implements AIController {
    private static long fireDuration;
    private static final int BULLETS_FIRED_IN_DURATION = 10;
    private static final int DIRECTIONS = 4;
    private static final double ROTATION_ANGLE = 180;

    private AIAction action = new AIAction();

    private CoinMoneyMan CMMan;
    private Random r = new Random();
    private boolean rotating = false;

    public CoinMoneyManCtrl(CoinMoneyMan CMMan) {
        this.CMMan = CMMan;
        fireDuration = CMMan.getStats().getFireRate() / 2;
    }

    @Override
    public AIAction aiAction() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!SharedValues.gamePaused) {
                    if (CMMan.dead) {
                        cancel();
                    } else {
                        rotating = r.nextBoolean();
                        shootInACross();
                    }
                }
            }
        }, 0, CMMan.stats.getFireRate());

        return action;
    }


    private void shootInACross() {
        long interval = fireDuration / BULLETS_FIRED_IN_DURATION;
        final int totalBullets = BULLETS_FIRED_IN_DURATION * DIRECTIONS;
        final double angleDif = 360 / DIRECTIONS;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            int fireCount = 0;
            int iterations = 0;
            double currentRotationAngle = 0;

            @Override
            public void run() {
                if (!SharedValues.gamePaused) {
                    if (rotating) {
                        currentRotationAngle = ROTATION_ANGLE / BULLETS_FIRED_IN_DURATION * iterations;
                    }
                    for (int i = 0; i < DIRECTIONS; i++) {
                        CMMan.makeBullet(Math.toRadians(angleDif * i + currentRotationAngle));
                        fireCount++;
                    }
                    iterations++;

                    if (fireCount >= totalBullets) cancel();
                }
            }
        }, 0, interval);

    }
}
