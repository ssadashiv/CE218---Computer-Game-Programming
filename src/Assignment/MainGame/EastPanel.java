package Assignment.MainGame;

import Assignment.GameObjects.Items.Item;
import Assignment.GameObjects.PlayerShip;
import Assignment.Other.SharedValues;
import Assignment.Map.MapHelper;
import Assignment.Map.MiniMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by el16035 on 15/03/2018.
 */
public class EastPanel extends InfoPanel{
    private static final Color BG_COLOR = new Color(104, 99, 92);
    private PlayerShip playerShip;

    private MiniMap miniMap;
    private List<Item> items;

    private JLabel miniMapLabel = new JLabel("Minimap");
    private static final int LABEL_X = 0;
    private static final int LABEL_Y = 5;
    private static final int LABEL_HEIGHT = 20;
    private static final int LABEL_WIDTH = InfoPanel.PANEL_WIDTH;

    EastPanel(int parentHeight, MapHelper mapHelper, PlayerShip playerShip){
        super(parentHeight, BG_COLOR);
        this.playerShip = playerShip;

        miniMapLabel.setBounds(LABEL_X, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT);
        miniMapLabel.setHorizontalAlignment(JLabel.CENTER);
        miniMapLabel.setVerticalAlignment(JLabel.CENTER);
        miniMapLabel.setForeground(new Color(218, 120,0));
        add(miniMapLabel);

        int initY = miniMapLabel.getY() + miniMapLabel.getHeight();
        miniMap = new MiniMap(this, mapHelper, initY);
    }


    public void updateMiniMap(){
        miniMap.updateMap();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g0){
        if (!SharedValues.gamePaused){
            Graphics2D g = (Graphics2D) g0;
            super.paintComponent(g);
            miniMap.draw(g);
        }
    }
}
