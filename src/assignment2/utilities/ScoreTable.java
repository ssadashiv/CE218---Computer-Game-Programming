package assignment2.utilities;

import javax.swing.table.AbstractTableModel;

/**
 * Created by eriklange on 10.12.2017.
 */

//Class for the model of the score table.
public class ScoreTable extends AbstractTableModel {

    //String array filled with the column names of the model.
    private final static String[] COLUMN_NAMES = {"Rank", "Name", "Score", "Date"};

    //Amount of rows in the table.
    private final static int ROWS = 10;

    //2D object array filled with data from the scores.txt file.
    private Object[][] data;


    //Constructor
    public ScoreTable(){
        resetData();
    }

    void resetData(){
        data = new Object[ROWS][COLUMN_NAMES.length];
    }


    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[][] getEmptyCopy(){
        return new Object[data.length][data[0].length];
    }

    //Getter methods
    @Override
    public int getRowCount() {
        return ROWS;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int col){
        return COLUMN_NAMES[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
