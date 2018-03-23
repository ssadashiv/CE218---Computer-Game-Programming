package assignment2.maingame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by el16035 on 17/03/2018.
 */

//superclass for the two sidepanels.
public class InfoPanel extends JPanel {
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);


    static final int PANEL_WIDTH = 200;
    static final int TITLE_MARGIN_SOUTH = 10;

    private static final Color BG_COLOR = new Color(53, 52, 49);
    private int panelHeight;

    public InfoPanel(int panelHeight) {
        this.panelHeight = panelHeight;
        this.setBackground(BG_COLOR);
    }

    JLabel getTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setBounds(0, 5, PANEL_WIDTH, 20);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);

        return label;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, panelHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
