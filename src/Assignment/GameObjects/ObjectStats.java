package Assignment.GameObjects;

/**
 * Created by el16035 on 14/03/2018.
 */
public class ObjectStats {

    private int maxArmour;

    private int armour;
    private int livesRemaining;

    //The interval between every time the ship can shoot bullets. in milliseconds
    private long fireRate;
    private int bulletSpeed;
    private int bulletDamage;


    private int contactDamage;

    //how much scrap metal the object drops on death. Scrap metal is the currency of the game
    private int scrapOnDeath;

    private ObjectStats initStats;




    ObjectStats(int armour, int livesRemaining, long fireRate, int bulletSpeed, int bulletDamage, int contactDamage, int scrapOnDeath){
        this.armour = armour;
        this.livesRemaining = livesRemaining;
        this.fireRate = fireRate;

        this.bulletSpeed = bulletSpeed;
        this.bulletDamage = bulletDamage;
        this.contactDamage = contactDamage;
        this.scrapOnDeath = scrapOnDeath;

        maxArmour = this.armour;
    }


    void newLife(){
        armour = maxArmour;
        livesRemaining--;
    }

    public int getArmour() {
        return armour;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    public void setFireRate(long fireRate) {
        this.fireRate = fireRate;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public int getContactDamage() {
        return contactDamage;
    }

    public int getScrapOnDeath() {
        return scrapOnDeath;
    }


    public void addArmour(int amt) {
        armour += amt;
    }

    //decrease lives remain
    public void addLivesRemaining(int amount) {
        livesRemaining += amount;
    }

    public long getFireRate() {
        return fireRate;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public void setContactDamage(int contactDamage) {
        this.contactDamage = contactDamage;
    }

    public void setScrapOnDeath(int scrapOnDeath) {
        this.scrapOnDeath = scrapOnDeath;
    }
}
