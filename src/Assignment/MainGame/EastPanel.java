package Assignment.MainGame;

import Assignment.GameObjects.Items.Item;
import Assignment.GameObjects.PlayerShip;
import Assignment.Utilities.Map.MapHelper;
import Assignment.Utilities.Map.MiniMap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by el16035 on 15/03/2018.
 */
public class EastPanel extends JPanel{
    private static final int PANEL_WIDTH = 200;

    private MainFrame container;
    private MapHelper mapHelper;
    private PlayerShip playerShip;


    private MiniMap miniMap;
    private List<Item> items;



    EastPanel(MainFrame container, MapHelper mapHelper, PlayerShip playerShip){
        this.container = container;
        this.mapHelper = mapHelper;
        this.playerShip = playerShip;

        miniMap = new MiniMap(this, mapHelper);
    }

    public void updateMiniMap(){
        miniMap.updateMap();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, container.getHeight());
    }

    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D) g0;

        g.setColor(Color.WHITE);
        g.fillRect(0,0, PANEL_WIDTH, container.getHeight());
        miniMap.draw(g);
    }
}
