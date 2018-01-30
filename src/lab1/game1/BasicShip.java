package lab1.game1;

import static lab1.game1.Constants.DT;
import static lab1.game1.Constants.FRAME_HEIGHT;
import static lab1.game1.Constants.FRAME_WIDTH;

import lab1.utilities.Action;
import lab1.utilities.BasicController;
import lab1.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 29/01/2018.
 */
public class BasicShip {
    public static final int RADIUS = 8;

    //Rotation velocity in radians per second
    public static final double STEER_RATE = 2*Math.PI;

    //Acceleration when thrust is applied
    public static final double MAG_ACC = 200;

    //Constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    public Vector2D position; //On frame
    public Vector2D velocity; //Per second

    /*
    Direction in which the nose of the ship is pointing
    This will be the direction in which thrust is applied
    It is a unit vector representing the angle by which the ship has rotated
     */
    public Vector2D direction;
    private BasicController ctrl;

    public BasicShip(BasicController ctrl){
        this.ctrl = ctrl;

        position = new Vector2D(FRAME_WIDTH /2, FRAME_HEIGHT/2);
        velocity = new Vector2D(0,0);
        direction = new Vector2D(0, -1);
    }

    public void draw(Graphics2D g){
        g.setColor(COLOR);
        g.drawOval((int) position.x - RADIUS, (int) position.y - RADIUS, RADIUS*2, RADIUS*2);
    }

    public void update(){

        Action action = ctrl.action();

        direction.rotate(STEER_RATE * action.turn);

        position.x += velocity.x * DT * action.thrust;
        position.y += velocity.y * DT * action.thrust;

        position.x = (position.x + FRAME_WIDTH) % FRAME_WIDTH;
        position.y = (position.y + FRAME_HEIGHT) % FRAME_HEIGHT;

    }
}
