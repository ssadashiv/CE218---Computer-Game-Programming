package Assignment.MainGame;

import Assignment.Utilities.Controllers.KeyBindingController;
import Assignment.Utilities.MMButtonListener;
import Assignment.Utilities.Map.MapHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
//TODO: Add an KeyListener to ESCAPE which pauses the game and opens the menu.

public class MainFrame extends JFrame {
    private static final String WARNING_MESSAGE_EXIT = "Do you want to exit the game?";
    private static final String LABEL_EXIT = "Exit Game";

    private static final String WARNING_MESSAGE_NEW_GAME = "Do you want to start a new game?\nAll progress during your last run will be deleted.";
    private static final String LABEL_NEW_GAME = "New Game";
    private View view;
    private Game game;
    private MainMenu menu;



    MainFrame(String title){
        super(title);
        game = new Game();
        view = new View(game);

        KeyBindingController kbc = new KeyBindingController(view, this);
        game.setPlayerKeys(kbc);

        MapHelper mapHelper = new MapHelper();
        game.setMapHelper(mapHelper);
        view.setMapHelper(mapHelper);

        MMButtonListener bl = new MMButtonListener(this);

        menu = new MainMenu(bl);
        openMenu();
        EastPanel eastPanel = new EastPanel(this, view.mapHelper, game.playerShip);

        view.setEastPanel(eastPanel);

        getContentPane().add(BorderLayout.CENTER, menu);
        getContentPane().add(BorderLayout.EAST, eastPanel);

        pack();
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    void update(){
        game.update();
        view.repaint();
    }
    public boolean isPaused(){
        return menu.isOpen();
    }

    public void openMenu(){
        menu.openPanel();

    }

    private void initNewGame(){
        game.newGame();
        menu.closePanel();
        getContentPane().add(BorderLayout.CENTER, view);
    }

    public void startNewGame(){
        if (game.isGameRunning()){
            int dialogResult = JOptionPane.showConfirmDialog (null, WARNING_MESSAGE_NEW_GAME, LABEL_NEW_GAME, JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION){
                initNewGame();
            }
        }else{
            initNewGame();
        }
    }

    public void resumeGame(){
        if (game.isGameRunning()){
            menu.closePanel();
        }
    }

    public void openScores(){
        System.out.println("openScores in MainFrame run ");
    }

    public void openSettings(){
        System.out.println("openSettings in MainFrame run");
    }

    public void exitGame(){
        System.out.println("exitGame in MainFrame run");
        int dialogResult = JOptionPane.showConfirmDialog (null, WARNING_MESSAGE_EXIT, LABEL_EXIT, JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION){
            System.exit(1);
        }

    }


    //Options

    public void pauseGame(){
        menu.openPanel();
    }

    public void muteSounds(){
        //TODO: mute sounds
    }


}
