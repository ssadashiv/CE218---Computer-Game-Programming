package Assignment.MainGame;

import Assignment.Utilities.MMButtonListener;

import javax.swing.*;
import java.awt.*;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 13/03/2018.
 */
public class MainMenu extends JPanel {
    public static String newGameText = "New Game";
    public static String resumeText = "Resume Game";
    public static String scoresText = "Scores";
    public static String settingsText = "Settings";
    public static String exitText = "Exit";
    private boolean isOpen = true;

    private MMButtonListener buttonListener;

    //TODO: Create actionlisteners for the buttons
    public MainMenu(MMButtonListener bl) {
        this.buttonListener = bl;
        createButtons();
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }



    public boolean isOpen() {
        return isOpen;
    }


    private void createButtons() {
        JButton newGame = new JButton(newGameText);
        newGame.setSize(100, 40);
        newGame.setVisible(true);

        JButton resume = new JButton(resumeText);
        resume.setSize(100, 40);
        resume.setVisible(true);

        JButton scores = new JButton(scoresText);
        scores.setSize(100, 40);
        scores.setVisible(true);

        JButton settings = new JButton(settingsText);
        settings.setSize(100, 40);
        settings.setVisible(true);

        JButton exit = new JButton(exitText);
        exit.setSize(100, 40);
        exit.setVisible(true);


        newGame.addActionListener(buttonListener);
        resume.addActionListener(buttonListener);
        scores.addActionListener(buttonListener);
        settings.addActionListener(buttonListener);
        exit.addActionListener(buttonListener);

        add(newGame);
        add(resume);
        add(scores);
        add(settings);
        add(exit);
    }

    public void closePanel() {
        setVisible(false);
        isOpen = false;
    }

    public void openPanel() {
        setVisible(true);
        isOpen = true;
    }

    @Override
    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
