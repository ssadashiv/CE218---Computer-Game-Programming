package Assignment.Utilities;

import Assignment.GameObjects.GameObject;

import java.awt.*;

import static Assignment.Other.Constants.DT;

/**
 * Created by el16035 on 14/03/2018.
 */
public class HitDetection {

    //Methods testing which side objOne comparing to objTwo
    /*public static boolean hitFromNorth(GameObject objOne, GameObject objTwo) {
        Rectangle objOnePos = getOldPos(objOne);
        Rectangle objTwoPos = objTwo.getBounds();


        boolean testCase1 = objOnePos.x + (objOnePos.width) >= objTwoPos.x;
        boolean testCase2 = objOnePos.x <= (objTwoPos.x + objTwoPos.width);
        boolean testCase3 = objOnePos.y + (objOnePos.height)<= objTwoPos.y;

        if (testCase1 && testCase2 && testCase3) {
            System.out.println("hit from north");
            return true;
        }
        return false;
    }
    public static boolean hitFromSouth(GameObject objOne, GameObject objTwo) {
        return false;
    }
    public static boolean hitFromWest(GameObject objOne, GameObject objTwo) {
        return false;
    }
    public static boolean hitFromEast(GameObject objOne, GameObject objTwo) {
        Rectangle objOnePos = getOldPos(objOne);
        Rectangle objTwoPos = objTwo.getBounds();

        boolean testCase1 = objOnePos.y + objOnePos.height <= objTwoPos.y;
        boolean testCase2 = objOnePos.y >= objTwoPos.y + objTwoPos.height;
        boolean testCase3 = objOnePos.x >= objTwoPos.x + objTwoPos.width;

        if (testCase1 && testCase2 && testCase3) {
            System.out.println("hit from east");
            return true;
        }
        return false;
    }

    private static Rectangle getOldPos(GameObject obj){
        Vector2D origVel = new Vector2D(obj.velocity).mult(-1);
        Rectangle bounds = obj.getBounds();
        bounds.x -= origVel.x * DT;
        bounds.y -= origVel.y * DT;
        return bounds;
    }*/

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
