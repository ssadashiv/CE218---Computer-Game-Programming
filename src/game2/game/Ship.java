package game2.game;

import game2.utilities.Action;
import game2.utilities.Controller;
import game2.utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game2.game.Constants.*;

/**
 * Created by el16035 on 29/01/2018.
 */
class Ship extends GameObject{
    static final int RADIUS = 8;

    //Rotation velocity in radians per second
    private static final double STEER_RATE = 2 * Math.PI;

    //Acceleration when thrust is applied
    private static final double MAG_ACC = 200;

    //Constant speed loss factor
    private static final double DRAG = 0.01;

    private static final Color SHIP_COLOR = Color.cyan;
    private static final Color THRUST_COLOR = Color.blue;
    private static final Color TURRET_COLOR = Color.red;

    private Vector2D turretVec;

    private int[] XP, YP, XPTHRUST, YPTHRUST;

    private boolean thrusting;

    private Vector2D direction;
    private Action action;

    Bullet bullet = null;

    public Game game;

    Ship(Controller ctrl, Game game) {
        super(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2), new Vector2D(0,0), RADIUS);
        this.game = game;
        action = ctrl.action();
        createComponents();
    }

    private void createComponents() {
        direction = new Vector2D(0, -1);
        turretVec = new Vector2D(0, 0);
        XP = new int[]{
                2,
                0,
                -2,
                0
        };

        YP = new int[]{
                2,
                -3,
                2,
                1
        };

        XPTHRUST = XP;

        YPTHRUST = new int[]{
                YP[0] + 2,
                YP[3] + 1,
                YP[2] + 2,
                YP[3] + 2
        };


    }

    //TODO: fix these variables
    public void draw(Graphics2D g) {
        //Draw the turret



        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);


        //Draw the turret
        g.setColor(TURRET_COLOR);
        g.drawLine(XP[1], YP[1], XP[1], YP[1] + 1);

        g.setColor(SHIP_COLOR);
        g.fillPolygon(XP, YP, XP.length);

        if (thrusting) {
            g.setColor(THRUST_COLOR);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);

    }

    void update() {

        thrusting = action.thrust != 0;
        direction.rotate(STEER_RATE * action.turn * DT);
        velocity.addScaled(direction, MAG_ACC * DT * action.thrust);
        velocity.mult(1-DRAG);

        //TODO: Fix the turret position.
        setTurretPos(position.x + RADIUS * 3 * Math.cos(direction.angle()), position.y + RADIUS * 3 * Math.sin(direction.angle()));
        super.update();

        if (action.shoot){
            mkBullet();
            action.shoot = false;
        }
    }

    private void setTurretPos(double x, double y) {
        turretVec.set(x, y);
    }

    private void mkBullet(){
        //init the bullet just outside the turret.
        Vector2D butPos = new Vector2D(turretVec);
        Vector2D bulVel = new Vector2D(velocity);
        Vector2D butDir = new Vector2D(direction);

        bulVel.addScaled(direction, Bullet.MUZZLE_VEL);
        bullet = new Bullet(butPos, bulVel, butDir);

        action.shoot = false;
    }

    public void collisionHandling(GameObject other) {
        if (!(other instanceof Bullet)){
            super.collisionHandling(other);
        }
    }
}
