package assignment2.gameobjects;

import assignment2.gameobjects.projectiles.Projectile;

import java.util.List;

/**
 * Created by el16035 on 20/03/2018.
 */

//interface of the GameObjects which can shoot projectiles
public interface Turret {
    List<Projectile> getProjectiles();
    void clearProjectiles();
    void makeBullet(double angle);
}
