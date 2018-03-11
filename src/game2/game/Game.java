package game2.game;

import game2.utilities.*;
import game2.utilities.controllers.AimNShoot;
import game2.utilities.controllers.Controller;
import game2.utilities.controllers.Keys;
import game2.utilities.controllers.RandomAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;



/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    //TODO: Make several levels. Array with N_INITIAL_ASTEROIDS.
    private static final int[] N_INITIAL_ASTEROIDS = new int[]{
            2,
            3,
            4,
            5,
            6
    };

    private static final int ASTEROID_INIT_SIZE = 2;
    private static final int SCORE_NEW_LIFE = 1500;

    private int currentLevel = 1;
    private int score = 0;
    private int scoreTracker = 0;
    private PlayerShip playerShip;
    private int lives  = 3;

    public List<GameObject> objects;
    public Keys keys = new Keys();

    public Game(){
        objects = new CopyOnWriteArrayList<>();
        spawnShip();
        objects.addAll(spawnAsteroids());
    }

    private void spawnShip(){
        if (lives > 0){
            AimNShoot as = new AimNShoot(this);
            playerShip = new PlayerShip(as);
            as.setParent(playerShip);


            objects.add(playerShip);
            SoundManager.extraShip();
        }else{
            System.exit(0);
        }

    }

    private List<Asteroid> spawnAsteroids(){

        List<Asteroid> newAsteroids = new ArrayList<>();
        if (currentLevel <= N_INITIAL_ASTEROIDS.length){
            for (int i=0; i<N_INITIAL_ASTEROIDS[currentLevel - 1];i++){
                newAsteroids.add(Asteroid.makeRandomAsteroid(ASTEROID_INIT_SIZE));
            }

            return newAsteroids;
        }else{
            System.out.println("All levels cleared. Exiting game");
            System.exit(0);
            return null;
        }
    }

    void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 15, 15);
        g.drawString("Lives Remaining: " + lives, 15, 35);
        g.drawString("Current Level: " + currentLevel, 15, 55);
    }

    public void update(){
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
        if (playerShip.dead){
            lives --;
            spawnShip();
        }

        objects.forEach(GameObject::update);
        List<GameObject> alive = objects.stream().filter(o -> !o.dead).collect(Collectors.toList());

        boolean containsAsteroid = false;

        for (GameObject go : alive){
            if (go instanceof Asteroid){
                containsAsteroid = true;
                break;
            }
        }

        if (!containsAsteroid){
            currentLevel ++;
            List<Asteroid> ast = spawnAsteroids();
            if (ast != null) alive.addAll(ast);
        }

        if (playerShip.bullet != null) {
            alive.add(playerShip.bullet);
            playerShip.bullet = null;
        }

        synchronized (Game.class){
            objects.clear();
            objects.addAll(alive);

            for (int i = 0; i < objects.size(); i++){
                for (int j = i + 1; j < objects.size(); j++){
                    int score = objects.get(i).collisionHandling(objects.get(j));
                    if (score > 0){
                        incScore(score);
                    }
                }
            }
        }
    }


    private void incScore(int x){
        score += x;
        scoreTracker += x;

        if (scoreTracker == SCORE_NEW_LIFE){
            lives ++;
            scoreTracker = 0;
        }
        //TODO: Add lives at certain scores.
    }

    public int getScore(){
        return score;
    }
}
