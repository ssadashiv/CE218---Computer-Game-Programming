package Assignment.Utilities.Controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by eriklange on 13.03.2018.
 */
public class KeyBindingController implements Controller{


    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    private static final String MOVE_NORTH = "move north";
    private static final String MOVE_WEST = "move west";
    private static final String MOVE_SOUTH = "move south";
    private static final String MOVE_EAST = "move east";

    private static final String LOOK_UP = "look up";
    private static final String LOOK_LEFT = "look left";
    private static final String LOOK_DOWN = "look down";
    private static final String LOOK_RIGHT = "look right";

    private NewAction action;





    public KeyBindingController(JComponent comp){
        InputMap im = comp.getInputMap(IFW);
        ActionMap am = comp.getActionMap();

        im.put(KeyStroke.getKeyStroke("W"), MOVE_NORTH);
        im.put(KeyStroke.getKeyStroke("S"), MOVE_SOUTH);

        im.put(KeyStroke.getKeyStroke("A"), MOVE_WEST);
        im.put(KeyStroke.getKeyStroke("D"), MOVE_EAST);

        im.put(KeyStroke.getKeyStroke("UP"), LOOK_UP);
        im.put(KeyStroke.getKeyStroke("DOWN"), LOOK_DOWN);

        im.put(KeyStroke.getKeyStroke("LEFT"), LOOK_LEFT);
        im.put(KeyStroke.getKeyStroke("RIGHT"), LOOK_RIGHT);


        am.put(MOVE_NORTH, new MoveY(-1));
        am.put(MOVE_SOUTH, new MoveY(1));

        am.put(MOVE_WEST, new MoveX(-1));
        am.put(MOVE_EAST, new MoveX(1));


        am.put(LOOK_UP, new LookY(-1));
        am.put(LOOK_DOWN, new LookY(1));

        am.put(LOOK_LEFT, new LookX(-1));
        am.put(LOOK_RIGHT, new LookX(1));

    }


    @Override
    public NewAction newAction() {
        return action;
    }



    class MoveX extends AbstractAction{
        private int newX;

        private MoveX(int newThrust){
            this.newX = newThrust;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.thrustWest = newX;
        }


    }

    class MoveY extends AbstractAction{
        private int newY;

        private MoveY(int newY){
            this.newY = newY;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.thrustWest = newY;
        }


    }

    class LookX extends AbstractAction{
        private int newLookX;

        private LookX(int newLookX){
           this.newLookX = newLookX;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            action.directionX = newLookX;
        }

    }

    class LookY extends AbstractAction{
        private int newLookY;

        private LookY(int newLookY){
           this.newLookY = newLookY;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            action.directionY = newLookY;
        }

    }

    /*



        /*
        *
        * pressed.add(e.getKeyCode());
        if (!pressed.isEmpty()) {
            for (Integer key : pressed) {
                switch (key) {
                    case KeyEvent.VK_W:
                        action.thrustNorth = -1;
                        break;
                    case KeyEvent.VK_A:
                        action.thrustWest = -1;
                        break;
                    case KeyEvent.VK_S:
                        action.thrustNorth = 1;
                        break;
                    case KeyEvent.VK_D:
                        action.thrustWest = 1;
                        break;

                    case KeyEvent.VK_UP:
                        action.directionY = -1;
                        break;
                    case KeyEvent.VK_LEFT:
                        action.directionX = -1;
                        break;
                    case KeyEvent.VK_DOWN:
                        action.directionY = 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        action.directionX = 1;
                        break;
                    case KeyEvent.VK_SPACE:
                        action.shoot = true;
                        break;

                    case KeyEvent.VK_ESCAPE:
                        System.out.println("escape pressed");
                        pressed.clear();
                        frame.pauseGame();
                        break;
                    case KeyEvent.VK_M:
                        System.out.println("mute pressed");
                        frame.muteSounds();
                        break;

                }
            }
        }
        * */
}
