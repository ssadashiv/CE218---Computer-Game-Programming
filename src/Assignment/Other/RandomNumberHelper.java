package Assignment.Other;

import java.util.List;
import java.util.Random;

/**
 * Created by eriklange on 16.03.2018.
 */
public class RandomNumberHelper {
    private Random r = new Random();

    private double[] range;
    private int sum;

    private double[] probabilities;

    public void setProbabilities(double[] probabilities){
        this.probabilities = probabilities;
        setSum();
        generateArray();
    }



    private void setSum(){
        double doubleSum = 0;
        for (double d : probabilities) doubleSum += d;
        sum = (int) doubleSum;

    }

    private void generateArray(){
        range = new double[probabilities.length];

        int currentValue = 0;
        for (int i =0;i<range.length;i++){
            range[i] = probabilities[i] + currentValue;
            currentValue += probabilities[i];
        }
    }


    public int getRandomNumber(){
        int randomNum = r.nextInt(sum+1);
        for (int i=0;i<range.length;i++) {
            if (randomNum < range[i]) return i;
        }
        return 0;
    }

    public int randomInt(int bound){
        return r.nextInt(bound);
    }
}
