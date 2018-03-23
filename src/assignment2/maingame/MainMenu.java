package assignment2.maingame;

import assignment2.other.SharedValues;
import assignment2.utilities.MMButtonListener;
import assignment2.utilities.ScoreFrame;
import assignment2.utilities.Sprite;

import javax.swing.*;
import java.awt.*;

import static assignment2.other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 13/03/2018.
 */
//The class for the initial view the player is greeted with. Contains the buttons "new game", "resume", "scores" and "exit".
public class MainMenu extends JPanel {
    private static final Image BG_IMAGE = Sprite.MAIN_MENU_BG;
    private static final Dimension RIGID_AREA_DIM = new Dimension(0, 10);
    public static final String NEW_GAME_TEXT = "New Game";
    public static final String RESUME_TEXT = "Resume Game";
    public static final String SCORES = "Scores";
    public static final String EXIT_TEXT = "Exit";

    private Game game;
    private MMButtonListener buttonListener;

    private JButton resumeButton;

    MainMenu(Game game, MMButtonListener bl) {
        this.game = game;
        this.buttonListener = bl;
        createButtons();
    }

    private void createButtons() {
        createButton(NEW_GAME_TEXT);
        resumeButton = createButton(RESUME_TEXT);
        createButton(SCORES);
        createButton(EXIT_TEXT);
    }

    //Method to add some space between the buttons
    private void addMargin() {
        add(Box.createRigidArea(RIGID_AREA_DIM));
    }

    private JButton createButton(String text) {
        addMargin();
        JButton b = new JButton(text);
        b.setSize(100, 40);
        b.addActionListener(buttonListener);
        add(b);
        return b;
    }

    void closePanel() {
        setVisible(false);
        SharedValues.gamePaused = false;
    }

    void openPanel() {
        resumeButton.setEnabled(game.isGameRunning());
        setVisible(true);
        SharedValues.gamePaused = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG_IMAGE, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
