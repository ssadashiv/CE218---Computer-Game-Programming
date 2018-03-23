package assignment2.utilities.objectassist;

import assignment2.gameobjects.GameObject;
import assignment2.utilities.Vector2D;

import java.util.Timer;
import java.util.TimerTask;

import static assignment2.other.Constants.DELAY;

/**
 * Created by el16035 on 16/03/2018.
 */

//Class which handles the animation of the GameObjects
public class Animation {

    public static void moveObject(GameObject movingObject, Vector2D endPos, long duration){
        movingObject.canMove = false;

        int frames = (int) duration / DELAY;
        Vector2D startPos = movingObject.position;

        double xPerFrame = (endPos.x - startPos.x) / frames;
        double yPerFrame = (endPos.y - startPos.y) / frames;


        new Timer().schedule(new TimerTask() {
            int frameCount = 1;
            @Override
            public void run() {
                frameCount++;
                startPos.x += xPerFrame;
                startPos.y += yPerFrame;
                if (frameCount == frames) {
                    movingObject.canMove = true;
                    movingObject.switchingRooms = false;
                    cancel();
                }

            }
        }, 0, DELAY);
        movingObject.velocity.set(0, 0);
    }
}
