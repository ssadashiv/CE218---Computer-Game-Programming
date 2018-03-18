package Assignment.GameObjects;

import Assignment.Utilities.Animation;
import Assignment.Map.Room;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import java.awt.*;

import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.FRAME_WIDTH;
import static Assignment.Other.Constants.SWITCH_ROOM_ANIMATION_DURATION;

/**
 * Created by el16035 on 16/03/2018.
 */
public class Door extends Obstacle{
    private final static Image IMAGE = Sprite.DOOR;
    private Room room;

    private Vector2D initPos;

    public Door(Room room, Vector2D pos, int width, int height) {
        super(pos, IMAGE,  width,  height);
        this.room = room;
        setDirection();
    }

    //Flips the door 90 degrees if it is facing the wrong way
    private void setDirection(){
        setPosition();

        if (position.y <= FRAME_HEIGHT /4 || position.y > FRAME_HEIGHT / 4 *3){
            int temp = width;
            width = height;
            height = temp;

            //direction.y = 1;
        }


    }

    private void setPosition() {

        if (position.x > FRAME_WIDTH / 4 * 3) {
            position.x += width / 2;

        } else if (position.x < FRAME_WIDTH / 4) {
            position.x -= width / 2;
        }
        if (position.y > FRAME_HEIGHT / 4 * 3) {
            position.y += width / 2;
        } else if (position.y < FRAME_HEIGHT / 4) {
            position.y -= width / 2;
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
