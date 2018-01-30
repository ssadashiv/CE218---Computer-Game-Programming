package lab1.game1;

import lab1.utilities.BasicController;
import lab1.utilities.BasicKeys;
import lab1.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static lab1.game1.Constants.DELAY;

/**
 * Created by el16035 on 16/01/2018.
 */
public class BasicGame {

    public static final int N_INITIAL_ASTEROIDS = 5;
    public BasicController ctrl;
    public List<BasicAsteroid> asteroids;
    public BasicShip ship;

    public BasicGame(){
        asteroids = new ArrayList<>();
        for (int i=0; i<N_INITIAL_ASTEROIDS;i++){
            asteroids.add(BasicAsteroid.makeRandomAsteroid());
        }

        ship = new BasicShip(ctrl);

    }

    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(new BasicKeys());

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


    public void update(){
        asteroids.forEach(BasicAsteroid::update);
        ship.update();
    }
}
