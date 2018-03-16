package Assignment.Utilities;

import Assignment.GameObjects.DoorButton;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


import static Assignment.Other.Constants.WALL_REFLECT;

/**
 * Created by el16035 on 14/03/2018.
 */
public class HitDetection {
    public static final String NORTH = "NORTH";
    public static final String SOUTH = "SOUTH";
    public static final String WEST = "WEST";
    public static final String EAST = "EAST";


    public static boolean sameClass(GameObject obj1, GameObject obj2){
        return obj1.getClass() == obj2.getClass();
    }
    public static String whichDirection(GameObject objOne, GameObject objTwo){
        Rectangle oneBounds = objOne.getBounds();
        Rectangle twoBounds = objTwo.getBounds();

        Vector2D objOneVel = new Vector2D(objOne.velocity);

        Rectangle r = oneBounds.intersection(twoBounds);

        if (r.width >= r.height){
            //Hits from North or South

            if ((r.y == twoBounds.y && objOneVel.y > 0) || (r.y != twoBounds.y && objOneVel.y < 0)){
                return (oneBounds.y + (oneBounds.height/2) < twoBounds.y + (twoBounds.height/2)) ? NORTH : SOUTH;
            }

        } else{
            //Hit from east or west or on the edge of the obstacle
            if ((r.x == twoBounds.x && objOneVel.x > 0) || (r.x != twoBounds.x && objOneVel.x < 0)){
                return (oneBounds.x + (oneBounds.width/2) < twoBounds.x + (twoBounds.width /2)) ? WEST : EAST;
            }

        }
        return "";
    }

    public static void HitObstacle(GameObject obj1, GameObject obj2){
        GameObject otherObject;
        Obstacle obstacle;

        if (obj1 instanceof Obstacle) {
            otherObject = obj2;
            obstacle = (Obstacle) obj1;
        } else {
            otherObject = obj1;
            obstacle = (Obstacle) obj2;
        }

        Vector2D obsPos = new Vector2D(obstacle.position);
        Rectangle obsBounds = obstacle.getBounds();

        String direction = HitDetection.whichDirection(otherObject, obstacle);

        switch (direction) {
            case HitDetection.NORTH:
                otherObject.position.y = obsPos.y - otherObject.radius;
                otherObject.velocity.y *= WALL_REFLECT;
                break;
            case HitDetection.SOUTH:
                otherObject.position.y = obsPos.y + obsBounds.height + otherObject.radius;
                otherObject.velocity.y *= WALL_REFLECT;
                break;
            case HitDetection.WEST:
                otherObject.position.x = obsPos.x - otherObject.radius;
                otherObject.velocity.x *= WALL_REFLECT;
                break;
            case HitDetection.EAST:
                otherObject.position.x = obsPos.x + obsBounds.width + otherObject.radius;
                otherObject.velocity.x *= WALL_REFLECT;
                break;

        }
    }

    public static void HitDoorButton(GameObject obj1, GameObject obj2){
        DoorButton button = obj1 instanceof DoorButton ? (DoorButton) obj1 : (DoorButton) obj2;
        button.pressButton();
    }

    public static boolean isSameGrid(GameObject obj1, GameObject obj2){
        List<int[]> smallestGrid;
        List<int[]> largestGrid;

        if (obj1.gridCoordinates.size() > obj2.gridCoordinates.size()){
            smallestGrid = obj2.gridCoordinates;
            largestGrid = obj1.gridCoordinates;
        }else{
            smallestGrid = obj1.gridCoordinates;
            largestGrid = obj2.gridCoordinates;
        }

        for (int i =0;i<smallestGrid.size();i++){
            if (Arrays.equals(smallestGrid.get(i), largestGrid.get(i))) return true;
        }

        return false;
    }
}
