package assignment2.maingame;


import assignment2.gameobjects.*;
import assignment2.gameobjects.wormholes.LvlHole;
import assignment2.gameobjects.wormholes.Wormhole;
import assignment2.gameobjects.obstacles.Door;
import assignment2.gameobjects.projectiles.Projectile;
import assignment2.other.SharedValues;
import assignment2.utilities.controllers.playercontrollers.KeyBindingController;
import assignment2.map.Room;
import assignment2.utilities.gravity.ForceFieldGravity;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.map.*;
import assignment2.utilities.SoundManager;
import assignment2.utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static assignment2.other.Constants.FRAME_HEIGHT;
import static assignment2.other.Constants.FRAME_WIDTH;
import static assignment2.other.Constants.SWITCH_ROOM_ANIMATION_DURATION;

/**
 * Created by el16035 on 16/01/2018.
 */

//The main Class for the game
public class Game {


    public List<GameObject> objects;
    public KeyBindingController playerKeys;
    public PlayerShip playerShip;

    private boolean gameRunning = false;
    private int currentLevel = 1;
    private MapHelper mapHelper;
    private Room currentRoom;

    private boolean newGame = true;

    //The forcefield of a current room.
    private ForceFieldGravity forceFieldGravity = new ForceFieldGravity();

    private MainFrame mainFrame;

    public Game(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        objects = new CopyOnWriteArrayList<>();
        //playerKeys = new PlayerKeys(mainFrame);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    void newGame() {
        for (GameObject o : objects) o.dead = true;


        newGame = true;
        mapHelper.setLevelAndMap(currentLevel);
        playerShip.setMapHelper(mapHelper);
        spawnShip(true);


        gameRunning = true;
        objects.clear();
        currentLevel = 1;

        updateMap();
        System.out.println("NEW GAME");

        switchRoom();
        newGame = false;


    }

    void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
    }

    boolean isGameRunning() {
        return gameRunning;
    }

    void setPlayerKeys(KeyBindingController kbc) {
        playerKeys = kbc;
        playerShip = new PlayerShip(playerKeys);
    }

    //Adds a LvlHole to the room if the boss is killed.
    private void bossKilled(){
        LvlHole h = new LvlHole(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/3), this);
        currentRoom.addLvlHole(h);
        objects.add(h);
        playerShip.bossKilled = false;
    }

    //The LvlHole opens up a confimationbox. yes=proceed to the next level.
    public void nextLevelConfirmation(){
        if (mainFrame.nextLevel()){
            SharedValues.gamePaused = true;
            currentLevel++;
            SharedValues.lvlDifficulty *= SharedValues.diffPerLevel;
            mapHelper.setLevelAndMap(currentLevel);
            playerShip.resetPosition(true);
            SharedValues.gamePaused = false;
        }else{
            playerShip.position.set(FRAME_WIDTH/2, FRAME_HEIGHT/2);
            playerShip.velocity.set(0,0);
        }

    }


    private void spawnShip(boolean newGame) {
        if (!newGame) {
            if (playerShip.getStats().getLivesRemaining() == 0) {
                SharedValues.gamePaused = true;
                gameRunning = false;
                mainFrame.gameEnded(playerShip);
                return;
            } else {
                playerShip.newLife();
                switchRoom();
            }
        } else {
            playerShip.newGame();
        }

        SoundManager.extraShip();
        objects.add(playerShip);
    }

    private void updateMap() {
        objects.clear();
        currentRoom = mapHelper.getMap().getRoomAtPosition(playerShip.getMapPos());
        currentRoom.setShip(playerShip);
        objects.addAll(currentRoom.getObjects());
        objects.add(playerShip);
        mapHelper.roomChanged = false;
    }

    int[] shipMapPos() {
        return playerShip.getMapPos();
    }

    //update method which loops through all the objects and updates them.
    //It also deals with hit detection, and adding of eventual projectiles to the list of GameObjects.
    public void update() {
        if (playerShip.dead) {
            playerShip.removeAllZappers();
            playerShip.getStats().addLivesRemaining(-1);
            spawnShip(false);
        }

        if (playerShip.bossKilled){
            bossKilled();
        }

        if (mapHelper.roomChanged) {
            this.switchRoom();
        }

        currentRoom.updateObjectives();

        objects.forEach(GameObject::update);
        //List<GameObject> alive = objects.stream().filter(o -> !o.dead).collect(Collectors.toList());

        List<GameObject> alive = new ArrayList<>();
        for (GameObject o: objects){
            if (o.dead && o.metal != null) {
                alive.add(o.metal);
            }
            if (!o.dead) alive.add(o);
        }

        List<Projectile> projectiles = new ArrayList<>();

        alive.stream().filter(o -> o instanceof Turret).forEach(o -> {
            projectiles.addAll(((Turret) o).getProjectiles());
            ((Turret) o).clearProjectiles();
        });

        alive.addAll(projectiles);

        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);

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

    private void switchRoom() {
        mapHelper.updateMap(false);
        this.updateMap();
        this.closeDoorsInNewRoom();
        List<Wormhole> wormholes = new ArrayList<>();
        for (GameObject o : objects) {
            if (o instanceof Wormhole) wormholes.add((Wormhole) o);
            o.setField(forceFieldGravity);
        }
        forceFieldGravity.setWormholes(wormholes);
    }

    private void closeDoorsInNewRoom() {
        List<Door> doors = objects.stream().filter(o -> o instanceof Door).map(o -> (Door) o).collect(Collectors.toList());

        if (newGame) {
            doors.forEach(Door::closeDoor);
            return;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                doors.forEach(Door::closeDoor);
            }
        }, SWITCH_ROOM_ANIMATION_DURATION);

    }
}
