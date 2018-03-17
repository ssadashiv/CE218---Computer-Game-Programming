package Assignment.Utilities.Map;

import Assignment.GameObjects.Door;
import Assignment.GameObjects.DoorButton;
import Assignment.GameObjects.GameObject;
import Assignment.GameObjects.Obstacle;
import Assignment.Other.RandomNumberHelper;
import Assignment.Utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/03/2018.
 */
public class Room {
    private static final int WEST_ROOM = 0;
    private static final int NORTH_ROOM = 1;
    private static final int EAST_ROOM = 2;
    private static final int SOUTH_ROOM = 3;


    private boolean canOpenDoors;

    //Enemies and buttons that needs to be pushed for a
    private List<GameObject> objectives = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();


    public Room(char[][] objects) {
        initObjects(objects);
        reset();
    }

    private void initObjects(char[][] objects) {
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

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();
        objects.addAll(objectives);
        objects.addAll(obstacles);

        return objects;
    }


    public void reset() {
        canOpenDoors = false;
    }

    public void addObjective(GameObject obj) {
        objectives.add(obj);
        canOpenDoors = false;
    }

    public void removeObjective(GameObject obj) {
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

    public void closeDoors() {

    }


    //Method for generating a map with mapSize as width and height of the map
    public static boolean[][] generateMap(int mapSize) {
        RandomNumberHelper randomNH = new RandomNumberHelper();


        boolean[][] roomMap = new boolean[mapSize][mapSize];
        int[] currentPos = {mapSize / 2, mapSize / 2};
        roomMap[currentPos[0]][currentPos[1]] = true;

        //Rooms
        List<int[]> leafRooms = new ArrayList<>();
        leafRooms.add(currentPos);

        int desiredRooms = mapSize * 3 / 2;
        System.out.println("desRoom=" + desiredRooms);
        int roomsGenerated = 1;

        //random generator which will produce a number between 0 and 3 which indicated the next direction for the new room
        //0 = west, 1=north, 2=east, 3=south

        while (roomsGenerated < desiredRooms) {
            leafRooms = getEdgeRooms(roomMap);
            currentPos = leafRooms.get(randomNH.randomInt(leafRooms.size()));
            randomNH.setProbabilities(getDirectionProbability(roomMap, currentPos));
            int[] newPos = getNewRoomPos(currentPos, randomNH);
            if (isValidPosition(newPos, mapSize) && !roomMap[newPos[0]][newPos[1]]) {
                roomMap[newPos[0]][newPos[1]] = true;
                currentPos = newPos;
                roomsGenerated++;

            }

        }

        return roomMap;

    }


    //Method which will return rooms which has a neighboring room that only has 1 neighbour
    public static List<int[]> getEdgeRooms(boolean[][] map){
        List<int[]> edgeRooms = new ArrayList<>();

        int[] currentPos;
        for (int i = 0;i<map.length;i++){
            for (int j = 0;j<map[i].length;j++){
                if (map[i][j]){
                    currentPos = new int[]{i, j};

                    if (canMakeLeafRoom(map, currentPos)){
                        edgeRooms.add(currentPos);
                    }
                }
            }
        }
        return edgeRooms;
    }

    //Return true if a position can make a room which have no neighbours except from the room that created it.
    private static boolean canMakeLeafRoom(boolean[][] map, int[] currentPos){
        int[] validDirections = getNeighboursValidDirections(map, currentPos);

        for (int num : validDirections){
            if (num == 3) return true;
        }
        return false;
    }

    private static int[] getNewRoomPos(int[] lastPos, RandomNumberHelper rnh) {
        int num = rnh.getRandomNumber();
        switch (num) {
            case 0:
                return new int[]{lastPos[0] - 1, lastPos[1]};
            case 1:
                return new int[]{lastPos[0], lastPos[1] - 1};
            case 2:
                return new int[]{lastPos[0] + 1, lastPos[1]};
            case 3:
                return new int[]{lastPos[0], lastPos[1] + 1};

        }
        return new int[]{0, 0};
    }

    private static boolean isValidPosition(int[] newPos, int mapSize) {
        return (newPos[0] >= 0 && newPos[0] < mapSize) && (newPos[1] >= 0 && newPos[1] < mapSize);
    }

    //Method returning the probabilities of which room to go to next from a current position. depends on how many neighbours the neighbouring rooms have.
    private static double[] getDirectionProbability(boolean[][] map, int[] currentPos) {
        double[] probabilities = new double[4];
        int[] validDirections = getNeighboursValidDirections(map, currentPos);

        for (int i = 0; i < probabilities.length; i++){
            probabilities[i] = getProbability(validDirections[i]);
        }
        return probabilities;
    }

    //Get the valid directions of the neighbour rooms of a room.
    private static int[] getNeighboursValidDirections(boolean[][] map, int[] currentPos){
        //Initializing the array with length 4, because 4 directions
        int[] validDirections = new int[4];

        validDirections[WEST_ROOM] = getValidDirections(map, new int[]{currentPos[0] - 1, currentPos[1]});
        validDirections[NORTH_ROOM] = getValidDirections(map, new int[]{currentPos[0], currentPos[1] - 1});
        validDirections[EAST_ROOM] = getValidDirections(map, new int[]{currentPos[0] + 1, currentPos[1]});
        validDirections[SOUTH_ROOM] = getValidDirections(map, new int[]{currentPos[0], currentPos[1] + 1});

        return validDirections;
    }


    //Method which returns amount of possible directions a room could get generated from a current position
    private static int getValidDirections(boolean[][] map, int[] currentPos) {
        int count = 0;


        //Testing
        int[] currentTest;


        currentTest = new int[]{currentPos[0] - 1, currentPos[1]};
        if (isValidPosition(currentTest, map.length)) {
            if (!map[currentTest[0]][currentTest[1]]) count++;
        }

        currentTest = new int[]{currentPos[0] + 1, currentPos[1]};
        if (isValidPosition(currentTest, map.length)) {
            if (!map[currentTest[0]][currentTest[1]]) count++;
        }

        currentTest = new int[]{currentPos[0], currentPos[1]-1};
        if (isValidPosition(currentTest, map.length)) {
            if (!map[currentTest[0]][currentTest[1]]) count++;
        }

        currentTest = new int[]{currentPos[0], currentPos[1]+1};
        if (isValidPosition(currentTest, map.length)) {
            if (!map[currentTest[0]][currentTest[1]]) count++;
        }






        //Working
        /*
        if (isValidPosition(new int[]{currentPos[0] - 1, currentPos[1]}, map.length)) {
            if (!map[currentPos[0] - 1][currentPos[1]]) count++;
        }

        if (isValidPosition(new int[]{currentPos[0] + 1, currentPos[1]}, map.length)) {
            if (!map[currentPos[0] + 1][currentPos[1]]) count++;
        }

        if (isValidPosition(new int[]{currentPos[0], currentPos[1] - 1}, map.length)) {
            if (!map[currentPos[0]][currentPos[1] - 1]) count++;
        }

        if (isValidPosition(new int[]{currentPos[0], currentPos[1] + 1}, map.length)) {
            if (!map[currentPos[0]][currentPos[1] + 1]) count++;
        }
*/
        return count;

    }

    //Returns the chances of a room spawning. The chances are timed by 100, so 5% = 50
    private static double getProbability(int validDirections){
        if (validDirections == 0) return 0;
        double powerOf = 1.5;
        double val = 4 - validDirections;

        double newValue = Math.pow(val, powerOf);

        return 100 / newValue;
    }
}
