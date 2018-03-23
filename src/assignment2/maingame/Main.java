package assignment2.maingame;


import assignment2.other.Constants;
import assignment2.other.SharedValues;

import javax.swing.*;

/**
 * Created by el16035 on 10/03/2018.
 */

//The main class.
public class Main {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainFrame frame = new MainFrame("CE218 Assignment 1, Game. Erik Lange 1601219");

        while (true){
            System.out.print("");
            if (!SharedValues.gamePaused){
                frame.update();
                try {
                    Thread.sleep(Constants.DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
