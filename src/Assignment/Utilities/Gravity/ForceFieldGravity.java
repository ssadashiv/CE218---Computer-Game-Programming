package Assignment.Utilities.Gravity;

import Assignment.GameObjects.BlackHole;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Hole;
import Assignment.GameObjects.WhiteHole;
import Assignment.Other.SharedValues;
import Assignment.Utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 19/03/2018.
 */
final public class ForceFieldGravity implements ForceField {
    private static final int MIN_GRAV_PULL = 20;
    private static final int MAX_GRAV_PULL = 50;
    private static final int GRAV_DIFF = MAX_GRAV_PULL - MIN_GRAV_PULL;

    List<Hole> holes = new ArrayList<>();


    public ForceFieldGravity() {

    }

    public void setHoles(List<Hole> holes){
        this.holes = holes;
    }
    public void update(GameObject object, double DT) {
        if (!SharedValues.gamePaused && !holes.isEmpty()) {
            if (!object.dead) {
                Vector2D combinedPull = new Vector2D(0, 0);
                for (Hole h : holes) {
                    Vector2D vecToObject = new Vector2D(h.position).subtract(object.position).normalise();
                    combinedPull.add(new Vector2D(vecToObject.mult(calcGravPull(h, object))));
                }
                object.velocity.addScaled(combinedPull, DT);
            }
        }

    }

    //Calculates the strength of the pull. The closer the ship is, the stronger the holes will be.
    private double calcGravPull(Hole hole, GameObject object) {
        double dist = hole.position.dist(object.position);
        return (MIN_GRAV_PULL + (GRAV_DIFF * (1 - (dist / FRAME_HEIGHT)))) * hole.getFactor();
    }
}