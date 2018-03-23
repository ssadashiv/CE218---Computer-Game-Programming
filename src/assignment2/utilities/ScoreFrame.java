package assignment2.utilities;

import assignment2.maingame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by eriklange on 10.12.2017.
 */

//Class for displaying scores in a jtable in a jpanel
public class ScoreFrame extends JFrame implements ActionListener {
    //CLASS VARIABLES

    //DateFormat which all date strings will be formatted as.
    private final static DateFormat DF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //Both of the following strings are used for
    //  - Setting text of radiobuttons to pick what type of scores which will be displayed
    //  - To call ScoreManager and receieve data of either top 10 scores of all time or top 10 last 24 hours
    private final static String TOP_TEN = "Top 10 All Time";
    private final static String PAST_DAY = "Top 10 Last 24 Hours";


    //ScoreManager which will be used to query "scores.txt" and add more data to it.
    private ScoreWriter scoreManager;

    //Custom model for the JTable.
    private ScoreTable model;

    //The JTable which will display the scores.
    private JTable table;

    //List of objects of type Score. Will be initialized with the data from "scores.txt"
    private List<Score> scores;


    private JPanel mainPanel = new JPanel();
    //Constructor
    public ScoreFrame() {
        super("Scores");
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(350, 500));
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        createComponents();

        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    //Method to create all the components of ScoreFrame
    private void createComponents() {

        //Create 4 different JPanels to get the correct layout.
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();

        JPanel radioPanel = new JPanel();
        JPanel optionBtnPanel = new JPanel();


        southPanel.add(radioPanel, BorderLayout.NORTH);
        southPanel.add(optionBtnPanel, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        //Creating the score table.
        model = new ScoreTable();
        table = new JTable(model);

        table.setPreferredScrollableViewportSize(getPreferredSize());
        table.setFillsViewportHeight(true);

        JScrollPane p = new JScrollPane(table);
        northPanel.add(p);
        northPanel.setPreferredSize(new Dimension(p.getBounds().width, getBounds().height / 2));


        //Creating radio buttons
        ButtonGroup group = new ButtonGroup();
        JRadioButton topTenBtn = new JRadioButton();
        JRadioButton pastDayBtn = new JRadioButton();

        topTenBtn.setSelected(true);

        topTenBtn.setText(TOP_TEN);
        pastDayBtn.setText(PAST_DAY);

        topTenBtn.addActionListener(this);
        pastDayBtn.addActionListener(this);

        group.add(topTenBtn);
        group.add(pastDayBtn);


        radioPanel.add(topTenBtn, BorderLayout.WEST);
        radioPanel.add(pastDayBtn, BorderLayout.EAST);
    }

    //Setter method for ScoreManager
    public void setScoreParser(ScoreWriter sm) {
        scoreManager = sm;
        sm.sortByScore();
        updateTable(TOP_TEN);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    //Setter methods for the scores
    public void setScores(List<Score> scores) {
        this.scores = scores;
    }



    //Method to update the score table. Takes a string as an argument which is either "Past 24 Hours" or "Top 10"
    public void updateTable(String scoreType) {
        model.resetData();

        Object[][] tempData = model.getEmptyCopy();

        List<Score> temp;

        if (scoreType.equals(TOP_TEN)) {
            scoreManager.sortByScore();
            temp = scores;
        } else {
            temp = scoreManager.getTopTenPastDay();
        }

        for (int i = 0; i < tempData.length && i < temp.size(); i++) {
            Score s = temp.get(i);
            String date = DF.format(s.getDate());

            tempData[i][0] = i + 1;
            tempData[i][1] = s.getName();
            tempData[i][2] = s.getScore();
            tempData[i][3] = date;
        }

        model.setData(tempData);

        //https://docs.oracle.com/javase/7/docs/api/javax/swing/table/AbstractTableModel.html#fireTableDataChanged%28%29
        model.fireTableDataChanged();
    }

    //Runs every time one of the 2 radiobuttons or the 2 JButtons is clicked.
    @Override
    public void actionPerformed(ActionEvent e) {
        //Button clicked

        updateTable(e.getActionCommand());
    }
}

