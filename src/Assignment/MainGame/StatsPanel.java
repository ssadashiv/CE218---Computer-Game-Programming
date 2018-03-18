package Assignment.MainGame;

import Assignment.GameObjects.ObjectStats;
import Assignment.GameObjects.PlayerShip;
import Assignment.Other.SharedValues;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by el16035 on 17/03/2018.
 */
public class StatsPanel extends InfoPanel {
    private static final Color BG_COLOR = new Color(50, 21, 75);
    private static final int Y_DIFF = 15;
    private static final int X_POS = 15;

    private ObjectStats stats;
    private Map<String, Integer> statsMap;

    public StatsPanel(int parentHeight, ObjectStats stats) {
        super(parentHeight, BG_COLOR);
        this.stats = stats;
    }

    public void updateStats(){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g0){
        if (!SharedValues.gamePaused){
            Graphics2D g = (Graphics2D) g0;
            super.paintComponent(g);
            statsMap = stats.getStats();

            int yVal = Y_DIFF;

            g.setColor(Color.WHITE);
            for (String s : statsMap.keySet()){
                g.drawString(s + ": " + statsMap.get(s), X_POS, yVal);
                yVal += Y_DIFF;
            }

        }
    }


}
