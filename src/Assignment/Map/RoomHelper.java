package Assignment.Map;

import java.util.Arrays;

import static Assignment.Map.MapConstants.*;

/**
 * Created by eriklange on 17.03.2018.
 */
public class RoomHelper {


    static char[][] readFile(boolean[] roomNeighbours) {
        int neighbourCount = 0;
        int indexCount  = 0;

        //Counting neighbours
        for (int i = 0; i < roomNeighbours.length; i++) {
            if (roomNeighbours[i]) {
                neighbourCount++;
                indexCount += i;
            }
        }

        String fileName = "null";

        //Rotate the file depending on where the doors are.
        int fileRotations = 0;

        switch (neighbourCount) {
            case 1:
                fileName = MAP_1_DOORS;
                for (int i = 0; i < roomNeighbours.length; i++) {
                    if (roomNeighbours[i]) {
                        fileRotations = i;
                        break;
                    }
                }
                break;
            case 2:
                //Opposite doors
                if (indexCount % 2 == 0) {
                    fileName = MAP_2_DOORS_OPPOSITE;
                    if (roomNeighbours[0]) break;
                    fileRotations = 1;

                } else {
                    fileName = MAP_2_DOORS_NONOPPOSITE;
                    int[] doorPos = new int[neighbourCount];
                    int count = 0;

                    for (int i = 0; i < roomNeighbours.length; i++) {
                        if (roomNeighbours[i]) {
                            doorPos[count] = i;
                            count++;
                        }
                    }

                    if(doorPos[0] == NORTH_ROOM && doorPos[1] == EAST_ROOM){
                        fileRotations = 1;
                    }else if(doorPos[0] == EAST_ROOM && doorPos[1] == SOUTH_ROOM){
                        fileRotations = 2;
                    }else if (doorPos[0] == WEST_ROOM && doorPos[1] == SOUTH_ROOM){
                        fileRotations = 3;
                    }
                }

                break;
            case 3:
                fileName = MAP_3_DOORS;
                for (int i = 0; i < roomNeighbours.length; i++) {
                    if (!roomNeighbours[i]) {
                        fileRotations = i;
                    }
                }
                break;


            case 4:
                fileName = MAP_4_DOORS;
                break;


        }

        return MapFileParser.readFile(fileName, fileRotations);
    }
}
