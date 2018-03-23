package assignment2.utilities.gravity;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.wormholes.Wormhole;
import assignment2.other.SharedValues;
import assignment2.utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static assignment2.other.Constants.FRAME_HEIGHT;

/**
 * Created by el16035 on 19/03/2018.
 */
//Class for calculating the gravitational pull on all the GameObjects except from the Obstacle
final public class ForceFieldGravity implements ForceField {
    private static final int MIN_GRAV_PULL = 20;
    private static final int MAX_GRAV_PULL = 50;
    private static final int GRAV_DIFF = MAX_GRAV_PULL - MIN_GRAV_PULL;

    List<Wormhole> wormholes = new ArrayList<>();


    public ForceFieldGravity() {

    }

    public void setWormholes(List<Wormhole> wormholes){
        this.wormholes = wormholes;
    }
    public void update(GameObject object, double DT) {
        if (!SharedValues.gamePaused && !wormholes.isEmpty()) {
            if (!object.dead) {
                Vector2D combinedPull = new Vector2D(0, 0);
                for (Wormhole h : wormholes) {
                    Vector2D vecToObject = new Vector2D(h.position).subtract(object.position).normalise();
                    combinedPull.add(new Vector2D(vecToObject.mult(calcGravPull(h, object))));
                }
                object.velocity.addScaled(combinedPull, DT);
            }
        }

    }

    //Calculates the strength of the pull. The closer the ship is, the stronger the wormholes will be.
    private double calcGravPull(Wormhole wormhole, GameObject object) {
        double dist = wormhole.position.dist(object.position);
        return (MIN_GRAV_PULL + (GRAV_DIFF * (1 - (dist / FRAME_HEIGHT)))) * wormhole.getFactor();
    }
}