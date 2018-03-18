package Assignment.MainGame;

import Assignment.Other.SharedValues;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 17/03/2018.
 */

//TODO: Add backgrounds for the information panels
public class InfoPanel extends JPanel{
    public static final int PANEL_WIDTH = 200;
    private int panelHeight;
    private Color bgColor;

    public InfoPanel(int panelHeight, Color bgColor){
        this.panelHeight = panelHeight;
        this.setBackground(bgColor);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, panelHeight);
    }

    public void paintComponent(Graphics2D g) {
        super.paintComponent(g);/*
        g.setColor(bgColor);
        g.fillRect(0, 0, PANEL_WIDTH, panelHeight);*/
    }
}
