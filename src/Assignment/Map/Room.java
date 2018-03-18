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
    private List<Obstacle> obstacles = new ArrayList<>();
    private PlayerShip ship;
    private char[][] objects;
    private boolean roomCleared = false;

    //boolean array identifying which sides this room have neighbours.
    //index 0=west, 1=north, 2=east, 3= south;

    public Room() {
        //setRoomColor();
        reset();
    }

    /*public Room(char[][] objects) {
        this.objects = objects;
        setRoomColor();
        reset();
    }
*/
    void setRoomMap(boolean[] neighbours) {
        objects = RoomHelper.readFile(neighbours);
    }

    public void setShip(PlayerShip ship){
        this.ship = ship;
        initObjects(objects);
    }
/*
    private void setRoomColor() {
        Random ran = new Random();
        float r = ran.nextFloat();
        float g = ran.nextFloat();
        float b = ran.nextFloat();
        roomColor = new Color(r, g, b);

    }

    public Color getRoomColor() {
        return roomColor;
    }*/

    private void initObjects(char[][] objects) {
        if (!roomCleared){
            double obstSize = (double) FRAME_SIZE.height / (double) objects[0].length;
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
                                obstacles.add(new Door(this, new Vector2D(j * obstSize+ (obstSize / 2), i * obstSize + (obstSize / 2)), (int) obstSize / 2, (int) obstSize));
                                break;


                            //OBJECTIVES. EITHER BUTTONS OR ENEMIES
                            //Button
                            case '+':
                                objectives.add(new DoorButton(this, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)), (int) obstSize / 5));
                                break;

                            //ChargeBot. Add a ChargeStation as well
                            case 'C':
                                ChargeStation chargeStation = new ChargeStation(new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)));
                                objectives.add(new ChargeBot(ship, chargeStation, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                                obstacles.add(chargeStation);
                                break;


                            //Black hole
                            case 'B':
                                objectives.add(new BlackHole(ship, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2))));
                                break;

                            default:
                                System.out.println("ERROR: Unknown symbol'"+objects[i][j]+"'. Exiting program");
                                System.exit(0);
                        }
                    }
                }
            }
        }
    }

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();
        objects.addAll(objectives);
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

    public void updateObjectives(){
        List<GameObject> alive = objectives.stream().filter(o -> !o.dead).collect(Collectors.toList());
        synchronized (Game.class){
            objectives.clear();
            objectives.addAll(alive);

            if (objectives.isEmpty()) {
                roomCleared();
            }
        }
    }

    private void roomCleared(){
        roomCleared = true;
        canOpenDoors = true;
    }

    public void removeObjective(GameObject obj) {
        System.out.println("removing object");

        System.out.println("printing out members of objectives BEFORE ");
        for (GameObject o : objectives) System.out.println("MEMBER" + o.getClass().getName());

        objectives.remove(obj);

        System.out.println("printing out members of objectives");
        for (GameObject o : objectives) System.out.println("MEMBER" + o.getClass().getName());
        //Check whether there are more unpressed buttons
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
