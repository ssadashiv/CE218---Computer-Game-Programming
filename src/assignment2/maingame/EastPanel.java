package assignment2.maingame;


import assignment2.other.SharedValues;
import assignment2.map.MapHelper;
import assignment2.map.MiniMap;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 15/03/2018.
 */

//A panel which displays the minimap
public class EastPanel extends InfoPanel{
    private static final String TITLE_TEXT = "MiniMap";

    private MiniMap miniMap;

    EastPanel(int parentHeight, MapHelper mapHelper){
        super(parentHeight);
        JLabel titleLabel = getTitleLabel(TITLE_TEXT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, TITLE_MARGIN_SOUTH)));

        int initY = titleLabel.getY() + titleLabel.getHeight() + 10;
        miniMap = new MiniMap(this, mapHelper, initY);
    }


    void updateMiniMap(){
        miniMap.updateMap();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g0){
        super.paintComponent(g0);
        if (!SharedValues.gamePaused){
            Graphics2D g = (Graphics2D) g0;
            miniMap.draw(g);
        }
    }
}
