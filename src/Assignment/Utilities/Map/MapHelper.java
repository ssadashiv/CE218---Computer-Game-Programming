package Assignment.Utilities.Map;

import Assignment.MainGame.Game;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by el16035 on 13/03/2018.
 */

public class MapHelper {
    private static final int MAP_SIZE = 5;
    private static final int[] INIT_POS = {1,1};

    //If room has been visited by the player
    public static final int ROOM_VISITED = 2;

    //If the room is a neighbour to a room that is visited.
    public static final int ROOM_NEIGHBOR_TO_VISIT = 1;

    //Unexplored room
    public static final int ROOM_NOT_VISIBLE = 0;

    private static Color[][] map = new Color[MAP_SIZE][MAP_SIZE];


    //int 2d array which stores which rooms have been visited before.
    //2 = visited, 1 = neighbor to a visited room, 0= not visited or is neighbour to a visited room
    private int[][] exploredRooms;


    static {

        Random ran = new Random();

        for (int i = 0;i<map.length;i++){
            for (int j = 0; j<map[i].length;j++){
                float r = ran.nextFloat();
                float g = ran.nextFloat();
                float b = ran.nextFloat();

                map[i][j] = new Color(r, g, b);
            }
        }
    }


    private int[] mapPos;

    private Game game;

    public MapHelper(){
        resetMap();
    }

    public void resetMap(){
        System.out.println("reset called in maphelper");
        mapPos = INIT_POS;
        exploredRooms = new int[map.length][map[0].length];
        updateMap();
    }

    private void updateMap(){
        setNeighbourRooms();
        setVisitedRooms();
    }

    private void setNeighbourRooms(){
        int[][] newVisibleRooms = new int[4][2];
        newVisibleRooms[0] = new int[]{mapPos[0]-1, mapPos[1]};
        newVisibleRooms[1] = new int[]{mapPos[0]+1, mapPos[1]};
        newVisibleRooms[2] = new int[]{mapPos[0], mapPos[1]-1};
        newVisibleRooms[3] = new int[]{mapPos[0], mapPos[1]+1};

        for (int[] newRoom : newVisibleRooms) {
            if (roomExists(newRoom) && exploredRooms[newRoom[0]][newRoom[1]] == 0) {
                exploredRooms[newRoom[0]][newRoom[1]] = ROOM_NEIGHBOR_TO_VISIT;
            }
        }
    }

    private void setVisitedRooms(){
        exploredRooms[mapPos[0]][mapPos[1]] = ROOM_VISITED;
    }

    public Color getMap(int[] index){
        if (roomExists(index)) return map[index[0]][index[1]];

        System.out.println("return null");
        return null;
    }

    public Color[][] getMiniMap(){
        return map;
    }

    public boolean[][] getWhichRoomsExist(){
        boolean[][] whichMapExist = new boolean[map.length][map[0].length];

        for (int i=0; i<map.length;i++){
            for (int j=0;j<map[i].length;j++){
                whichMapExist[i][j] = roomExists(new int[]{i, j});
            }
        }
        return whichMapExist;
    }

    public void setMapPos(int[] newPos){
        mapPos = newPos;
        game.closeDoors();
        updateMap();
    }

    public int[] getMapPos(){
        return new int[]{mapPos[0],mapPos[1]};
    }

    public int[][] getExploredRooms(){
        return exploredRooms;
    }

    private static boolean roomExists(int[] index){
        try{
            return map[index[0]][index[1]] != null;
        } catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }



}
