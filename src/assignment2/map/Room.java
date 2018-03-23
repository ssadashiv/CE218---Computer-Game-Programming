package assignment2.map;

import assignment2.gameobjects.*;
import assignment2.gameobjects.enemies.*;
import assignment2.gameobjects.wormholes.BlackHole;
import assignment2.gameobjects.wormholes.LvlHole;
import assignment2.gameobjects.wormholes.WhiteHole;
import assignment2.gameobjects.obstacles.Door;
import assignment2.gameobjects.obstacles.DoorButton;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.obstacles.Wall;
import assignment2.maingame.Game;
import assignment2.utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static assignment2.map.MapConstants.REGULAR_ROOM;
import static assignment2.other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/03/2018.
 */

//The class for Rooms in a map.
    //Every map contains a 2d array of chars which indicate what GameObject it is.
public class Room {
    private boolean canOpenDoors;


    //enemies and buttons that needs to be pushed for a
    private List<GameObject> objectives = new ArrayList<>();
    private List<GameObject> otherObjects = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();
    private PlayerShip ship;
    private char[][] objects;
    private boolean roomCleared = false;
    private String roomType = REGULAR_ROOM;


    public Room() {
        //setRoomColor();
        reset();
    }

    void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    String getRoomType() {
        return roomType;
    }

    void setRoomMap(boolean[] neighbours, boolean initPos) {
        objects = RoomHelper.getRoomObjects(neighbours, roomType, initPos);
        initObjects(objects);
    }

    //Add the lvl hole to the next lvl
    public void addLvlHole(LvlHole hole){
        otherObjects.add(hole);
    }

    public void setShip(PlayerShip ship) {
        this.ship = ship;
        objectives.stream().filter(o -> o instanceof Enemy).forEach(o -> ((Enemy) o).setPlayerShip(ship));
        otherObjects.stream().filter(o -> o instanceof ShopMan).forEach(o -> ((ShopMan) o).setPlayerShip(ship));
    }

    //Initializing the objects in the room
    private void initObjects(char[][] objects) {
        clearObjects();
        double obstSize = (double) FRAME_SIZE.height / (double) objects[0].length;
        boolean whiteHoleAdded = false;

        List<BlackHole> tempBHoles = new ArrayList<>();
        List<ChargeBot> chargeBots = new ArrayList<>();
        ChargeStation chargeStation = null;
        //ChargeStation chargeStation

        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[i].length; j++) {
                if (objects[i][j] != '-') {
                    switch (objects[i][j]) {

                        //OBSTACLES. WALLS OR DOORS
                        //Obstacle
                        case '#':
                            obstacles.add(new Wall(new Vector2D(j * obstSize + (obstSize / 2), i * obstSize + (obstSize / 2)), (int) obstSize, (int) obstSize));
                            break;

                        //Door
                        case '@':
                            obstacles.add(new Door(this, new Vector2D(j * obstSize + (obstSize / 2), i * obstSize + (obstSize / 2)), (int) obstSize / 2, (int) obstSize));
                            break;


                        //OBJECTIVES. EITHER BUTTONS OR ENEMIES
                        //Button
                        case '+':
                            objectives.add(new DoorButton(this, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)), (int) obstSize / 2));
                            break;

                        //ChargeBot. Add a ChargeStation as well
                        case 'C':
                            if (chargeStation == null) {
                                chargeStation = new ChargeStation(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                                obstacles.add(chargeStation);
                            }
                            chargeBots.add(new ChargeBot(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;

                        //BOSSES
                        //CoinMoneyMan
                        case 'M':
                            objectives.add(new CoinMoneyMan(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;
                        //Zapper
                        case 'Z':
                            Zapper zapper = new Zapper(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                            objectives.add(zapper);
                            break;

                        //Saucer
                        case 'S':
                            Saucer saucer = new Saucer(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                            objectives.add(saucer);
                            break;


                        //Black hole
                        case 'B':
                            tempBHoles.add(new BlackHole(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;

                        //White hole
                        case 'W':
                            otherObjects.add(new WhiteHole(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            whiteHoleAdded = true;
                            break;

                        //SHOP
                        case 'T':
                            otherObjects.add(new ShopMan(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;

                        default:
                            System.out.println("ERROR: Unknown symbol'" + objects[i][j] + "'. Exiting program");
                            System.exit(0);
                    }
                }
            }
        }

        //if the white hole is added, add it to all the black holes.
        if (whiteHoleAdded) {
            for (BlackHole b : tempBHoles) {
                for (GameObject o : otherObjects) {
                    if (o instanceof WhiteHole) {
                        b.setWhiteHole((WhiteHole) o);
                        break;
                    }
                }
            }
        }

        //add the chargestation to all the chargebots
        if (chargeStation != null) {
            for (ChargeBot cb : chargeBots) {
                cb.setChargeStation(chargeStation);
            }
        }


        otherObjects.addAll(tempBHoles);
        objectives.addAll(chargeBots);


    }

    private void clearObjects(){
        objectives.clear();
        obstacles.clear();
        otherObjects.clear();
    }

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();
        if (!roomCleared) objects.addAll(objectives);
        objects.addAll(otherObjects);
        objects.addAll(obstacles);

        return objects;
    }

    private void reset() {
        canOpenDoors = false;
    }

    public void addObjective(GameObject obj) {
        objectives.add(obj);
        canOpenDoors = false;
    }

    public void updateObjectives() {
        List<GameObject> alive = objectives.stream().filter(o -> !o.dead).collect(Collectors.toList());
        synchronized (Game.class) {
            objectives.clear();
            objectives.addAll(alive);

            if (objectives.isEmpty()) {
                roomCleared();
            }
        }
    }

    private void roomCleared() {
        roomCleared = true;
        canOpenDoors = true;
    }

    public void removeObjective(GameObject obj) {
        objectives.remove(obj);

        //Check whether there are more objectives in the room
        if (objectives.isEmpty()) {
            roomCleared();
        }
        //canOpenDoors = false;
    }

    public boolean canOpenDoors() {
        return canOpenDoors;
    }

}
