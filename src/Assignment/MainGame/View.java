package Assignment.MainGame;

import Assignment.GameObjects.Bullet;
import Assignment.GameObjects.GameObject;
import Assignment.Utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Iterator;

import Assignment.GameObjects.GameObject;

import Assignment.Other.Constants;

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

    View(Game game) {
        this.game = game;
        mapHelper = new MapHelper();
        mapPos = mapHelper.getMapPos();
        currentBG = mapHelper.getMap(mapPos);

        game.setShipMapHelper(mapHelper);

        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);

        double stretchX = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH / imWidth);
        double stretchY = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_WIDTH / imHeight);

        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);


    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        //g.setBackground(Sprite.map);

        int[] newPos = game.shipMapPos();
        if (!isSamePosition(newPos)){
            System.out.println("swap");
            mapHelper.setMapPos(newPos);
            mapPos = mapHelper.getMapPos();
            currentBG = mapHelper.getMap(mapPos);


            Iterator<GameObject> it = game.objects.iterator();

            while (it.hasNext()){
                GameObject o = it.next();
                if (o instanceof Bullet) {
                    it.remove();
                }
            }
        }

        g.setColor(currentBG);
        g.fillRect(0,0, getWidth(), getHeight());
        //g.setBackground(currentBG);
        //g.drawImage(im, bgTransf, null);
        for (GameObject object : game.objects) object.draw(g);
        game.draw(g);

    }

    private boolean isSamePosition(int[] newPos){
        for (int i=0;i<mapPos.length;i++) if (mapPos[i] != newPos[i]) return false;
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
