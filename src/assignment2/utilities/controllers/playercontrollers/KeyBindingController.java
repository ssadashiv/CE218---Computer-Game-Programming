
package assignment2.utilities.controllers.playercontrollers;

import assignment2.maingame.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by eriklange on 13.03.2018.
 */
//KeyBindingController for the player.
public class KeyBindingController implements Controller {


    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    private static final String MOVE_NORTH = "move north";
    private static final String MOVE_SOUTH = "move south";
    private static final String STOP_Y_AXIS = "stop y axis";


    private static final String MOVE_WEST = "move west";
    private static final String MOVE_EAST = "move east";
    private static final String STOP_X_AXIS = "stop x axis";

    private static final String LOOK_UP = "look up";
    private static final String LOOK_LEFT = "look left";
    private static final String LOOK_DOWN = "look down";
    private static final String LOOK_RIGHT = "look right";

    private static final String STOP_LOOK_X = "stop look x";
    private static final String STOP_LOOK_Y = "stop look y";

    private static final String SHOOT_BULLET = "shoot bullet";
    private static final String STOP_SHOOTING = "stop shooting";
    private static final String OPEN_MENU = "open menu";


    private Action action;

    private MainFrame frame;

    public KeyBindingController(JComponent comp, MainFrame frame) {
        this.frame = frame;
        action = new Action();
        InputMap im = comp.getInputMap(IFW);
        ActionMap am = comp.getActionMap();


        /*
        * ON PRESS
        * */
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), MOVE_NORTH);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), MOVE_SOUTH);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), MOVE_WEST);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), MOVE_EAST);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), LOOK_UP);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), LOOK_DOWN);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), LOOK_LEFT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), LOOK_RIGHT);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), SHOOT_BULLET);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), OPEN_MENU);


        /*
        * ON RELEASE
        * */
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), STOP_Y_AXIS);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), STOP_Y_AXIS);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), STOP_X_AXIS);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), STOP_X_AXIS);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), STOP_LOOK_Y);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), STOP_LOOK_Y);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), STOP_LOOK_X);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), STOP_LOOK_X);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), STOP_SHOOTING);




         /*
        * ACTIONS
        * */
        am.put(MOVE_NORTH, new MoveY(-1));
        am.put(MOVE_SOUTH, new MoveY(1));
        am.put(MOVE_WEST, new MoveX(-1));
        am.put(MOVE_EAST, new MoveX(1));

        am.put(STOP_Y_AXIS, new MoveY(0));
        am.put(STOP_X_AXIS, new MoveX(0));

        am.put(LOOK_UP, new LookY(-1));
        am.put(LOOK_DOWN, new LookY(1));
        am.put(LOOK_LEFT, new LookX(-1));
        am.put(LOOK_RIGHT, new LookX(1));

        am.put(STOP_LOOK_Y, new LookY(0));
        am.put(STOP_LOOK_X, new LookX(0));


        am.put(SHOOT_BULLET, new ShootBullet(true));
        am.put(STOP_SHOOTING, new ShootBullet(false));


        am.put(OPEN_MENU, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.openMenu();
            }
        });
    }


    @Override
    public Action action() {
        return action;
    }


    class MoveX extends AbstractAction {
        private int newX;

        private MoveX(int newThrust) {
            this.newX = newThrust;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.thrustWest = newX;

        }


    }

    class MoveY extends AbstractAction {
        private int newY;

        private MoveY(int newY) {
            this.newY = newY;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.thrustNorth = newY;

        }


    }

    class LookX extends AbstractAction {
        private int newLookX;

        private LookX(int newLookX) {
            this.newLookX = newLookX;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.directionX = newLookX;

        }

    }

    class LookY extends AbstractAction {
        private int newLookY;

        private LookY(int newLookY) {
            this.newLookY = newLookY;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.directionY = newLookY;
        }

    }

    class ShootBullet extends AbstractAction {
        private boolean isShooting;

        private ShootBullet(boolean isShooting) {
            this.isShooting = isShooting;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.shoot = isShooting;
        }

    }


}

