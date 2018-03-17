package Assignment.MainGame;

import Assignment.GameObjects.Items.Item;
import Assignment.GameObjects.PlayerShip;
import Assignment.Other.SharedValues;
import Assignment.Utilities.Map.MapHelper;
import Assignment.Utilities.Map.MiniMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by el16035 on 15/03/2018.
 */
public class EastPanel extends InfoPanel{

    private MainFrame container;
    private MapHelper mapHelper;
    private PlayerShip playerShip;


    private MiniMap miniMap;
    private List<Item> items;



    EastPanel(int parentHeight, MapHelper mapHelper, PlayerShip playerShip){
        super(parentHeight);

        this.mapHelper = mapHelper;
        this.playerShip = playerShip;

        miniMap = new MiniMap(this, mapHelper);
    }

    public void updateMiniMap(){
        miniMap.updateMap();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g0){
        if (!SharedValues.gamePaused){
            Graphics2D g = (Graphics2D) g0;
            miniMap.draw(g);
            super.paintComponent(g);
        }
    }
}
