package testing;

import Assignment.Utilities.Map.Room;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

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
        boolean[][] roomMap = Room.generateMap(11);

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
        }
    }
}
