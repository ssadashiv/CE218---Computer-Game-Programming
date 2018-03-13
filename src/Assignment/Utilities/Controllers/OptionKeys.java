package Assignment.Utilities.Controllers;

import Assignment.MainGame.MainFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by el16035 on 13/03/2018.
 */

//Controller class to handle ESCAPE, M(ute) etc.
public class OptionKeys implements KeyListener {

    private MainFrame frame;

    public OptionKeys(MainFrame frame){
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        switch (key){

        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }
}
