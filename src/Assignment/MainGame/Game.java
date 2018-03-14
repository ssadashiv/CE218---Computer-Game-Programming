package Assignment.MainGame;


import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Controllers.PlayerKeys;
import Assignment.Utilities.SoundManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    private int currentLevel;
    private int score;

    public List<GameObject> objects;
    public PlayerKeys playerKeys;
    private PlayerShip playerShip;
    MainFrame container;

    public Game(MainFrame container) {
        this.container = container;
        objects = new LinkedList<>();
        playerKeys = new PlayerKeys(container);
        playerShip = new PlayerShip(playerKeys);
        newGame();
    }

    void newGame() {
        objects.clear();
        score = 0;
        currentLevel = 1;
        spawnShip();
        //spawnSaucer();
        //objects.addAll(spawnAsteroids());*/
    }

    private void spawnShip() {
        playerShip.resetPos();
        SoundManager.extraShip();
        objects.add(playerShip);
    }


    void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 15, 15);
        g.drawString("Lives Remaining: " + playerShip.getStats().getLivesRemaining(), 15, 35);
        g.drawString("Current Level: " + currentLevel, 15, 55);
    }

    public void setShipMapHelper(MapHelper mh) {
        playerShip.setMapHelper(mh);
    }

    public int[] shipMapPos() {
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


    public void update() {
        if (playerShip.dead) {
            spawnShip();
        }


        objects.forEach(GameObject::update);
        List<GameObject> alive = objects.stream().filter(o -> !o.dead).collect(Collectors.toList());

        if (playerShip.bullet != null) {
            alive.add(playerShip.bullet);
            playerShip.bullet = null;
        }

        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }
    }
}
