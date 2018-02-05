package game2.game;

import game2.utilities.Keys;
import game2.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static game2.game.Constants.DELAY;

/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    private static final int N_INITIAL_ASTEROIDS = 5;
    private static Keys keys = new Keys();

    //List<Asteroid> asteroids;
    Ship ship;

    List<GameObject> objects;

    public Game(){
        objects = new ArrayList<>();

        ship = new Ship(keys);
        objects.add(ship);
        for (int i=0; i<N_INITIAL_ASTEROIDS;i++){
            objects.add(Asteroid.makeRandomAsteroid());
        }
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
        List<GameObject> alive = objects.stream().filter(o -> !o.dead).collect(Collectors.toList());

        if (ship.bullet != null) {
            alive.add(ship.bullet);
            ship.bullet = null;
        }

        objects.clear();
        objects.addAll(alive);

        for (GameObject o : objects){
            o.update();
        }
    }
}
