package Assignment.GameObjects;

import Assignment.Utilities.Animation;
import Assignment.Utilities.Map.Room;
import Assignment.Utilities.Vector2D;

import java.awt.*;

import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.FRAME_WIDTH;
import static Assignment.Other.Constants.SWITCH_ROOM_ANIMATION_DURATION;

/**
 * Created by el16035 on 16/03/2018.
 */
public class Door extends Obstacle{
    private static Color COLOR = Color.RED;
    private Room room;

    private Vector2D initPos;

    public Door(Room room, Vector2D pos, int width, int height) {
        super(pos, COLOR,  width, height);
        this.room = room;
        setDirection();
    }

    //Flips the door 90 degrees if it is facing the wrong way
    private void setDirection(){
        if (position.x == 0 || position.x > FRAME_WIDTH / 4 *3){
            int temp = width;
            width = height;
            height = temp;
        }

        setPosition();
    }

    private void setPosition(){

        if (position.x > FRAME_WIDTH / 4 *3) {
            position.x += width;

        }

        if (position.y > FRAME_HEIGHT /4 * 3){
            position.y += height;
        }

        initPos = new Vector2D(position);
    }

    public void update() {
        dead = room.canOpenDoors();
    }

    public void closeDoor(){
        //inits the doors as open, then animates them to close.
        if (position.x == 0 || position.x > FRAME_WIDTH / 4 * 3){
            if (position.y < FRAME_WIDTH/2){
                position.y -= height;
            }else{
                position.y += height;
            }
        }


        if (position.y == 0 || position.y > FRAME_HEIGHT / 4 * 3){
            if (position.x < FRAME_HEIGHT/2){
                position.x -= width;
            }else{
                position.x += width;
            }
        }

        Animation.moveObject(this, initPos, SWITCH_ROOM_ANIMATION_DURATION);
    }

}
