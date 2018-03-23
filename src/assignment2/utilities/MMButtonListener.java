package assignment2.utilities;

import assignment2.maingame.MainFrame;
import assignment2.maingame.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by el16035 on 13/03/2018.
 */

//Class for handling the buttons on the Main Menu
public class MMButtonListener implements ActionListener {

    private MainFrame frame;

    public MMButtonListener(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title = e.getActionCommand();

        switch (title) {
            case MainMenu.NEW_GAME_TEXT:
                frame.startNewGame();

                break;
            case MainMenu.RESUME_TEXT:
                frame.resumeGame();

                break;
            case MainMenu.SCORES:
                frame.openScores();
                break;

            case MainMenu.EXIT_TEXT:
                frame.exitGame();

                break;
            default:
                System.out.println("unknown button pressed");
                break;
        }
    }
}
