package assignment2.gameobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by el16035 on 14/03/2018.
 */

//Class for storing the stats of an object.
public class ObjectStats {

    //Stat string
    public static final String MAX_ARMOUR = "max armour";
    public static final String ARMOUR = "armour";
    private static final String LIVES_REMAINING = "lives remaining";

    //The interval between every time the ship can shoot coinBullets. in milliseconds
    public static final String FIRE_RATE = "fire rate";
    private static final String BULLET_SPEED = "bullet speed";
    public static final String BULLET_DAMAGE = "bullet damage";
    private static final String CONTACT_DAMAGE = "contact damage";

    //how much scrap metal the object drops on death. Scrap metal is the currency of the game
    private static final String SCRAP_ON_DEATH = "scrap on death";

    private Map<String, Integer> stats = new HashMap<>();
    private final Map<String, Integer> initStats = new HashMap<>();

    private GameObject parent;

    ObjectStats(GameObject parent, int armour, int livesRemaining, int fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath) {
        this.parent = parent;
        stats.put(MAX_ARMOUR, armour);
        stats.put(ARMOUR, armour);
        stats.put(LIVES_REMAINING, livesRemaining);
        stats.put(FIRE_RATE, fireRate);
        stats.put(BULLET_SPEED, bulletSpeed);
        stats.put(BULLET_DAMAGE, bulletDamage);
        stats.put(CONTACT_DAMAGE, contactDamage);
        stats.put(SCRAP_ON_DEATH, scrapOnDeath);


        //Makes a copy of the initial stats, to be able to reset the current stats. for example when new game is pressed.
        initStats.put(MAX_ARMOUR, armour);
        initStats.put(ARMOUR, armour);
        initStats.put(LIVES_REMAINING, livesRemaining);
        initStats.put(FIRE_RATE, fireRate);
        initStats.put(BULLET_SPEED, bulletSpeed);
        initStats.put(BULLET_DAMAGE, bulletDamage);
        initStats.put(CONTACT_DAMAGE, contactDamage);
        initStats.put(SCRAP_ON_DEATH, scrapOnDeath);
    }

    void newLife() {
        stats.put(ARMOUR, getMaxArmour());
        //stats.put(LIVES_REMAINING, getLivesRemaining() - 1);
    }

    void resetStats() {
        for (String s : initStats.keySet()) {
            stats.put(s, initStats.get(s));
        }
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void addMaxArmour(int amount) {
        stats.put(MAX_ARMOUR, getMaxArmour() + amount);
        stats.put(ARMOUR, getArmour() + amount);
    }

    public void addArmour(int amount) {
        if (amount < 0) parent.timeOutInvincible();
        stats.put(ARMOUR, getArmour() + amount);
        if (getArmour() <= 0) {
            parent.dropScrapMetal(getScrapOnDeath());
        }

        if (getArmour() > getMaxArmour()){
            stats.put(ARMOUR, stats.get(MAX_ARMOUR));
        }
    }

    public void addBulletDamage(int amount) {
        stats.put(BULLET_DAMAGE, getBulletDamage() + amount);
    }

    public void decFireRate(double factor) {
        stats.put(FIRE_RATE, (int) ((double) getFireRate() * factor));
    }





    //decrease lives remain
    public void addLivesRemaining(int amount) {
        stats.put(LIVES_REMAINING, getLivesRemaining() + amount);
    }


    /*
    * GETTER METHODS
    * */
    public int getMaxArmour() {
        return stats.get(MAX_ARMOUR);
    }

    public int getArmour() {
        return stats.get(ARMOUR);
    }

    public int getLivesRemaining() {
        return stats.get(LIVES_REMAINING);
    }

    public int getFireRate() {
        return stats.get(FIRE_RATE);
    }

    public int getBulletSpeed() {
        return stats.get(BULLET_SPEED);
    }

    public int getBulletDamage() {
        return stats.get(BULLET_DAMAGE);
    }

    public int getContactDamage() {
        return stats.get(CONTACT_DAMAGE);
    }

    public int getScrapOnDeath() {
        return stats.get(SCRAP_ON_DEATH);
    }


    /*
    * SETTER METHODS
    * */

    public void setMaxArmour(int newValue) {
        stats.put(MAX_ARMOUR, newValue);
    }

    public void setArmour(int newValue) {
        stats.put(ARMOUR, newValue);
    }

    public void setLivesRemaining(int newValue) {
        stats.put(LIVES_REMAINING, newValue);
    }

    public void setFireRate(int newValue) {
        stats.put(FIRE_RATE, newValue);
    }

    public void setBulletSpeed(int newValue) {
        stats.put(BULLET_SPEED, newValue);
    }

    public void setBulletDamage(int newValue) {
        stats.put(BULLET_DAMAGE, newValue);
    }

    public void setContactDamage(int newValue) {
        stats.put(CONTACT_DAMAGE, newValue);
    }

    public void setScrapOnDeath(int newValue) {
        stats.put(SCRAP_ON_DEATH, newValue);
    }
}
