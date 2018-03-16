package Assignment.MainGame;

import Assignment.Utilities.MMButtonListener;

import javax.swing.*;
import java.awt.*;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 13/03/2018.
 */
public class MainMenu extends JPanel {
    public static final String NEW_GAME_TEXT = "New Game";
    public static final String RESUME_TEXT = "Resume Game";
    public static final String SCORES = "Scores";
    public static final String SETTINGS_TEXT = "Settings";
    public static final String EXIT_TEXT = "Exit";
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
        JButton newGame = new JButton(NEW_GAME_TEXT);
        newGame.setSize(100, 40);
        newGame.setVisible(true);

        JButton resume = new JButton(RESUME_TEXT);
        resume.setSize(100, 40);
        resume.setVisible(true);

        JButton scores = new JButton(SCORES);
        scores.setSize(100, 40);
        scores.setVisible(true);

        JButton settings = new JButton(SETTINGS_TEXT);
        settings.setSize(100, 40);
        settings.setVisible(true);

        JButton exit = new JButton(EXIT_TEXT);
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
