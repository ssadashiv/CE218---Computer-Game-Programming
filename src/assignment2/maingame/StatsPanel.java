package assignment2.maingame;

import assignment2.gameobjects.ObjectStats;
import assignment2.gameobjects.PlayerShip;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by el16035 on 17/03/2018.
 */

//The class on the left-hand side of the screen. Displays the stats of the player, as well as the score and the amount of scrap metal the player has.
public class StatsPanel extends InfoPanel {
    private static final EmptyBorder border = new EmptyBorder(0, 10, 10, 0);
    private static final String TITLE_TEXT = "Player Stats";

    private PlayerShip playerShip;
    private ObjectStats stats;
    private Game game;


    private JLabel scrapMetal = new JLabel();
    private JLabel level = new JLabel();
    private JLabel score = new JLabel();
    private JLabel livesRemaining = new JLabel();
    private JLabel armour = new JLabel();

    private JLabel bulletDamage = new JLabel();
    private JLabel fireRate = new JLabel();

    public StatsPanel(PlayerShip playerShip, Game game, int parentHeight) {
        super(parentHeight);
        this.playerShip = playerShip;
        this.stats = playerShip.getStats();
        this.game = game;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getTitleLabel(TITLE_TEXT));
        add(Box.createRigidArea(new Dimension(0, TITLE_MARGIN_SOUTH)));


        setLabel(scrapMetal);
        setLabel(level);
        setLabel(score);
        setLabel(livesRemaining);
        setLabel(armour);
        setLabel(bulletDamage);
        setLabel(fireRate);
    }

    private void setLabel(JLabel l) {
        l.setForeground(Color.WHITE);
        l.setBorder(border);
        add(l);
    }

    void updateStats() {
        scrapMetal.setText("Scrap Metal: " + playerShip.getScrapMetal());
        level.setText("Current Level: " + game.getCurrentLevel());
        score.setText("Score: " + playerShip.getScore());
        livesRemaining.setText("Lives Left: " + stats.getLivesRemaining());
        armour.setText("Armour: " + stats.getArmour() + "/" + stats.getMaxArmour());
        bulletDamage.setText("Bullet Damage: " + stats.getBulletDamage());
        fireRate.setText("Fire Rate: " + (1000.0 / stats.getFireRate()) + "/s");

    }
}
