package game2;

import game2.game.Game;
import game2.game.View;
import game2.utilities.JEasyFrame;
import game2.utilities.controllers.Keys;

import static game2.Constants.DELAY;

/**
 * Created by el16035 on 10/03/2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(new Keys());

        while (true){
            game.update();
            view.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
