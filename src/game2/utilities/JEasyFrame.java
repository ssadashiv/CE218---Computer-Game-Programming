package game2.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 16/01/2018.
 */
public class JEasyFrame extends JFrame {

    public JEasyFrame(Component comp, String title){
        super(title);
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        repaint();
    }
}
