package assignment2.other;

import assignment2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
//Constants used by several classes
public class Constants {
    public static final boolean TESTING = false;

    public static final int FRAME_HEIGHT = 640;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);

    //sleep time between two frames
    public static final int DELAY = 20;
    public static final double DT = DELAY / 1000.0;
    public static final double DRAWING_SCALE = 5.0;

    //multiply any object which crashes against a wall with this
    public static final double WALL_REFLECT = -0.2;

    //Constant speed loss factor for any controlled GameObject
    public static final double DRAG = 0.01;

    //Vectors for immobile game objects
    public static final Vector2D VEC_PLACEHOLDER = new Vector2D(0,0);

    public static final long SWITCH_ROOM_ANIMATION_DURATION = 700;

}
