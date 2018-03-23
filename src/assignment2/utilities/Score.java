package assignment2.utilities;
import java.util.Date;


/**
 * Created by eriklange on 10.12.2017.
 */

//Class to store different Scores.
public class Score {

    //Name of the player that holds the score
    private String name;

    //The score
    private int score;

    //The date and time the game finished
    private Date date;


    //Constructor
    public Score(String name, int score, Date d){
        this.name = name;
        this.score = score;
        this.date = d;
    }


    //Getter methods
    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }


    //toString method which returns the values of the class.
    @Override
    public String toString(){
        return "Name: " + name + ", Score: "+score+ ", Date: "+date;
    }
}
