package Assignment.Utilities;

import Assignment.GameObjects.GameObject;

import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;

/**
 * Created by el16035 on 16/03/2018.
 */
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
                    cancel();
                }

            }
        }, 0, DELAY);
        movingObject.velocity.set(0, 0);
    }
}
