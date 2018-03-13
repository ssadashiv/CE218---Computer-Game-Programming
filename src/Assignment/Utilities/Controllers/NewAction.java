package Assignment.Utilities.Controllers;

/**
 * Created by el16035 on 13/03/2018.
 */
public class NewAction {
    public int thrustNorth; // 0 = off, 1 = on, -1 = going south;
    public int thrustWest; // 0 = off, 1 = on, -1 = going east;
    public int directionX; // -1=pointing west, 0=default, 1=pointing east
    public int directionY; // -1=pointing north, 0=default, 1=pointing south

    //public int turn; // -1 = left turn, 0 = no turn, 1 = right turn
    public boolean shoot;
}
