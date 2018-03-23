package assignment2.map;


/**
 * Created by el16035 on 13/03/2018.
 */

public class MapHelper {
    private static final int[] ROOM_SIZE_AT_LEVEL = new int[]{7, 9, 11};
    //If room has been visited by the player
    public static final int ROOM_VISITED = 2;

    //If the room is a neighbour to a room that is visited.
    public static final int ROOM_NEIGHBOR_TO_VISIT = 1;


    //Unexplored room
    public static final int ROOM_NOT_VISIBLE = 0;

    //private static Color[][] map = new Color[MAP_SIZE][MAP_SIZE];


    //int 2d array which stores which rooms have00 been visited before.
    //2 = visited, 1 = neighbor to a visited room, 0= not visited or is neighbour to a visited room
    private int[][] exploredRooms;
    private int[] mapPos;
    private int currentLevel;

    private Map map;
    public boolean roomChanged = true;

    public void setLevelAndMap(int currentLevel) {
        this.currentLevel = currentLevel;
        newMap();
    }

    public Map getMap() {
        return map;
    }

    public boolean doesRoomExist(int[] position) {
        return map.roomExists(position);
    }

    //Generate a new map.
    private void newMap() {
        int index = Math.min(ROOM_SIZE_AT_LEVEL.length-1, currentLevel);
        map = new Map(ROOM_SIZE_AT_LEVEL[index]);
        mapPos = map.getInitPos();
        exploredRooms = new int[map.size][map.size];
        updateMap(true);
    }

    public void updateMap(boolean newLevel) {
        roomChanged = !newLevel;
        setNeighbourRooms();
        setVisitedRooms();
    }

    //Get the start position of the current map
    public int[] getInitPos(){
        return map.getInitPos();
    }

    //Set the 2d array exploredRooms to ROOM_NEIGHBOR_TO_VISIT. This allows the player to see the silhouette of the room on the minimap.
    private void setNeighbourRooms() {
        int[][] newVisibleRooms = new int[4][2];
        newVisibleRooms[0] = new int[]{mapPos[0] - 1, mapPos[1]};
        newVisibleRooms[1] = new int[]{mapPos[0] + 1, mapPos[1]};
        newVisibleRooms[2] = new int[]{mapPos[0], mapPos[1] - 1};
        newVisibleRooms[3] = new int[]{mapPos[0], mapPos[1] + 1};

        for (int[] newRoom : newVisibleRooms) {
            if (roomExists(newRoom) && exploredRooms[newRoom[0]][newRoom[1]] == 0) {
                exploredRooms[newRoom[0]][newRoom[1]] = ROOM_NEIGHBOR_TO_VISIT;
            }
        }
    }

    Room getRoomAtPos(int[] index) {

        return map.getRoomAtPosition(index);
    }

    //Whenever a player visits a new map position, set the position to be visited in the exploredRooms array.
    private void setVisitedRooms() {
        exploredRooms[mapPos[0]][mapPos[1]] = ROOM_VISITED;
    }

    //boolean array displaying which position has a room and which does not
    boolean[][] getWhichRoomsExist() {
        boolean[][] whichMapExist = new boolean[map.size][map.size];

        for (int i = 0; i < map.size; i++) {
            for (int j = 0; j < map.size; j++) {
                whichMapExist[i][j] = roomExists(new int[]{i, j});
            }
        }
        return whichMapExist;
    }

    public void setMapPos(int[] newPos) {
        mapPos = newPos;
        updateMap(false);
    }

    public int[] getMapPos() {
        return new int[]{mapPos[0], mapPos[1]};
    }

    int[][] getExploredRooms() {
        return exploredRooms;
    }

    private boolean roomExists(int[] index) {
        return map.roomExists(index);
    }


}
