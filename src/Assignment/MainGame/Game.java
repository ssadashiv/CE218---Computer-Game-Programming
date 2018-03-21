package Assignment.MainGame;


import Assignment.GameObjects.*;
import Assignment.GameObjects.Projectiles.Projectile;
import Assignment.Other.SharedValues;
import Assignment.Utilities.Controllers.PlayerControllers.KeyBindingController;
import Assignment.Map.Room;
import Assignment.Utilities.Gravity.ForceFieldGravity;
import Assignment.Utilities.HitDetection;
import Assignment.Map.*;
import Assignment.Utilities.SoundManager;
import Assignment.Utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.FRAME_WIDTH;
import static Assignment.Other.Constants.SWITCH_ROOM_ANIMATION_DURATION;

/**
 * Created by el16035 on 16/01/2018.
 */
public class Game {


    public List<GameObject> objects;
    public KeyBindingController playerKeys;
    public PlayerShip playerShip;

    private boolean gameRunning = false;
    private int currentLevel = 1;
    private MapHelper mapHelper;
    private Room currentRoom;

    private boolean newGame = true;

    private ForceFieldGravity forceFieldGravity = new ForceFieldGravity();

    private MainFrame container;

    public Game(MainFrame container) {
        this.container = container;
        objects = new CopyOnWriteArrayList<>();
        //playerKeys = new PlayerKeys(container);
    }

    void newGame() {
        for (GameObject o : objects) o.dead = true;

        newGame = true;
        mapHelper.setLevelAndMap(currentLevel);
        playerShip.setMapHelper(mapHelper);

        gameRunning = true;
        objects.clear();
        currentLevel = 1;
        spawnShip(true);
        updateMap();
        System.out.println("NEW GAME");

        switchRoom();
        newGame = false;


    }

    public void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
    }


    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setPlayerKeys(KeyBindingController kbc) {
        playerKeys = kbc;
        playerShip = new PlayerShip(playerKeys);
    }

    private void bossKilled(){
        new LvlHole(new Vector2D(2,2), this);
        currentRoom.addLvlHole(new LvlHole(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/3), this));

    }

    public void nextLevelConfirmation(){
        if (container.nextLevel()){
            SharedValues.gamePaused = true;
            currentLevel++;
            SharedValues.lvlDifficulty *= SharedValues.diffPerLevel;
            mapHelper.setLevelAndMap(currentLevel);
        }

    }

    private void spawnShip(boolean newGame) {
        if (!newGame) {
            if (playerShip.getStats().getLivesRemaining() == 0) {
                //noMoreLives();
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

    public int[] shipMapPos() {
        return playerShip.getMapPos();
    }

    public void update() {
        if (playerShip.dead) {
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
        mapHelper.updateMap();
        this.updateMap();
        this.closeDoorsInNewRoom();
        List<Hole> holes = new ArrayList<>();
        for (GameObject o : objects) {
            if (o instanceof Hole) holes.add((Hole) o);
            o.setField(forceFieldGravity);
        }

        forceFieldGravity.setHoles(holes);
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
