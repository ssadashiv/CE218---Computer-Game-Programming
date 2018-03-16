package Assignment.MainGame;


import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Controllers.KeyBindingController;
import Assignment.Utilities.Controllers.PlayerKeys;
import Assignment.Utilities.Map.*;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {

    private int currentLevel;
    private int score;

    public List<GameObject> objects;
    public KeyBindingController playerKeys;
    public  PlayerShip playerShip;

    public Game() {
        objects = new CopyOnWriteArrayList<>();
        //playerKeys = new PlayerKeys(container);


    }

    void newGame() {
        objects.clear();
        score = 0;
        currentLevel = 1;
        spawnShip();
        addObstacles();
    }

    public void setPlayerKeys(KeyBindingController kbc){
        playerKeys = kbc;
        playerShip = new PlayerShip(playerKeys);
        newGame();
    }

    private void spawnShip() {
        playerShip.resetPos();
        SoundManager.extraShip();
        objects.add(playerShip);
    }

    private void addObstacles(){
        boolean[][] obstacles = MapFileParser.getObstacles();
        for (GameObject o : objects) o.setGridSize();


        double obstSize = (double) FRAME_SIZE.height /  (double) obstacles[0].length;


        for (int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < obstacles[i].length; j++) {
                if (obstacles[i][j]) {
                    System.out.println("added an obstacle");
                    System.out.println("pos.x=" + j*obstSize);
                    System.out.println("pos.y=" + i*obstSize);
                    objects.add(new Obstacle(new Vector2D(j * obstSize, i * obstSize), obstSize));
                }
            }
        }

    }

    void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 15, 15);
        g.drawString("Lives Remaining: " + playerShip.getStats().getLivesRemaining(), 15, 35);
        g.drawString("Current Level: " + currentLevel, 15, 55);
    }

    public void setMapHelper(MapHelper mh) {
        playerShip.setMapHelper(mh);
    }

    public int[] shipMapPos() {
        return playerShip.getMapPos();
    }

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

            //TODO: Make a better collisionhandler
            for (int i = 0; i < objects.size(); i++){
                for (int j = i + 1; j < objects.size(); j++){
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }
        }
    }
}
