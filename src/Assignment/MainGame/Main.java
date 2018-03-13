package Assignment.MainGame;


import Assignment.Other.Constants;

/**
 * Created by el16035 on 10/03/2018.
 */

/*
* TODO:
* Add levels of enemies
*   - By adding a list of lists of types of enemies
*   List<List<GameObject>> levelEnemies = new List<>();
*
* */

public class Main {
    public static void main(String[] args) throws Exception {
        MainFrame frame = new MainFrame("Assignment 2 Game. Erik Lange 1601219");

        while (true){
            System.out.print("");
            if (!frame.isPaused()){
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
