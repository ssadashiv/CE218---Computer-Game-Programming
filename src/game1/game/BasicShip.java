/*
package game1.game;

import static game1.game.Constants.DT;
import static game1.game.Constants.FRAME_HEIGHT;
import static game1.game.Constants.FRAME_WIDTH;

import game1.utilities.Action;
import game1.utilities.BasicController;
import game1.utilities.Vector2D;

import java.awt.*;

*/
/**
 * Created by el16035 on 29/01/2018.
 *//*

public class Ship {
    public static final int RADIUS = 8;

    //Rotation velocity in radians per second
    public static final double STEER_RATE = 2*Math.PI;

    //Acceleration when thrust is applied
    public static final double MAG_ACC = 200;

    //Constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color SHIP_COLOR = Color.cyan;

    public Vector2D position; //On frame
    public Vector2D velocity; //Per second

    private Vector2D turretVec;

    */
/*
    Direction in which the nose of the ship is pointing
    This will be the direction in which thrust is applied
    It is a unit vector representing the angle by which the ship has rotated
     *//*

    public Vector2D direction;
    private Action action;

    public Ship(Controller ctrl){
        action = ctrl.action();
        position = new Vector2D(FRAME_WIDTH /2, FRAME_HEIGHT/2);
        velocity = new Vector2D(0,0);
        direction = new Vector2D(0, -1);
        turretVec = new Vector2D(0,0);
    }

    public void draw(Graphics2D g){
        g.setColor(SHIP_COLOR);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, RADIUS*2, RADIUS*2);
        g.drawLine((int) position.x, (int)position.y, (int) turretVec.x, (int) turretVec.y);
    }

    public void update(){
        direction.rotate(STEER_RATE * action.turn * DT);

        velocity.addScaled(direction, MAG_ACC * DT * action.thrust);
        velocity.mult(0.99);

        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        setTurretPos(position.x + RADIUS * 2 * Math.cos(direction.angle()), position.y + RADIUS * 2 * Math.sin(direction.angle()));
    }

    private void setTurretPos(double x, double y){
        turretVec.set(x, y);
    }
}
*/
