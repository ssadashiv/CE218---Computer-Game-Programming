package game2.game;

import game2.utilities.Keys;
import game2.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static game2.game.Constants.DELAY;

/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    //TODO: Make several levels. Array with N_INITIAL_ASTEROIDS.
    private static final int N_INITIAL_ASTEROIDS = 2;
    private static final int ASTEROID_INIT_SIZE = 2;
    private static Keys keys = new Keys();

    private int score = 0;
    //List<Asteroid> asteroids;
    private Ship ship;

    List<GameObject> objects;

    public Game(){
        objects = new ArrayList<>();

        ship = new Ship(keys, this);
        objects.add(ship);
        for (int i=0; i<N_INITIAL_ASTEROIDS;i++){
            objects.add(Asteroid.makeRandomAsteroid(ASTEROID_INIT_SIZE));
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

    private void spawnShip(){

    }


    private void update(){
        Iterator<GameObject> it = objects.iterator();

        List<GameObject> newItems = new ArrayList<>();
        while (it.hasNext()){
            GameObject o = it.next();
            if (o instanceof Asteroid){
                if (((Asteroid) o).spawnedAsteroids != null){
                    newItems.addAll(((Asteroid) o).spawnedAsteroids);
                }
            }
        }
        objects.addAll(newItems);

        objects.forEach(GameObject::update);
        List<GameObject> alive = objects.stream().filter(o -> !o.dead).collect(Collectors.toList());

        if (ship.bullet != null) {
            alive.add(ship.bullet);
            ship.bullet = null;
        }

        synchronized (Game.class){
            objects.clear();
            objects.addAll(alive);

            for (int i = 0; i < objects.size(); i++){
                for (int j = i + 1; j < objects.size(); j++){
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }
        }
    }


    public void incScore(){
        score ++;
        //TODO: Add lives at certain scores.
    }

    public int getScore(){
        return score;
    }
}
