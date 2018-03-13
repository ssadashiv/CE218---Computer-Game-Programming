package game2.game;

import game2.utilities.SoundManager;
import game2.utilities.Sprite;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by el16035 on 05/02/2018.
 */
class Bullet extends GameObject {
    private static final Clip DEATH_SOUND = SoundManager.fire;
    private static final Image IMAGE = Sprite.BULLET;

    private static final int RADIUS = 2;
    static final double MUZZLE_VEL = 300;

    Ship parent;
    //Lifetime of every bullet in seconds
    private int lifeTime = 2;


    Bullet(Vector2D position, Vector2D velocity, Vector2D direction, Ship parent) {
        super(position, velocity, direction, RADIUS, DEATH_SOUND, IMAGE);
        this.parent = parent;
        double mag = position.mag();

        startTimer();
    }

   /* public void draw(Graphics2D g) {
        Vector2D endPos = new Vector2D(position);
        endPos.addScaled(direction, newMagFac);

        //System.out.println("s X:" + startPos.x + ", s Y:" + startPos.y+ ", p X:" + position.x + ", p Y:" + position.y);
        g.setColor(COLOR);
        g.drawLine((int) position.x, (int) position.y, (int) endPos.x, (int) endPos.y);
    }*/

    private void startTimer() {

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

    public boolean canHit(GameObject other) {
        return !(parent instanceof Saucer && other instanceof Saucer) && ((other instanceof Asteroid && parent instanceof PlayerShip) || (other instanceof PlayerShip && parent instanceof Saucer));

    }

    public int collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (super.collisionHandling(other) != 0) {
                return 10;
            }
        }


        return 0;
    }
}
