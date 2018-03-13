/*
package Utilities.Controllers;

import MainGame.MainFrame;
import Utilities.Action;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

*/
/**
 * Created by el16035 on 29/01/2018.
 *//*

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

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("Keys pressed");

        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_DOWN:
                action.thrust = -1;
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
            case KeyEvent.VK_ESCAPE:
                System.out.println("escape pressed");
                frame.pauseGame();
                break;
            case KeyEvent.VK_M:
                System.out.println("mute pressed");
                frame.muteSounds();
                break;

        }

        //System.out.println("thrust:" + action.thrust);
        //System.out.println("turn:" + action.turn);
        //System.out.println("shoot:" + action.shoot);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_DOWN:
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
*/
