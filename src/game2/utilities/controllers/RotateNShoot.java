package game2.utilities.controllers;

/**
 * Created by el16035 on 27/02/2018.
 */
public class RotateNShoot implements Controller {
    Action action = new Action();

    @Override
    public Action action(){
        action.shoot = true;
        action.turn = 1;
        return action;
    }
}
