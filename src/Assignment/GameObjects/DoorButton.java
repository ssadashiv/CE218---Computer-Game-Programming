package Assignment.GameObjects;

import Assignment.Utilities.HitDetection;
import Assignment.Map.Room;
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
        super(position, VEC_PLACEHOLDER, VEC_PLACEHOLDER, radius*2, radius*2,  SOUND, NOT_PRESSED);
        this.room = room;
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip && !isPressed;
    }

    public void pressButton(){
        room.removeObjective(this);
        setImage(PRESSED);
    }

    @Override
    public void hitDetected(GameObject other) {
        HitDetection.HitDoorButton(this);
    }
}
