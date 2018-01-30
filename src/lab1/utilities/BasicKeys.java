package lab1.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by el16035 on 29/01/2018.
 */
public class BasicKeys extends KeyAdapter implements BasicController {
    Action action;

    public BasicKeys(){
        action = new Action();
    }

    public Action action(){
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
