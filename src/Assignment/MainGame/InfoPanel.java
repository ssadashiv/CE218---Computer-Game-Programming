package Assignment.MainGame;

import Assignment.Other.SharedValues;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 17/03/2018.
 */

//TODO: Add backgrounds for the information panels
public class InfoPanel extends JPanel{
    private static final int PANEL_WIDTH = 200;
    private int panelHeight;

    public InfoPanel(int panelHeight){
        this.panelHeight = panelHeight;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, panelHeight);
    }

    public void paintComponent(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, PANEL_WIDTH, panelHeight);
    }
}
