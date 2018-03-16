package Assignment.MainGame;


import Assignment.GameObjects.*;
import Assignment.Utilities.Controllers.KeyBindingController;
import Assignment.Utilities.Map.Room;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.Map.*;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import java.awt.*;
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
    public PlayerShip playerShip;

    private boolean gameRunning = false;

    public Game() {
        objects = new CopyOnWriteArrayList<>();
        //playerKeys = new PlayerKeys(container);


    }

    void newGame() {
        gameRunning = true;
        objects.clear();
        score = 0;
        currentLevel = 1;
        spawnShip();
        addObstacles();
    }

    public boolean isGameRunning(){
        return gameRunning;
    }

    public void setPlayerKeys(KeyBindingController kbc) {
        playerKeys = kbc;
        playerShip = new PlayerShip(playerKeys);
    }

    private void spawnShip() {
        playerShip.resetPosition();
        SoundManager.extraShip();
        objects.add(playerShip);
    }

    private void addObstacles() {
        char[][] obstacles = MapFileParser.getObstacles();
        //objects.forEach(GameObject::updateGrid);


        Room currentRoom = new Room(obstacles);
        objects.addAll(currentRoom.getObjects());
        /*double obstSize = (double) FRAME_SIZE.height / (double) obstacles[0].length;


        for (int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < obstacles[i].length; j++) {
                if (obstacles[i][j] != '-') {
                    //Initalize it as a placeholder.
                    GameObject newObj = new Obstacle(new Vector2D(0,0), Color.BLACK, 0,0);
                    switch (obstacles[i][j]) {
                        //Obstacle
                        case '#':
                            newObj = new Obstacle(new Vector2D(j * obstSize, i * obstSize), Color.BLACK, (int) obstSize, (int) obstSize);
                            break;

                        //Button
                        case '+':
                            newObj =  new DoorButton(room, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)), (int) obstSize / 5);
                            room.addObjective(newObj);
                            break;

                        //Door
                        case '@':
                            newObj =  new Door(room, new Vector2D(j * obstSize, i * obstSize), (int) obstSize, (int) obstSize / 2);
                            break;
                        default:
                            System.out.println("ERROR: Unknown symbol. Exiting program");
                            System.exit(0);
                    }

                    objects.add(newObj);
                }
            }
        }*/
    }

    void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 15, 15);
        g.drawString("Lives Remaining: " + playerShip.getStats().getLivesRemaining (), 15, 35);
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
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    GameObject obj1 = objects.get(i);
                    GameObject obj2 = objects.get(j);
                    if (!HitDetection.sameClass(obj1, obj2)) {
                        //if (HitDetection.isSameGrid(obj1, obj2)){
                            obj1.collisionHandling(obj2);
                       // }
                    }
                }
            }
        }
    }

    public void closeDoors(){

    }
}
