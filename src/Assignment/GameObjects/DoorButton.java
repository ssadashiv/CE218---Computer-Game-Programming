package Assignment.GameObjects;

import Assignment.Utilities.Map.Room;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Sprite;
import Assignment.Utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Assignment.Other.Constants.VEC_PLACEHOLDER;

/**
 * Created by el16035 on 16/03/2018.
 */
public class DoorButton extends GameObject {
    private static final Image NOT_PRESSED = Sprite.DOOR_BUTTON_NOT_PRESSED;
    private static final Image PRESSED = Sprite.DOOR_BUTTON_PRESSED;
    private static final Clip SOUND = SoundManager.beat2;

    private boolean isPressed = false;
    private Room room;

    public DoorButton(Room room, Vector2D position, int radius){
        super(position, VEC_PLACEHOLDER, VEC_PLACEHOLDER, radius, SOUND, NOT_PRESSED);
        this.room = room;
        room.addObjective(this);
    }

    public boolean isPressed(){
        return isPressed;
    }


    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip && !isPressed;
    }

    public void collisionHandling(GameObject other) {
        if (canHit(other)) {
            super.collisionHandling(other);
        }
    }

    public void pressButton(){
        room.removeObjective(this);
        isPressed = true;
        setImage(PRESSED);
    }
}
