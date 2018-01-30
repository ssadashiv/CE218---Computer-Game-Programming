package game2.game;

import game2.utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game1.game.Constants.*;

/**
 * Created by el16035 on 16/01/2018.
 */

public class BasicAsteroid {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;
    public static final double MIN_SPEED = 10;

    private Vector2D position;
    private Vector2D velocity;

    public BasicAsteroid(double x, double y, double vx, double vy){
        position = new Vector2D(x, y);
        velocity = new Vector2D(vx, vy);
    }


	public static BasicAsteroid makeRandomAsteroid() {
	    Random r = new Random();
        int i;

        //Minus RADIUS to make sure ranX is not off screen
        double ranX = (FRAME_WIDTH - (RADIUS*2)) * r.nextDouble();
        double ranY = (FRAME_HEIGHT - (RADIUS*2)) * r.nextDouble();

        double ranVelX = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();
        double ranVelY = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();

        i = 1 + (2-1) *r.nextInt();

        if (i == 1){
            ranVelX *= -1;
        }

        i = 1 + (2-1) *r.nextInt();

        if (i == 1){
            ranVelY *= -1;
        }

        return new BasicAsteroid(ranX, ranY, ranVelX, ranVelY);
    }
    public void update(){
        position.x += velocity.x * DT;
        position.y += velocity.y * DT;
        position.x = (position.x + FRAME_WIDTH) % FRAME_WIDTH;
        position.y = (position.y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    @Override
    public String toString(){
        return "X: " + position.x+", Y: "+ position.y+", velX: " + velocity.x +", velY: "+ velocity.y;
    }

}
