package Assignment.Utilities.Map;

import Assignment.GameObjects.PlayerShip;
import Assignment.Other.RandomNumberHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el16035 on 17/03/2018.
 */
public class Map {
    private static final int WEST_ROOM = 0;
    private static final int NORTH_ROOM = 1;
    private static final int EAST_ROOM = 2;
    private static final int SOUTH_ROOM = 3;


    Room[][] rooms;
    public int size;
    private int[] initPos;


    public Map(int size) {
        this.size = size;
        rooms = new Room[size][size];
        generateRooms();
    }

    public Room[][] getMap() {
        return rooms;
    }

    public int[] getInitPos() {
        return initPos;
    }

    public boolean doesRoomExist(int[] index){
        try{
            return rooms[index[0]][index[1]] != null;
        } catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public Room getRoomAtPosition(int[] position) {
        //The position has to be an array of 2 coordinates
        if (position.length != 2) return null;

        if (isValidPosition(position, size) && rooms[position[0]][position[1]] != null) {
            return rooms[position[0]][position[1]];
        }

        return null;
    }

    //TODO: Make a list of Lists of rooms. each list of rooms with x amount of exit doors
    public void generateRooms() {
        char[][] tempObstacles = MapFileParser.getObstacles();
        boolean[][] roomLocations = generateMap();

        for (int i = 0; i < roomLocations.length; i++) {
            for (int j = 0; j < roomLocations[i].length; j++) {
                if (roomLocations[i][j]) {
                    rooms[i][j] = new Room(tempObstacles);
                }
            }
        }
    }


    //Method for generating a map with size as width and height of the map
    private boolean[][] generateMap() {

        RandomNumberHelper randomNH = new RandomNumberHelper();


        boolean[][] roomMap = new boolean[size][size];
        int[] currentPos = {size / 2, size / 2};
        initPos = new int[]{currentPos[0], currentPos[1]};
        roomMap[currentPos[0]][currentPos[1]] = true;

        //Rooms
        List<int[]> leafRooms = new ArrayList<>();
        leafRooms.add(currentPos);

        int desiredRooms = size * 3 / 2;
        System.out.println("desRoom=" + desiredRooms);
        int roomsGenerated = 1;

        //random generator which will produce a number between 0 and 3 which indicated the next direction for the new room
        //0 = west, 1=north, 2=east, 3=south

        while (roomsGenerated < desiredRooms) {
            leafRooms = getEdgeRooms(roomMap);
            currentPos = leafRooms.get(randomNH.randomInt(leafRooms.size()));
            randomNH.setProbabilities(getDirectionProbability(roomMap, currentPos));
            int[] newPos = getNewRoomPos(currentPos, randomNH);
            if (isValidPosition(newPos, size) && !roomMap[newPos[0]][newPos[1]]) {
                roomMap[newPos[0]][newPos[1]] = true;
                roomsGenerated++;
            }
        }

        return roomMap;

    }

    //Method which will return rooms which has a neighboring room that only has 1 neighbour
    public List<int[]> getEdgeRooms(boolean[][] map) {
        List<int[]> edgeRooms = new ArrayList<>();

        int[] currentPos;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j]) {
                    currentPos = new int[]{i, j};

                    if (canMakeLeafRoom(map, currentPos)) {
                        edgeRooms.add(currentPos);
                    }
                }
            }
        }
        return edgeRooms;
    }

    //Return true if a position can make a room which have no neighbours except from the room that created it.
    private boolean canMakeLeafRoom(boolean[][] map, int[] currentPos) {
        int[] validDirections = getNeighboursValidDirections(map, currentPos);

        for (int num : validDirections) {
            if (num == 3) return true;
        }
        return false;
    }

    private int[] getNewRoomPos(int[] lastPos, RandomNumberHelper rnh) {
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

    private boolean isValidPosition(int[] newPos, int mapSize) {
        return (newPos[0] >= 0 && newPos[0] < mapSize) && (newPos[1] >= 0 && newPos[1] < mapSize);
    }

    //Method returning the probabilities of which room to go to next from a current position. depends on how many neighbours the neighbouring rooms have.
    private double[] getDirectionProbability(boolean[][] map, int[] currentPos) {
        double[] probabilities = new double[4];
        int[] validDirections = getNeighboursValidDirections(map, currentPos);

        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = getProbability(validDirections[i]);
        }
        return probabilities;
    }

    //Get the valid directions of the neighbour rooms of a room.
    private int[] getNeighboursValidDirections(boolean[][] map, int[] currentPos) {
        //Initializing the array with length 4, because 4 directions
        int[] validDirections = new int[4];

        validDirections[WEST_ROOM] = getValidDirections(map, new int[]{currentPos[0] - 1, currentPos[1]});
        validDirections[NORTH_ROOM] = getValidDirections(map, new int[]{currentPos[0], currentPos[1] - 1});
        validDirections[EAST_ROOM] = getValidDirections(map, new int[]{currentPos[0] + 1, currentPos[1]});
        validDirections[SOUTH_ROOM] = getValidDirections(map, new int[]{currentPos[0], currentPos[1] + 1});

        return validDirections;
    }


    //Method which returns amount of possible directions a room could get generated from a current position
    private int getValidDirections(boolean[][] map, int[] currentPos) {
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

        currentTest = new int[]{currentPos[0], currentPos[1] - 1};
        if (isValidPosition(currentTest, map.length)) {
            if (!map[currentTest[0]][currentTest[1]]) count++;
        }

        currentTest = new int[]{currentPos[0], currentPos[1] + 1};
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
    private double getProbability(int validDirections) {
        if (validDirections == 0) return 0;
        double powerOf = 1.5;
        double val = 4 - validDirections;

        double newValue = Math.pow(val, powerOf);

        return 100 / newValue;
    }
}
