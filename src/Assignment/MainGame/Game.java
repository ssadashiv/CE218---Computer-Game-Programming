package Assignment.MainGame;


import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Controllers.NewKeys;
import Assignment.Utilities.SoundManager;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    private int currentLevel;
    private int score;

    private int lives  = 3;
    public List<GameObject> objects;
    public NewKeys playerKeys;
    private PlayerShip playerShip;
    MainFrame container;

    public Game(MainFrame container){
        this.container = container;
        objects = new CopyOnWriteArrayList<>();
        playerKeys = new NewKeys(container);
        playerShip = new PlayerShip(playerKeys);
    }

    void newGame(){
        objects.clear();
        score = 0;
        currentLevel = 1;
        spawnShip();
        //spawnSaucer();
        //objects.addAll(spawnAsteroids());*/
    }
    private void spawnShip(){
        if (lives > 0){
            if (playerShip == null) playerShip = new PlayerShip(playerKeys);
            objects.add(playerShip);
            SoundManager.extraShip();
        }else{
            System.exit(0);
        }

    }



    void draw(Graphics2D g){
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 15, 15);
        g.drawString("Lives Remaining: " + lives, 15, 35);
        g.drawString("Current Level: " + currentLevel, 15, 55);
    }


    public void update(){
        objects.forEach(GameObject::update);
    }

    public void setShipMapHelper(MapHelper mh){
        playerShip.setMapHelper(mh);
    }

    public int[] shipMapPos(){
        return playerShip.getMapPos();
    }

    /*
    private void spawnSaucer(){
        saucer = new Saucer(as);
        as.setParent(saucer);
        objects.add(saucer);
        SoundManager.saucerSmall();
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



    */


    /*
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

        if (saucer.dead) {
            spawnSaucer();
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
    }*/
}
