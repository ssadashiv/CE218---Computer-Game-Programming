package testing;

import java.awt.*;

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

        int i = 0;
        int j = -1;
        System.out.println("i=" + i+", j="+j+" : " + (map[i][j] == null));

    }
}
