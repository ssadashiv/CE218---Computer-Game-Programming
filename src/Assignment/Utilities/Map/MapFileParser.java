package Assignment.Utilities.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by el16035 on 14/03/2018.
 */
public class MapFileParser {
    private static String DIR_NAME = "maps/";
    private static String FILE_NAME = "map1";
    private static String FORMAT = ".txt";
    private static boolean[][] obstacles;
    private static int obstacleCount;


    static {

    }

    public static boolean[][] getObstacles(){
        readFile();
        return obstacles;
    }

    public static void readFile(){
        try {
            fileToArray(new Scanner(new File(DIR_NAME + FILE_NAME + FORMAT)));
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }
    }

    private static void fileToArray(Scanner input){
        String firstLine = input.nextLine();
        obstacleCount = firstLine.length();
        obstacles = new boolean[obstacleCount][obstacleCount];
        int i = 0;

        while (input.hasNextLine()){
            String line = (i == 0) ? firstLine : input.nextLine();
            for (int j = 0; j<line.length(); j++) obstacles[i][j] = (line.charAt(j) == '#');
            i++;
        }

        for (int x = 0; x<obstacles.length;x++){
            for (int j=0;j<obstacles[x].length;j++){
                String p = obstacles[x][j] ? "#" : "-";
                System.out.print(p);
            }
            System.out.println();
        }
    }
}
