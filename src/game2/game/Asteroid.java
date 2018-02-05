package game2.game;

import game2.utilities.Vector2D;

import static game2.game.Constants.DT;
import static game2.game.Constants.FRAME_HEIGHT;
import static game2.game.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * Created by el16035 on 16/01/2018.
 */

class Asteroid extends GameObject{
    private static final int RADIUS = 10;
    private static final double MAX_SPEED = 100;
    private static final double MIN_SPEED = 10;

    private Asteroid(Vector2D position, Vector2D velocity, double radius){
        super(position, velocity, radius);

    }


    static Asteroid makeRandomAsteroid() {
        Random r = new Random();
        int i;

        //Minus RADIUS to make sure ranX is not off screen
        double ranX = (FRAME_WIDTH - (RADIUS*2)) * r.nextDouble();
        double ranY = (FRAME_HEIGHT - (RADIUS*2)) * r.nextDouble();

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

        return new Asteroid(new Vector2D(ranX, ranY), new Vector2D(ranVelX, ranVelY), RADIUS);
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    @Override
    public String toString(){
        return "Pos: " + position.toString() + ", Vel: " + velocity.toString();
    }

}
