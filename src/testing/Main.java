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
        double l = 1.9;
        int i = (int) l;

        System.out.println(i);

    }
}
