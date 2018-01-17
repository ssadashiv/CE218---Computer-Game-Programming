package lab1.game1;

import static lab1.game1.Constants.DT;
import static lab1.game1.Constants.FRAME_HEIGHT;
import static lab1.game1.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * Created by el16035 on 16/01/2018.
 */

public class BasicAstroid{
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;
    public static final double MIN_SPEED = 10;

    private double x, y;
    private double vx, vy;

    public BasicAstroid(double x, double y, double vx, double vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }


	public static BasicAstroid makeRandomAsteroid() {
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

        return new BasicAstroid(ranX, ranY, ranVelX, ranVelY);
    }
    public void update(){
        x += vx * DT;
        y += vy * DT;
        x = (x + FRAME_WIDTH) % FRAME_WIDTH;
        y = (y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    @Override
    public String toString(){
        return "X: " + x+", Y: "+y+", velX: " + vx +", velY: "+vy;
    }

}
