package assignment2.utilities.controllers.enemycontrollers;

import assignment2.utilities.Vector2D;

/**
 * Created by el16035 on 17/03/2018.
 */

//The action for the enemies
public class AIAction {
    public Vector2D velocity = new Vector2D(0,0);
    public Vector2D direction = new Vector2D(1,0);

    public boolean targetInRange;
}
