package Assignment.GameObjects;

import Assignment.GameObjects.Projectiles.Projectile;

import java.util.List;

/**
 * Created by el16035 on 20/03/2018.
 */
public interface Turret {
    List<Projectile> getProjectiles();
    void clearProjectiles();
    void makeBullet(double angle);
}
