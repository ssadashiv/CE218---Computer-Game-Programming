package Assignment.Other;

import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);

    //sleep time between two frames
    public static final int DELAY = 20;
    public static final double DT = DELAY / 1000.0;
    public static final double DRAWING_SCALE = 5.0;

    //multiply any object which crashes against a wall with this
    public static final double WALL_REFLECT = -0.2;

    //TODO: find a way for objects to reflect off each other.
    public static final double OBJECT_REFLECT = 0;

    //Constant speed loss factor for any controlled GameObject
    public static final double DRAG = 0.01;
}
