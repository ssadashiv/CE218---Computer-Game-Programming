/*
package game1.game;

import javax.swing.*;
import java.awt.*;

*/
/**
 * Created by el16035 on 16/01/2018.
 *//*

public class View extends JComponent {
    //background colour

    public static final Color BG_COLOR = Color.black;
    private Game game;

    public View(Game game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D) g0;
        //paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0,0,getWidth(), getHeight());

        for (game1.game.Asteroid a: game.asteroids) {
            a.draw(g);
        }

        game.ship.draw(g);



    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
*/
