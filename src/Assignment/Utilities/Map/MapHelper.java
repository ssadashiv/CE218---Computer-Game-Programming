package Assignment.Utilities.Map;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by el16035 on 13/03/2018.
 */

public class MapHelper {
    private static Color[][] map = new Color[][]{
            {Color.lightGray, Color.red, Color.BLUE},
            {Color.GREEN, Color.CYAN, Color.BLACK},
            {Color.ORANGE, Color.MAGENTA, Color.pink}
    };




    private int[] mapPos = {1,1};

    public Color getMap(int[] index){
        if (elementExist(index)) return map[index[0]][index[1]];

        System.out.println("return null");
        return null;
    }

    public void setMapPos(int[] newPos){
        mapPos = newPos;
    }

    public int[] getMapPos(){
        return new int[]{mapPos[0],mapPos[1]};
    }


    private boolean elementExist(int[] index){
        try{
            return map[index[0]][index[1]] != null;
        } catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }



}
