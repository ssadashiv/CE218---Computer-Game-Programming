package Assignment.Map;

import Assignment.GameObjects.*;
import Assignment.GameObjects.Enemies.ChargeBot;
import Assignment.GameObjects.Enemies.ChargeStation;
import Assignment.MainGame.Game;
import Assignment.Utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public Room() {
        //setRoomColor();
        reset();
    }

    void setRoomMap(boolean[] neighbours) {
        objects = RoomHelper.readFile(neighbours);
    }

    public void setShip(PlayerShip ship) {
        this.ship = ship;
        initObjects(objects);
    }

    private void initObjects(char[][] objects) {
        double obstSize = (double) FRAME_SIZE.height / (double) objects[0].length;
        boolean whiteHoleAdded = false;

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
                            if (!roomCleared)
                                objectives.add(new DoorButton(this, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)), (int) obstSize / 5));
                            break;

                        //ChargeBot. Add a ChargeStation as well
                        case 'C':
                            ChargeStation chargeStation = new ChargeStation(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                            if (!roomCleared)
                                objectives.add(new ChargeBot(ship, chargeStation, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            obstacles.add(chargeStation);
                            break;


                        //Black hole
                        case 'B':
                            otherObjects.add(new BlackHole(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                            break;

                        //White hole
                        case 'W':
                            otherObjects.add(new WhiteHole(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
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
            for (GameObject o : otherObjects) {
                if (o instanceof BlackHole) {
                    for (GameObject object : otherObjects) {
                        if (object instanceof WhiteHole) {
                            ((BlackHole) o).setWhiteHole((WhiteHole) object);
                        }
                    }
                }
            }
        }
    }

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();
        objects.addAll(objectives);
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

    public void closeDoors() {

    }


}
