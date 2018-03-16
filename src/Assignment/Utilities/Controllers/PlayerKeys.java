package Assignment.Utilities.Controllers;

import Assignment.MainGame.MainFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by el16035 on 29/01/2018.
 */
public class PlayerKeys extends KeyAdapter implements Controller {
    private Action action;
    private MainFrame frame;

    public PlayerKeys(MainFrame frame) {
        action = new Action();
        this.frame = frame;
    }

    public Action action() {
        return action;
    }

    // Set of currently pressed keys
    private final Set<Integer> pressed = new HashSet<>();

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
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
                        pressed.clear();
                        frame.pauseGame();
                        break;
                    case KeyEvent.VK_M:
                        frame.muteSounds();
                        break;

                }
            }
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressed.remove(key);

        switch (key) {
            case KeyEvent.VK_W:
                action.thrustNorth = 0;
                break;
            case KeyEvent.VK_A:
                action.thrustWest = 0;
                break;
            case KeyEvent.VK_S:
                action.thrustNorth = 0;
                break;
            case KeyEvent.VK_D:
                action.thrustWest = 0;
                break;

            case KeyEvent.VK_UP:
                action.directionY = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.directionX = 0;
                break;
            case KeyEvent.VK_DOWN:
                action.directionY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.directionX = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}