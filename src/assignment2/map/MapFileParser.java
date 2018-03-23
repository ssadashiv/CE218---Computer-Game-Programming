package assignment2.map;

import assignment2.other.SharedValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map;

import static assignment2.map.MapConstants.*;
import static assignment2.other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 14/03/2018.
 */
//Parses all the .txt files in the /maps folder and stores the 2D char arrays in a hashmap.
public class MapFileParser {
    private static String DIR_NAME = "maps/";
    private static String FORMAT = ".txt";

    private static final String[] FILE_NAMES = new String[]{
            MAP_1_DOORS,
            MAP_2_DOORS_NONOPPOSITE,
            MAP_2_DOORS_OPPOSITE,
            MAP_3_DOORS,
            MAP_4_DOORS,
            BOSS_ROOM,
            SHOP_ROOM
    };

    private static final Map<String, List<char[][]>> ROOM_MAPS_MAP = new HashMap<>();


    static {
        for (String fName : FILE_NAMES) {
            ROOM_MAPS_MAP.put(fName, new ArrayList<>());
        }
        readFile();
    }

    public static void printRooms() {
        for (String s : ROOM_MAPS_MAP.keySet()) {
            System.out.println(s);
            for (char[][] room : ROOM_MAPS_MAP.get(s)) {
                for (char[] row : room) System.out.println(Arrays.toString(row));
            }

            System.out.println();
        }
    }

    private static void readFile() {
        try {
            for (String fileName : FILE_NAMES) {
                fileToMap(fileName, new Scanner(new File(DIR_NAME + fileName + FORMAT)));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    private static void fileToMap(String fileName, Scanner input) {
        while (input.hasNextLine()) {
            ROOM_MAPS_MAP.get(fileName).add(createObjectArray(input));
        }
    }

    //Create a 2d char array which contains the characters of 16x16 characters int the mapfiles.
    private static char[][] createObjectArray(Scanner input) {
        String firstLine = input.nextLine();
        int objectCount = firstLine.length();
        char[][] objects = new char[objectCount][objectCount];

        SharedValues.gridSize = objectCount;
        SharedValues.cellSize = (int) (FRAME_SIZE.getWidth() / (double) SharedValues.gridSize);
        int i = 0;

        while (input.hasNextLine() && i < objectCount) {
            String line = (i == 0) ? firstLine : input.nextLine();
            for (int j = 0; j < line.length(); j++) {
                objects[i][j] = line.charAt(j);
            }
            i++;
        }
        return objects;
    }

    //Get a random room from the parsed files
    static char[][] getRandomRoom(String fileName, boolean initPos){
        Random r = new Random();
        List<char[][]> rooms = ROOM_MAPS_MAP.get(fileName);
        if (initPos) return rooms.get(0);
        return rooms.get(r.nextInt(rooms.size()));
    }
}
