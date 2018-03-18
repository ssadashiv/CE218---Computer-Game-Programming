package Assignment.Map;

import Assignment.GameObjects.GameObject;
import Assignment.Other.SharedValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 14/03/2018.
 */
public class MapFileParser {
    private static String DIR_NAME = "maps/";
    private static String FORMAT = ".txt";


    /*public static char[][] getObstacles(){
        readFile();
        return obstacles;
    }*/

    public static char[][] readFile(String fileName, int fileRotations) {
        try {
            char[][] objects = fileToArray(new Scanner(new File(DIR_NAME + fileName + FORMAT)));
            if (fileRotations != 0) {
                for (int i=0;i<fileRotations;i++){
                    objects = rotateArray(objects);
                }
            }

            return objects;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        return null;
    }

    private static char[][] fileToArray(Scanner input) {
        String firstLine = input.nextLine();
        int objectCount = firstLine.length();
        char[][] objects = new char[objectCount][objectCount];

        SharedValues.gridSize = objectCount;
        SharedValues.cellSize = (int) (FRAME_SIZE.getWidth() / (double) SharedValues.gridSize);
        int i = 0;

        while (input.hasNextLine()) {
            String line = (i == 0) ? firstLine : input.nextLine();
            for (int j = 0; j < line.length(); j++) {
                objects[i][j] = line.charAt(j);
            }
            i++;
        }

       /* for (int x = 0; x<objects.length;x++){
            for (int j=0;j<objects[x].length;j++){
                //String p = objects[x][j] ? "#" : "-";
                System.out.print(objects[x][j]);
            }
            System.out.println();
        }
*/
        SharedValues.mapLoaded = true;

        return objects;
    }

    private static char[][] rotateArray(char[][] objects) {

        char[][] newArray = new char[objects.length][objects[0].length];


        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[i].length; j++) {
                newArray[j][objects.length - 1 - i] = objects[i][j];
            }
        }


        return newArray;
    }
}
