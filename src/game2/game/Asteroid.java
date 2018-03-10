package game2.game;

import game2.utilities.SoundManager;
import game2.utilities.Sprite;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by el16035 on 16/01/2018.
 */

class Asteroid extends GameObject {
    private static final Clip[] DEATH_SOUND = new Clip[]{
            SoundManager.bangSmall,
            SoundManager.bangMedium,
            SoundManager.bangLarge
    };
    private static final int[] RADIUS_ARR = new int[]{5, 10, 30};
    private static final double MAX_SPEED = 100;
    private static final double MIN_SPEED = 10;
    private int size;

    List<Asteroid> spawnedAsteroids;

    private Asteroid(Vector2D position, Vector2D velocity, int size) {
        super(position, velocity,  new Vector2D(0, 0), RADIUS_ARR[size], DEATH_SOUND[size], Sprite.ASTEROID1);
        this.size = size;

        Random r = new Random();
        direction = new Vector2D(r.nextDouble(), r.nextDouble());

    }


    static Asteroid makeRandomAsteroid(int size) {
        int rad = RADIUS_ARR[size];
        Random r = new Random();
        int i;

        //Minus RADIUS to make sure ranX is not off screen

        double ranX = (FRAME_WIDTH - (rad * 2)) * r.nextDouble();
        double ranY = (FRAME_HEIGHT - (rad * 2)) * r.nextDouble();

        double ranVelX = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();
        double ranVelY = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();

        i = 1 + r.nextInt();

        if (i == 1) {
            ranVelX *= -1;
        }

        i = 1 + r.nextInt();

        if (i == 1) {
            ranVelY *= -1;
        }

        return new Asteroid(new Vector2D(ranX, ranY), new Vector2D(ranVelX, ranVelY), size);
    }

    static Asteroid makeRandomAsteroid(int size, Vector2D parentPos) {
        Random r = new Random();
        int i;

        Vector2D newPos = new Vector2D(parentPos);

        double ranVelX = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();
        double ranVelY = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();

        i = 1 + r.nextInt();

        if (i == 1) {
            ranVelX *= -1;
        }

        i = 1 + r.nextInt();

        if (i == 1) {
            ranVelY *= -1;
        }

        return new Asteroid(newPos, new Vector2D(ranVelX, ranVelY), size);
    }

    public void hit() {
        if (size != 0) {
            splitAsteroid();
        }

        super.hit();
    }

    private void splitAsteroid() {
        if (size == 0) {
            SoundManager.smallAsteroids();
            return;
        } else if (size == 1) {
            SoundManager.mediumAsteroids();
        } else {
            SoundManager.largeAsteroids();
        }

        spawnedAsteroids = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            spawnedAsteroids.add(makeRandomAsteroid(size - 1, position));
        }
    }

    /*public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(radius * 2 / imW, radius * 2 / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
    }*/


    public boolean canHit(GameObject other) {
        return (other instanceof PlayerShip && !((PlayerShip) other).invincible) || other instanceof Bullet;

    }

    @Override
    public String toString() {
        return "Pos: " + position.toString() + ", Vel: " + velocity.toString();
    }


    //TODO: Add code to handle collisions with alien Saucer
    public int collisionHandling(GameObject other) {
        if (canHit(other)) {
            if (super.collisionHandling(other) != 0) {
                return 10;
            }
        }
        return 0;
    }

}
