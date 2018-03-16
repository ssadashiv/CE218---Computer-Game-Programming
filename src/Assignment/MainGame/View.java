package Assignment.MainGame;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.Utilities.Controllers.KeyBindingController;
import Assignment.Utilities.Map.MapHelper;
import Assignment.Utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

import static Assignment.Other.Constants.FRAME_HEIGHT;
import static Assignment.Other.Constants.FRAME_SIZE;
import static Assignment.Other.Constants.FRAME_WIDTH;
import static Assignment.Other.SharedValues.cellSize;
import static Assignment.Other.SharedValues.gridSize;

/**
 * Created by el16035 on 16/01/2018.
 */

//TODO: Only update game when !paused
public class View extends JComponent {
    //background colour

    Image im = Sprite.MILKYWAY1;
    AffineTransform bgTransf;


    private int[] mapPos;
    private Color currentBG;

    private Game game;
    MapHelper mapHelper;

    private EastPanel eastPanel;

    View(Game game) {
        this.game = game;

    }

    void setMapHelper(MapHelper mh){
        mapHelper = mh;
        mapPos = mapHelper.getMapPos();
        currentBG = mapHelper.getMap(mapPos);
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        //g.setBackground(Sprite.map);


        List<GameObject> copyObj = new LinkedList<>(game.objects);

        int[] newPos = game.shipMapPos();
        if (!isSamePosition(newPos)){
            System.out.println("swap");
            mapHelper.setMapPos(newPos);
            mapPos = mapHelper.getMapPos();
            currentBG = mapHelper.getMap(mapPos);


            Iterator<GameObject> it = copyObj.iterator();

            while (it.hasNext()){
                GameObject o = it.next();
                if (o instanceof Bullet) {
                    game.objects.remove(o);
                }
            }

            eastPanel.updateMiniMap();
        }

        g.setColor(currentBG);
        g.fillRect(0,0, getWidth(), getHeight());
        //g.setBackground(currentBG);
        //g.drawImage(im, bgTransf, null);

        Iterator<GameObject> it = copyObj.iterator();
        while (it.hasNext()){
            it.next().draw(g);
        }
        game.draw(g);

        g.setColor(Color.WHITE);
        for (int i=0;i<gridSize;i++){
            g.drawLine(i* cellSize,0, FRAME_HEIGHT, 0);
        }

        for (int j=0;j<gridSize;j++){
            g.drawLine(0, j* cellSize, 0, FRAME_HEIGHT);
        }
    }

    public void setEastPanel(EastPanel eastPanel){
        this.eastPanel = eastPanel;
    }

    //Method to check if the ship has moved to a new map
    private boolean isSamePosition(int[] newPos){
        for (int i=0;i<mapPos.length;i++) if (mapPos[i] != newPos[i]) return false;
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
