package Assignment.Utilities;

import Assignment.MainGame.MainFrame;
import Assignment.MainGame.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by el16035 on 13/03/2018.
 */

//Class for handeling the buttons on the Main Menu
public class MMButtonListener implements ActionListener {

    private MainFrame frame;

    public MMButtonListener(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title = e.getActionCommand();

        if (title.equals(MainMenu.newGameText)) {
            System.out.println(title + "pressed ");
            frame.startGame(true);

        } else if (title.equals(MainMenu.resumeText)) {
            System.out.println(title + "pressed ");
            frame.startGame(false);

        } else if (title.equals(MainMenu.scoresText)) {
            System.out.println(title + "pressed ");
            frame.openScores();

        } else if (title.equals(MainMenu.settingsText)) {
            System.out.println(title + "pressed ");
            //dosomething
            frame.openSettings();

        } else if (title.equals(MainMenu.exitText)) {
            System.out.println(title + "pressed ");
            //dosomething
            frame.exitGame();
        } else {
            System.out.println("unknown button pressed");
        }
    }

}
