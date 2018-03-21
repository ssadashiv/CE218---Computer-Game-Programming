package Assignment.Map;


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

    /*  public MapHelper(){
      }
      */
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

    public void newMap() {
        System.out.println("reset called in maphelper");
        int index = Math.min(ROOM_SIZE_AT_LEVEL.length-1, currentLevel);
        map = new Map(ROOM_SIZE_AT_LEVEL[index]);
        mapPos = map.getInitPos();
        exploredRooms = new int[map.size][map.size];
        updateMap();
    }

    public void updateMap() {
        roomChanged = true;
        setNeighbourRooms();
        setVisitedRooms();
    }

    public int[] getInitPos(){
        return map.getInitPos();
    }

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

    public Room getRoomAtPos(int[] index) {

        return map.getRoomAtPosition(index);
    }


    private void setVisitedRooms() {
        exploredRooms[mapPos[0]][mapPos[1]] = ROOM_VISITED;
    }
/*
    public Color getRoomColor(int[] index){
        if (roomExists(index)) return map.getRoomAtPosition(index).getRoomColor();

        System.out.println("return null");
        return null;
    }*/

   /* public Color[][] getMiniMap(){
        Color[][] colorMap = new Color[map.size][map.size];
        for (int i =0;i<colorMap.length;i++){
            for (int j=0;j<colorMap[i].length;j++){
                int[] index = new int[]{i,j};
                if (map.roomExists(index)){
                    colorMap[i][j] = ROOM_VISITED_COLOR;
                    //map.getRoomAtPosition(new int[]{i,j}).getRoomColor();
                }
            }
        }
        return colorMap;
    }*/

    public boolean[][] getWhichRoomsExist() {
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
        updateMap();
    }

    public int[] getMapPos() {
        return new int[]{mapPos[0], mapPos[1]};
    }

    public int[][] getExploredRooms() {
        return exploredRooms;
    }

    private boolean roomExists(int[] index) {
        return map.roomExists(index);
    }


}
