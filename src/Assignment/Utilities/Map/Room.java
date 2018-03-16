package Assignment.Utilities.Map;

import Assignment.GameObjects.Door;
import Assignment.GameObjects.DoorButton;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/03/2018.
 */
public class Room {
    private boolean canOpenDoors;

    //Enemies and buttons that needs to be pushed for a
    private List<GameObject> objectives = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();


    public Room(char[][] objects){
        initObjects(objects);
        reset();
    }

    private void initObjects(char[][] objects){
        double obstSize = (double) FRAME_SIZE.height / (double) objects[0].length;
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[i].length; j++) {
                if (objects[i][j] != '-') {
                    switch (objects[i][j]) {
                        //Obstacle
                        case '#':
                            obstacles.add(new Obstacle(new Vector2D(j * obstSize, i * obstSize), Color.BLACK, (int) obstSize, (int) obstSize));
                            break;

                        //Button
                        case '+':
                            objectives.add(new DoorButton(this, new Vector2D((j * obstSize) + (obstSize / 2), (i * obstSize) + (obstSize / 2)), (int) obstSize / 5));
                            break;

                        //Door
                        case '@':
                            obstacles.add(new Door(this, new Vector2D(j * obstSize, i * obstSize), (int) obstSize, (int) obstSize / 2));
                            break;
                        default:
                            System.out.println("ERROR: Unknown symbol. Exiting program");
                            System.exit(0);
                    }
                }
            }
        }
    }

    public List<GameObject> getObjects(){
        List<GameObject> objects = new ArrayList<>();
        objects.addAll(objectives);
        objects.addAll(obstacles);

        return objects;
    }


    public void reset(){
        canOpenDoors = false;
    }

    public void addObjective(GameObject obj) {
        objectives.add(obj);
        canOpenDoors = false;
    }

    public void removeObjective(GameObject obj){
        objectives.remove(obj);

        //Check whether there are more unpressed buttons
        if (objectives.isEmpty()) {
            canOpenDoors = true;
            return;
        }
        canOpenDoors = false;
    }

    public boolean canOpenDoors() {
        return canOpenDoors;
    }

    public void closeDoors(){

    }
}
