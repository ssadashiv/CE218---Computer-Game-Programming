/*
package game1.game;

import game1.utilities.Vector2D;

import static game1.game.Constants.DT;
import static game1.game.Constants.FRAME_HEIGHT;
import static game1.game.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

*/
/**
 * Created by el16035 on 16/01/2018.
 *//*


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


	public static game1.game.Asteroid makeRandomAsteroid() {
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

        return new game1.game.Asteroid(ranX, ranY, ranVelX, ranVelY);
    }
    public void update(){
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
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
*/
