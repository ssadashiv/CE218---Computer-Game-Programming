package assignment2.maingame;

import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.GameObject;
import assignment2.map.MapHelper;
import assignment2.other.Constants;
import assignment2.utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

import static assignment2.other.Constants.FRAME_SIZE;

/**
 * Created by el16035 on 16/01/2018.
 */

//The view the repaints all of the GameObjects
class View extends JComponent {
    //background colour

    private static Image im1 = Sprite.BACKGROUND_STARS;
    private static Image im2 = Sprite.BACKGROUND_SPACESTATION;
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

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        double imWidth = im1.getWidth(null);
        double imHeight = im1.getHeight(null);

        double stretchX = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH / imWidth);
        double stretchY = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_WIDTH / imHeight);

        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);


        //paint the background
        g.drawImage(im1, bgTransf, null);
        g.drawImage(im2, bgTransf, null);
        //g.fillRect(0, 0, getWidth(), getHeight());


        List<GameObject> copyObj = new LinkedList<>(game.objects);

        int[] newPos = game.shipMapPos();
        if (!isSamePosition(newPos)) {
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

        //Drawing the obstacles first
        for (GameObject aCopyObj : copyObj) {
            aCopyObj.draw(g);
        }


    }

    void setEastPanel(EastPanel eastPanel) {
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
