package Assignment.MainGame;


import Assignment.GameObjects.*;
import Assignment.Utilities.Controllers.PlayerControllers.KeyBindingController;
import Assignment.Utilities.Map.Room;
import Assignment.Utilities.HitDetection;
import Assignment.Utilities.Map.*;
import Assignment.Utilities.SoundManager;

import java.awt.*;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    public Game() {
        objects = new CopyOnWriteArrayList<>();
        //playerKeys = new PlayerKeys(container);
    }

    void newGame() {
        mapHelper.setLevelAndMap(currentLevel);
        playerShip.setMapHelper(mapHelper);
        
        gameRunning = true;
        objects.clear();
        currentLevel = 1;
        spawnShip();
        updateMap();
        System.out.println("NEW GAME");


    }

    public void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
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

    private void updateMap() {
        System.out.println("GENERATING MAP");
        //objects.forEach(GameObject::updateGrid);
        objects.clear();

        Room currentRoom = mapHelper.getMap().getRoomAtPosition(playerShip.getMapPos());
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
            spawnShip();
        }

        if (mapHelper.roomChanged){
            this.switchRoom();
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

    private void switchRoom(){
        System.out.println("ROOM CHANGED");
        mapHelper.updateMap();
        this.updateMap();
        this.closeDoorsInNewRoom();
    }

    private void closeDoorsInNewRoom(){
        List<Door> doors = objects.stream().filter(o -> o instanceof Door).map(o -> (Door) o).collect(Collectors.toList());
        doors.forEach(Door::closeDoor);
    }
}
