package assignment2.gameobjects.obstacles;

import assignment2.utilities.objectassist.Animation;
import assignment2.map.Room;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import java.awt.*;

import static assignment2.other.Constants.FRAME_HEIGHT;
import static assignment2.other.Constants.FRAME_WIDTH;
import static assignment2.other.Constants.SWITCH_ROOM_ANIMATION_DURATION;
import static assignment2.other.SharedValues.cellSize;

/**
 * Created by el16035 on 16/03/2018.
 */

//Class for the Doors in every room of the game.
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

    //Sets the correct position
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

        if (position.x <= cellSize || position.x > FRAME_WIDTH / 4 * 3){
            if (position.y < FRAME_WIDTH/2){
                position.y -= height;
            }else{
                position.y += height;
            }
        }


        if (position.y <= cellSize || position.y > FRAME_HEIGHT / 4 * 3){
            if (position.x < FRAME_HEIGHT/2){
                position.x -= height;
            }else{
                position.x += height;
            }
        }


    }

    public void update() {
        dead = room.canOpenDoors();
    }

    //close the door after the player enters the room
    public void closeDoor(){
        //inits the doors as open, then animates them to close.
        Animation.moveObject(this, initPos, SWITCH_ROOM_ANIMATION_DURATION/2);
    }

}
