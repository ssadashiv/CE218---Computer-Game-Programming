package game2.game;

import game2.utilities.controllers.Action;
import game2.utilities.controllers.Controller;
import game2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by el16035 on 27/02/2018.
 */
abstract class Ship extends GameObject {
    Color color;
    Action action;
    


    Ship(Controller ctrl, Color color, Vector2D pos, Vector2D vel, Vector2D direction, int radius, Clip deathSound, Image image){
        super(pos, vel, direction, radius, deathSound, image);
        this.color = color;
        this.action = ctrl.action();

    }

}
