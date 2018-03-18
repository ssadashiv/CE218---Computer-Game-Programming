package Assignment.Utilities.Controllers.AIControllers;

import Assignment.GameObjects.Enemies.ChargeBot;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.PlayerShip;
import Assignment.Other.SharedValues;
import Assignment.Utilities.Vector2D;


import java.util.Timer;
import java.util.TimerTask;

import static Assignment.Other.Constants.DELAY;

/**
 * Created by el16035 on 17/03/2018.
 */
public class ChargeBotCtrl implements AIController {
    private AIAction action = new AIAction();
    private PlayerShip ship;
    private ChargeBot cBot;

    public ChargeBotCtrl(ChargeBot cBot, PlayerShip ship) {
        this.cBot = cBot;
        this.ship = ship;
    }

    @Override
    public AIAction aiAction() {
        new Timer().schedule(new ControlAI(cBot, ship), 0, DELAY);
        return action;
    }


    private class ControlAI extends TimerTask {
        ChargeBot bot;
        PlayerShip ship;

        private ControlAI(ChargeBot bot, PlayerShip ship) {
            this.bot = bot;
            this.ship = ship;
        }

        @Override
        public void run() {
            if (!SharedValues.gamePaused) {
                if (bot.dead) cancel();
                if (ship != null) {
                    if (shipInSector()) {
                        action.direction.set(new Vector2D(ship.position).subtract(bot.position).normalise());
                    } else {
                        action.direction.set(new Vector2D(bot.getChargingStationPos()).subtract(bot.position).normalise());
                    }

                    /*if (shipInSector()){
                        action.direction.set(bot.position.getVecToObject(ship.position).normalise());
                    }else{
                        action.direction.set(bot.position.getVecToObject(bot.getChargingStationPos()).normalise());
                    }*/
                    action.velocity = new Vector2D(action.direction).mult(bot.speed / action.direction.mag());

                   /* System.out.println("VELO="+action.velocity.toString());
                    System.out.println("DIR="+action.direction.toString());*/
                }
            }
        }

        /*private Vector2D getVecToObject(Vector2D v) {
            return new Vector2D(v).subtract(bot.position);
        }*/

        private boolean shipInSector() {
            return new Vector2D(bot.position).subtract(ship.position).mag() <= bot.getSectorRadius();
        }
    }
}
