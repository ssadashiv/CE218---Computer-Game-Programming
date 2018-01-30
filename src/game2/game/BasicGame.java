package game2.game;

import game1.game.Asteroid;
import game2.utilities.BasicKeys;
import game2.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game1.game.Constants.DELAY;

/**
 * Created by el16035 on 16/01/2018.
 */
public class BasicGame {

    private static final int N_INITIAL_ASTEROIDS = 5;
    private static BasicKeys keys = new BasicKeys();

    List<Asteroid> asteroids;
    BasicShip ship;

    public BasicGame(){
        asteroids = new ArrayList<>();
        for (int i=0; i<N_INITIAL_ASTEROIDS;i++){
            asteroids.add(Asteroid.makeRandomAsteroid());
        }
        ship = new BasicShip(keys);

    }

    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
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
        asteroids.forEach(Asteroid::update);
        ship.update();
    }
}
