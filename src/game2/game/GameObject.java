package game2.game;

import game2.utilities.Vector2D;

import java.awt.*;

import static game2.game.Constants.DT;
import static game2.game.Constants.FRAME_HEIGHT;
import static game2.game.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 05/02/2018.
 */
abstract class GameObject {
    Vector2D position;
    Vector2D velocity;
    boolean dead;
    double radius;

    GameObject(Vector2D position, Vector2D velocity, double radius){
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void hit(){

    }

    void update(){
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D g);
}
