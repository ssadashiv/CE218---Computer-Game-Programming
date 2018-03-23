package assignment2.maingame;

import assignment2.gameobjects.ObjectStats;
import assignment2.gameobjects.PlayerShip;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import static assignment2.gameobjects.ObjectStats.*;
import static assignment2.other.Constants.FRAME_HEIGHT;
import static assignment2.other.Constants.FRAME_WIDTH;

/**
 * Created by el16035 on 21/03/2018.
 */

//The class for the JFrame that contains the shop window. It allows the player to upgrade their stats.
public class ShopFrame extends JFrame {
    private static final EmptyBorder buttonBorder = new EmptyBorder(10,10,10,10);
    private static final Dimension DIMENSION = new Dimension(FRAME_WIDTH / 4 * 3, FRAME_HEIGHT / 4 * 3);


    private static final String TITLE = "Tae's Fix n Trix Shop";
    private static final String TOP_TEXT = "Welcome to the Shop!";

    private static final Map<String, Integer> UPGRADES_BOUGHT = new HashMap<>();
    private static final Map<String, Integer> UPGRADE_COST = new HashMap<>();
    private static final Map<String, Double> UPGRADE_EFFECT = new HashMap<>();
    private static final double COST_MULTIPLIER = 1.5;

    private ObjectStats stats;
    private PlayerShip playerShip;

    private JButton addMaxArmour = new JButton();
    private JButton fixArmour = new JButton();
    private JButton addBulletDamage = new JButton();
    private JButton decFireRate = new JButton();
    private final JPanel pane = new JPanel();

    static {
        UPGRADE_COST.put(MAX_ARMOUR, 100);
        UPGRADE_COST.put(ARMOUR, 30);
        UPGRADE_COST.put(BULLET_DAMAGE, 70);
        UPGRADE_COST.put(FIRE_RATE, 70);

        //The effect the upgrade will have on the stats
        UPGRADE_EFFECT.put(MAX_ARMOUR, 100.0);
        UPGRADE_EFFECT.put(ARMOUR, 75.0);
        UPGRADE_EFFECT.put(BULLET_DAMAGE, 5.0);
        UPGRADE_EFFECT.put(FIRE_RATE, 0.97);

        resetUpgradesBought();
    }

    public ShopFrame(PlayerShip playerShip) {
        super(TITLE);
        this.playerShip = playerShip;
        this.stats = playerShip.getStats();

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setPreferredSize(DIMENSION);

        JLabel topLabel = new JLabel(TOP_TEXT);
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLabel.setBorder(buttonBorder);
        pane.add(topLabel);

        setButtons(fixArmour, ARMOUR);
        setButtons(addMaxArmour, MAX_ARMOUR);
        setButtons(addBulletDamage, BULLET_DAMAGE);
        setButtons(decFireRate, FIRE_RATE);


        getContentPane().add(pane);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                playerShip.canMove = true;
            }
        });
        setResizable(false);
        setVisible(true);

        updateButtons();
    }

    private void addMargin(){
        pane.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void setButtons(JButton b, String type){
        addMargin();
        b.setForeground(Color.BLACK);
        b.addActionListener(new BuyItem(type));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBorder(buttonBorder);
        b.setBackground(new Color(181, 241, 251));
        b.setOpaque(true);
        pane.add(b);
    }

    private boolean hasPlayerSufficientFunds(int amount) {
        return playerShip.getScrapMetal() >= amount;
    }

    //update the buttons after every purhcase
    private void updateButtons() {
        fixArmour.setText("Current Armour(" + stats.getArmour() + "/"+ stats.getMaxArmour()+ ") //Fix for (" + getCost(ARMOUR) + " scraps)");
        addMaxArmour.setText("Max Armour(" + stats.getMaxArmour() + ") //Upgrade for (" + getCost(MAX_ARMOUR) + " scraps)");
        addBulletDamage.setText("Bullet Damage(" + stats.getBulletDamage() + ") //Upgrade for (" + getCost(BULLET_DAMAGE) + " scraps)");
        decFireRate.setText("Fire Rate(" + stats.getFireRate() + ") //Upgrade for (" + getCost(FIRE_RATE) + " scraps)");

        boolean armourEnable = enableOrDisableButton(ARMOUR) && stats.getArmour() < stats.getMaxArmour();

        fixArmour.setEnabled(armourEnable);
        addMaxArmour.setEnabled(enableOrDisableButton(MAX_ARMOUR));
        addBulletDamage.setEnabled(enableOrDisableButton(BULLET_DAMAGE));
        decFireRate.setEnabled(enableOrDisableButton(FIRE_RATE));
    }

    //disable the button if the player does not have enough scrap metal to buy the upgrade.
    private boolean enableOrDisableButton(String type){
        return hasPlayerSufficientFunds(getCost(type));
    }

    private int getCost(String upgradeType) {
        return (int) (UPGRADE_COST.get(upgradeType) * (Math.pow(COST_MULTIPLIER, UPGRADES_BOUGHT.get(upgradeType))));
    }

    private static void resetUpgradesBought() {
        UPGRADES_BOUGHT.put(MAX_ARMOUR, 0);
        UPGRADES_BOUGHT.put(ARMOUR, 0);
        UPGRADES_BOUGHT.put(BULLET_DAMAGE, 0);
        UPGRADES_BOUGHT.put(FIRE_RATE, 0);
    }

    private class BuyItem implements ActionListener {
        private String item;

        BuyItem(String item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (item) {
                case MAX_ARMOUR:
                    stats.addMaxArmour((int) getEffect());
                    break;
                case ARMOUR:
                    stats.addArmour((int) getEffect());
                    break;
                case BULLET_DAMAGE:
                    stats.addBulletDamage((int) getEffect());
                    break;
                case FIRE_RATE:
                    stats.decFireRate(getEffect());
                    break;
            }
            buyItem();
            updateButtons();
        }

        private double getEffect() {
            return Math.round(UPGRADE_EFFECT.get(item));
        }

        private void buyItem() {
            playerShip.useScrapMetal(getCost(item));
            UPGRADES_BOUGHT.put(item, UPGRADES_BOUGHT.get(item) + 1);
            updateButtons();
        }
    }
}
