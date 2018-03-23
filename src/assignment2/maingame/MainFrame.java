package assignment2.maingame;

import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.Score;
import assignment2.utilities.ScoreFrame;
import assignment2.utilities.ScoreWriter;
import assignment2.utilities.controllers.playercontrollers.KeyBindingController;
import assignment2.utilities.MMButtonListener;
import assignment2.map.MapHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

import static assignment2.other.Constants.FRAME_HEIGHT;

/**
 * Created by el16035 on 16/01/2018.
 */

// A JFrame class which holds all the JPanels displayed in the program.
public class MainFrame extends JFrame {
    private static final String WARNING_MESSAGE_EXIT = "Do you want to exit the game?";
    private static final String LABEL_EXIT = "Exit Game";

    private static final String WARNING_MESSAGE_NEW_GAME = "Do you want to start a new game?\nAll progress during your last run will be deleted.";
    private static final String LABEL_NEW_GAME = "New Game";

    private static final String WARNING_MESSAGE_NEW_LEVEL = "Do you want to proceed to the next level?\nenemies will get harder.";
    private static final String LABEL_NEW_LEVEL = "Next Level";
    private View view;
    private Game game;
    private MainMenu menu;
    private EastPanel eastPanel;
    private StatsPanel statsPanel;

    private ScoreWriter scoreWriter;

    MainFrame(String title) {
        super(title);
        game = new Game(this);
        view = new View(game);

        KeyBindingController kbc = new KeyBindingController(view, this);
        game.setPlayerKeys(kbc);

        MapHelper mapHelper = new MapHelper();
        game.setMapHelper(mapHelper);
        view.setMapHelper(mapHelper);


        menu = new MainMenu(game, new MMButtonListener(this));
        openMenu();
        eastPanel = new EastPanel(FRAME_HEIGHT, view.mapHelper);
        view.setEastPanel(eastPanel);

        statsPanel = new StatsPanel(game.playerShip, game, FRAME_HEIGHT);

        getContentPane().add(BorderLayout.CENTER, menu);
        getContentPane().add(BorderLayout.EAST, eastPanel);
        getContentPane().add(BorderLayout.WEST, statsPanel);


        setResizable(false);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void update() {
        game.update();
        view.repaint();
        statsPanel.updateStats();
        eastPanel.repaint();
    }

    boolean nextLevel() {
        int dialogResult = JOptionPane.showConfirmDialog(null, WARNING_MESSAGE_NEW_LEVEL, LABEL_NEW_LEVEL, JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    void gameEnded(PlayerShip playerShip){
        openMenu();
        openScores();
        String name = JOptionPane.showInputDialog(
                this,
                "Please enter your name",
                "Name Registration",
                JOptionPane.INFORMATION_MESSAGE);
        Score playerScore = new Score(name, playerShip.getScore(), new Date());

        scoreWriter.addScoreToFile(playerScore);
        scoreWriter.createScoreList();

        String infoText = "Your final score is: " + playerScore.getScore();
        JOptionPane.showMessageDialog(this, infoText);
        scoreWriter.displayScores();

    }

    public void openMenu() {
        menu.openPanel();
    }

    private void initNewGame() {
        game.newGame();
        view.newGame();
        menu.closePanel();
        eastPanel.updateMiniMap();
        statsPanel.updateStats();
        getContentPane().add(BorderLayout.CENTER, view);
    }

    public void startNewGame() {
        if (game.isGameRunning()) {
            int dialogResult = JOptionPane.showConfirmDialog(null, WARNING_MESSAGE_NEW_GAME, LABEL_NEW_GAME, JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                initNewGame();
            }
        } else {
            initNewGame();
        }
    }

    public void resumeGame() {
        menu.closePanel();
    }

    public void openScores() {
        ScoreFrame sFrame = new ScoreFrame();
        scoreWriter = new ScoreWriter(this, sFrame);
        sFrame.setScoreParser(scoreWriter);
        scoreWriter.createScoreList();
        scoreWriter.displayScores();

    }

    public void exitGame() {
        int dialogResult = JOptionPane.showConfirmDialog(null, WARNING_MESSAGE_EXIT, LABEL_EXIT, JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(1);
        }

    }


    //Options

    public void pauseGame() {
        menu.openPanel();
    }

    public void muteSounds() {
    }


}
