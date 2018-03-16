package Assignment.Utilities;

import Assignment.GameObjects.GameObject;

import java.awt.*;

import static Assignment.Other.Constants.DT;

/**
 * Created by el16035 on 14/03/2018.
 */
public class HitDetection {
    public static String whichDirection(GameObject objOne, GameObject objTwo){
        Rectangle oneBounds = objOne.getBounds();
        Rectangle twoBounds = objTwo.getBounds();

        Vector2D objOneVel = new Vector2D(objOne.velocity);

        Rectangle r = oneBounds.intersection(twoBounds);

        if (r.width >= r.height){
            //Hits from North or South

            if ((r.y == twoBounds.y && objOneVel.y > 0) || (r.y != twoBounds.y && objOneVel.y < 0)){
                return (oneBounds.y + (oneBounds.height/2) < twoBounds.y + (twoBounds.height/2)) ? "north" : "south";
            }

        } else{
            //Hit from east or west or on the edge of the obstacle
            if ((r.x == twoBounds.x && objOneVel.x > 0) || (r.x != twoBounds.x && objOneVel.x < 0)){
                return (oneBounds.x + (oneBounds.width/2) < twoBounds.x + (twoBounds.width /2)) ? "west" : "east";
            }

        }

        return "";
    }

}
