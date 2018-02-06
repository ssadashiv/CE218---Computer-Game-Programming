package game2.game;

import game2.utilities.Vector2D;

import static game2.game.Constants.DT;
import static game2.game.Constants.FRAME_HEIGHT;
import static game2.game.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by el16035 on 16/01/2018.
 */

class Asteroid extends GameObject{
    private static final int[] RADIUS_ARR = new int[]{5,10,30};
    private static final double MAX_SPEED = 100;
    private static final double MIN_SPEED = 10;

    private int radius;
    private int size;

    List<Asteroid> spawnedAsteroids;

    private Asteroid(Vector2D position, Vector2D velocity, int size){
        super(position, velocity, RADIUS_ARR[size]);
        radius = RADIUS_ARR[size];
        this.size = size;
    }


    static Asteroid makeRandomAsteroid(int size) {
        int rad = RADIUS_ARR[size];
        Random r = new Random();
        int i;

        //Minus RADIUS to make sure ranX is not off screen
        double ranX = (FRAME_WIDTH - (rad*2)) * r.nextDouble();
        double ranY = (FRAME_HEIGHT - (rad*2)) * r.nextDouble();

        double ranVelX = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();
        double ranVelY = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();

        i = 1 + r.nextInt();

        if (i == 1){
            ranVelX *= -1;
        }

        i = 1 + r.nextInt();

        if (i == 1){
            ranVelY *= -1;
        }

        return new Asteroid(new Vector2D(ranX, ranY), new Vector2D(ranVelX, ranVelY), size);
    }

    public void hit(){
        if (size != 0){
            splitAsteroid();
        }

        super.hit();
    }

    private void splitAsteroid(){
        if (size == 0) return;

        spawnedAsteroids = new ArrayList<>();
        for (int i = 0; i< 3; i++)  {
            spawnedAsteroids.add(makeRandomAsteroid(size - 1));
        }
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String toString(){
        return "Pos: " + position.toString() + ", Vel: " + velocity.toString();
    }

    public void collisionHandling(GameObject other) {
        if (other instanceof Ship) ((Ship) other).game.incScore();

        super.collisionHandling(other);
    }

}
