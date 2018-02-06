package game2.game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
class View extends JComponent {
    //background colour

    private static final Color BG_COLOR = Color.black;
    private Game game;

    View(Game game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0,0,getWidth(), getHeight());

        synchronized (Game.class){
            for (GameObject o: game.objects) {
                o.draw(g);
            }
        }

    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
