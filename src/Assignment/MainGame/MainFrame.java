package Assignment.MainGame;

import Other.Constants;
import Utilities.MMButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
//TODO: Add an KeyListener to ESCAPE which pauses the game and opens the menu.

public class MainFrame extends JFrame {
    private View view;
    private Game game;
    private MainMenu menu;



    MainFrame(String title){
        super(title);
        game = new Game(this);
        view = new View(game);

        addKeyListener(game.playerKeys);

        MMButtonListener bl = new MMButtonListener(this);

        menu = new MainMenu(bl);
        menu.setSize(Constants.FRAME_SIZE);
        getContentPane().add(BorderLayout.CENTER, menu);

        menu.openPanel();

        pack();
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


    public void startGame(boolean startNew){
        menu.closePanel();
        if (startNew) {
            game.newGame();
            getContentPane().add(BorderLayout.CENTER, view);
        }

        setFocusable(true);
        requestFocusInWindow();

    }

    public void openScores(){
        System.out.println("openScores in MainFrame run ");
    }

    public void openSettings(){
        System.out.println("openSettings in MainFrame run");
    }

    public void exitGame(){
        System.out.println("exitGame in MainFrame run");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to exit the game?","Exit?",dialogButton);

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
