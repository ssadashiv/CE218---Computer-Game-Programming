package game2.game;

import game2.utilities.Vector2D;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 05/02/2018.
 */
class Bullet extends GameObject{
    private static final Color COLOR = Color.yellow;
    static final int RADIUS = 10;

    private int lifeTime = 2;
    static final double MUZZLE_VEL = 300;

    private Vector2D direction;

    private double mag;
    private double newMagFac;

    Bullet(Vector2D position, Vector2D velocity, Vector2D direction){
        super(position, velocity, RADIUS);
        this.direction = direction;
        mag = position.mag();
        newMagFac = (mag + (RADIUS / 2)) / mag;

        startTimer();
    }

    public void draw(Graphics2D g) {
        Vector2D endPos = new Vector2D();
        endPos.addScaled(direction, newMagFac);

        //System.out.println("s X:" + startPos.x + ", s Y:" + startPos.y+ ", p X:" + position.x + ", p Y:" + position.y);
        g.setColor(COLOR);
        g.drawLine((int) position.x, (int) position.y, (int) endPos.x, (int) endPos.y);
    }

    private void startTimer(){

        int delay = 1000;
        int period = 1000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                lifeTime--;
                if (lifeTime == 0) dead = true;
            }
        }, delay, period);


    }

    public void collisionHandling(GameObject other) {
        if (other instanceof Asteroid){
            //TODO: Add score
        }
        if (!(other instanceof Ship)){
            super.collisionHandling(other);
        }

    }
}
