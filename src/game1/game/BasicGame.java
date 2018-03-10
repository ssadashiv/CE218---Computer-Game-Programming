/*
package game1.game;

import game1.utilities.BasicKeys;
import game1.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game1.game.Constants.DELAY;

*/
/**
 * Created by el16035 on 16/01/2018.
 *//*

public class Game {

    private static final int N_INITIAL_ASTEROIDS = 5;
    private static Keys keys = new Keys();

    List<game1.game.Asteroid> asteroids;
    Ship ship;

    public Game(){
        asteroids = new ArrayList<>();
        for (int i=0; i<N_INITIAL_ASTEROIDS;i++){
            asteroids.add(game1.game.Asteroid.makeRandomAsteroid());
        }
        ship = new Ship(keys);

    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(keys);

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


    private void update(){
        asteroids.forEach(game1.game.Asteroid::update);
        ship.update();
    }
}
*/
