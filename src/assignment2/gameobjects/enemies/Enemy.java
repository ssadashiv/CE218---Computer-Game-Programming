package assignment2.gameobjects.enemies;

import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.GameObject;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.PlayerShip;
import assignment2.utilities.objectassist.HitDetection;
import assignment2.utilities.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by el16035 on 18/03/2018.
 */

//Superclass for the Enemy GameObjects
public abstract class Enemy extends GameObject {

    public PlayerShip playerShip;
    public int speed;


    public Enemy(Vector2D position, Vector2D velocity, Vector2D direction, int speed, int width, int height, Clip deathSound, Image image) {
        super(position, velocity, direction, width, height, deathSound, image);
        this.speed = speed;

    }

    public void setPlayerShip(PlayerShip ship) {
        this.playerShip = ship;
    }

    public void hitDetected(GameObject other) {
        HitDetection.ContactHit(this, other);
    }


    @Override
    public boolean canHit(GameObject other) {
        if (other instanceof Bullet) {
            if (((Bullet) other).getParent() instanceof PlayerShip) {
                return true;
            }
        }

        return other instanceof Obstacle || other instanceof PlayerShip;
    }

    public abstract void setStats();
}
