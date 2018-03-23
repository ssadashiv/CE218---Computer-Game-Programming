package assignment2.utilities;

import assignment2.maingame.MainFrame;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eriklange on 10.12.2017.
 */

//Class to manage the scores. For example add scores at the end of a game
public class ScoreWriter  {


    //CLASS VARIABLES

    //Dateformat which will format a date which will then be added to
    private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //The file path of the txt file
    private static final String FILE_NAME = "scores";
    private static final String FORMAT = ".txt";

    //ScoreFrame instance to display the scores in that panel.
    private ScoreFrame scoreFrame;

    //List of all Scores.
    private List<Score> scores = new ArrayList<>();

    private MainFrame container;



    //Constructor
    public ScoreWriter(MainFrame container, ScoreFrame scoreFrame) {
        this.container = container;
        this.scoreFrame = scoreFrame;
        createScoreList();
    }


    //Adds the current score of the player to the txt file. Returns a score object.
    public Score addScoreToFile(Score playerScore) {
        String name = playerScore.getName();
        String score = String.valueOf(playerScore.getScore());

        Date d = new Date();
        String date = DF.format(d);
        String newLine = String.join(",", name, score, date);


        //https://docs.oracle.com/javase/7/docs/api/java/io/FileWriter.html
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME + FORMAT, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);


            printWriter.println(newLine);
            printWriter.close();

        } catch (IOException e) {
            System.out.println("Error loading file :" + e.toString());
        }

        return processLine(newLine);
    }

    //Adding all scores of the score text files to a List of Score instances.
    public void createScoreList() {
        Scanner sc;
        try {
            sc = new Scanner(new File(FILE_NAME + FORMAT));

            while (sc.hasNextLine()) {
                scores.add(processLine(sc.nextLine()));
            }


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(container, "Error: File not found. Play the game once, and a score file will be opened.");
        }

        scoreFrame.setScores(scores);
    }

    //Method to process each line of the textfile.
    private Score processLine(String line) {

        //https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html
        List<String> splitString = Arrays.asList(line.split(","));

        String name = splitString.get(0);
        int score;
        Date d;

        try {
            score = Integer.parseInt(splitString.get(1));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing string to integer");
            score = 0;
        }

        try {
            d = DF.parse(splitString.get(2));
        } catch (ParseException e) {
            d = new Date();
            System.out.println("Error parsing string to date");
        }

        return new Score(name, score, d);
    }

    //Method for initially displaying scores on the scoreFrame
    //Will initially display Top 10 All Time scores on the ScoreFrame
    public void displayScores() {
        sortByScore();
        scoreFrame.setScores(scores);
        scoreFrame.updateTable("Top 10 All Time");
    }


    //Method which sorts the score List by score
    public void sortByScore() {

        //Sort the list by score
        //Replaced with Lambda to simplify the code.
        Collections.sort(scores, (o1, o2) -> {
            if (o1.getScore() > o2.getScore()) {
                return -1;
            } else if (o1.getScore() < o2.getScore()) {
                return 1;
            }
            return 0;
        });
    }

    //TODO: FIX THIS BULLCRAP2
    //Code which returns a new list of scores, sorted by score. Will only include scores that was made the last 24 hours.
    public List<Score> getTopTenPastDay() {
        //https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html
        sortByScore();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        Date date = c.getTime();
        Date nowDate = new Date();

        List<Score> newList = new ArrayList<>();

        for (int i = 0;i < scores.size(); i++) {
            if (scores.get(i).getDate().after(date) && scores.get(i).getDate().before(nowDate)) {
                newList.add(scores.get(i));
            }
        }
        return newList;
    }
}