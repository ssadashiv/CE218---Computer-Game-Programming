package testing;

import Assignment.Map.MapFileParser;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by el16035 on 13/03/2018.
 */
public class Main {
    private static Color[][] map = new Color[][]{
            {Color.lightGray, Color.red, Color.BLUE},
            {Color.green, Color.CYAN, Color.BLACK},
            {Color.ORANGE, Color.MAGENTA, Color.pink}
    };

    public static void main(String[] args) {
        /*char[][] map = MapFileParser.getObstacles();
        System.out.println("BEFORE ROTATE\n");
        for (char[] c : map) System.out.println(Arrays.toString(c));

        char[][] newArray = new char[map.length][map[0].length];
        for(int i=0; i<map[0].length; i++){
            for(int j=map.length-1; j>=0; j--){
                newArray[i][j] = map[j][i];
            }
        }

        System.out.println("\nAFTER ROTATE\n");
        for (char[] c : newArray) System.out.println(Arrays.toString(c));

*/

        //Testing map autogenerator
        /* boolean[][] roomMap = Room.generateMap(11);

        for (int i=0;i<roomMap.length;i++){
            StringBuilder sb = new StringBuilder();
            for (int j=0;j<roomMap[i].length;j++){
                if (roomMap[i][j]){
                    sb.append('#');
                    continue;
                }

                sb.append('-');
            }

            System.out.println(sb.toString());
        }

        List<int[]> edges = Room.getEdgeRooms(roomMap);

        for (int[] pos : edges){
            System.out.println("edge pos:"+ Arrays.toString(pos));
        }*/
    }
}
