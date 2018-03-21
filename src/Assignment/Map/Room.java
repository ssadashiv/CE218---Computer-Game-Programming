package Assignment.Map;

import Assignment.GameObjects.*;
import Assignment.GameObjects.Enemies.*;
import Assignment.MainGame.Game;
import Assignment.Utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Assignment.Map.MapConstants.REGULAR_ROOM;
import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/03/2018.
 */
public class Room {
    private boolean canOpenDoors;


    //Enemies and buttons that needs to be pushed for a
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

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    void setRoomMap(boolean[] neighbours, boolean initPos) {
        objects = RoomHelper.getRoomObjects(neighbours, roomType, initPos);
    }

    public void addLvlHole(LvlHole hole){
        otherObjects.add(hole);
    }

    public void setShip(PlayerShip ship) {
        this.ship = ship;
        initObjects(objects);
    }

    /*TODO:
    * Add level difference
    * The enemies get harder the more lvls you beat. let's say 10% higher stats every lvl.
    * */
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
                            chargeBots.add(new ChargeBot(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;

                        //BOSSES
                        //CoinMoneyMan
                        case 'M':
                            objectives.add(new CoinMoneyMan(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;
                        //Zapper
                        case 'Z':
                            Zapper zapper = new Zapper(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                            objectives.add(zapper);
                            break;

                        //Saucer
                        case 'S':
                            Saucer saucer = new Saucer(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
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


                        default:
                            System.out.println("ERROR: Unknown symbol'" + objects[i][j] + "'. Exiting program");
                            System.exit(0);
                    }
                }
            }
        }

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


    //TODO: Reset all enemies and buttons.
    public void reset() {
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
