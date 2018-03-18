package Assignment.MainGame;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.Map.MapHelper;
import Assignment.Map.Room;
import Assignment.Other.Constants;
import Assignment.Utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

import static Assignment.Other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/01/2018.
 */

//TODO: Only update game when !paused
public class View extends JComponent {
    //background colour

    private static Image im = Sprite.BACKGROUND;
    AffineTransform bgTransf;


    private int[] mapPos;

    private Game game;
    MapHelper mapHelper;

    private EastPanel eastPanel;

    View(Game game) {
        this.game = game;
    }

    void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
    }


    void newGame() {
        mapPos = mapHelper.getMapPos();
        //currentBG = mapHelper.getRoomColor(mapPos);
    }


    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        //g.setBackground(Sprite.map);

        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);

        double stretchX = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH / imWidth);
        double stretchY = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_WIDTH / imHeight);

        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);


        //paint the background
        g.drawImage(im, bgTransf, null);
        g.fillRect(0, 0, getWidth(), getHeight());




        List<GameObject> copyObj = new LinkedList<>(game.objects);

        int[] newPos = game.shipMapPos();
        if (!isSamePosition(newPos)) {
            System.out.println("swap");
            mapHelper.setMapPos(newPos);
            mapPos = mapHelper.getMapPos();
            //currentBG = mapHelper.getRoomColor(mapPos);
            Iterator<GameObject> it = copyObj.iterator();

            while (it.hasNext()) {
                GameObject o = it.next();
                if (o instanceof Bullet) {
                    game.objects.remove(o);
                }
            }
        }

        eastPanel.updateMiniMap();
        Iterator<GameObject> it = copyObj.iterator();
        while (it.hasNext()) {
            it.next().draw(g);
        }
    }

    public void setEastPanel(EastPanel eastPanel) {
        this.eastPanel = eastPanel;
    }

    //Method to check if the ship has moved to a new map
    private boolean isSamePosition(int[] newPos) {
        for (int i = 0; i < mapPos.length; i++) if (mapPos[i] != newPos[i]) return false;
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        return FRAME_SIZE;
    }
}
