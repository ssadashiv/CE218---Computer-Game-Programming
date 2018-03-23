package assignment2.gameobjects.obstacles;

import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.map.Room;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Sprite;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static assignment2.other.Constants.VEC_PLACEHOLDER;

/**
 * Created by el16035 on 16/03/2018.
 */

//An objective in a room. A player has to press the button & kill all the enemies to proceed to the next rom.
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
