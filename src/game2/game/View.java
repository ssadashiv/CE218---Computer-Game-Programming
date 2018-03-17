package game2.game;

import game2.Constants;
import game2.utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

/**
 * Created by el16035 on 16/01/2018.
 */
public class View extends JComponent{
    //background colour

    private static final Color BG_COLOR = Color.black;
    Image im = Sprite.MILKYWAY1;
    AffineTransform bgTransf;

    private Game game;

    public View(Game game){
        this.game = game;

        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);

        double stretchX = (imWidth > Constants.FRAME_WIDTH? 1 : Constants.FRAME_WIDTH/imWidth);
        double stretchY = (imHeight >Constants.FRAME_HEIGHT? 1 : Constants.FRAME_WIDTH/imHeight);

        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);

    }

    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        g.drawImage(im, bgTransf, null);

        synchronized (Game.class){
            for (GameObject object : game.objects) object.draw(g);
        }

    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
