package assignment2.utilities.objectassist;

import assignment2.gameobjects.*;
import assignment2.gameobjects.obstacles.DoorButton;
import assignment2.gameobjects.obstacles.Obstacle;
import assignment2.gameobjects.projectiles.Bullet;
import assignment2.gameobjects.projectiles.Projectile;
import assignment2.utilities.Vector2D;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


import static assignment2.other.Constants.WALL_REFLECT;

/**
 * Created by el16035 on 14/03/2018.
 */
//Class which handles the hit collisions of the GameObjects
public class HitDetection {
    private static final String NORTH = "NORTH";
    private static final String SOUTH = "SOUTH";
    private static final String WEST = "WEST";
    private static final String EAST = "EAST";

    public static boolean sameClass(GameObject obj1, GameObject obj2) {
        return obj1.getClass() == obj2.getClass();
    }

    private static String whichDirection(GameObject objOne, GameObject objTwo) {
        Rectangle oneBounds = objOne.getBounds();
        Rectangle twoBounds = objTwo.getBounds();

        Vector2D objOneVel = new Vector2D(objOne.velocity);

        Rectangle r = oneBounds.intersection(twoBounds);

        if (r.width >= r.height) {
            //Hits from North or South

            if ((r.y == twoBounds.y && objOneVel.y > 0) || (r.y != twoBounds.y && objOneVel.y < 0)) {
                return (oneBounds.y + (oneBounds.height / 2) < twoBounds.y + (twoBounds.height / 2)) ? NORTH : SOUTH;
            }

        } else {
            //Hit from east or west or on the edge of the obstacle
            if ((r.x == twoBounds.x && objOneVel.x > 0) || (r.x != twoBounds.x && objOneVel.x < 0)) {
                return (oneBounds.x + (oneBounds.width / 2) < twoBounds.x + (twoBounds.width / 2)) ? WEST : EAST;
            }
        }
        return "";
    }

    public static void HitObstacle(Obstacle obstacle, GameObject object) {
        if (isOneProjectile(obstacle, object)) {
            return;
        }

        //Vector2D obsPos = new Vector2D(obstacle.position);
        Rectangle obsBounds = obstacle.getBounds();
        Rectangle objectBounds = object.getBounds();

        String direction = HitDetection.whichDirection(object, obstacle);

        switch (direction) {
            case HitDetection.NORTH:
                object.position.y = obsBounds.y - (objectBounds.height / 2);
                object.velocity.y *= WALL_REFLECT;
                break;
            case HitDetection.SOUTH:
                object.position.y = obsBounds.y + obsBounds.height + (objectBounds.height / 2);
                object.velocity.y *= WALL_REFLECT;
                break;
            case HitDetection.WEST:
                object.position.x = obsBounds.x - (objectBounds.width / 2);
                object.velocity.x *= WALL_REFLECT;
                break;
            case HitDetection.EAST:
                object.position.x = obsBounds.x + obsBounds.width + (objectBounds.width / 2);
                object.velocity.x *= WALL_REFLECT;
                break;
        }
    }

    //when the player hits the door button
    public static void HitDoorButton(DoorButton button) {
        button.pressButton();
    }

    //when any projectile hits something
    public static void ProjectileHitSomething(Projectile projectile, GameObject object) {
        if (!(object instanceof Obstacle) && !(object.isInvincible)) {
            object.getStats().addArmour(-projectile.damage);
        }
        projectile.damage = 0;
        projectile.dead = true;
    }

    //when two gameobjects wihch are not obstacles or projectiles or obstacles
    public static void ContactHit(GameObject obj1, GameObject obj2) {
        if (isOneObstacle(obj1, obj2) || isOneProjectile(obj1, obj2)) return;
        if (!obj1.isInvincible) obj1.getStats().addArmour(-obj2.getStats().getContactDamage());
        if (!obj2.isInvincible) obj2.getStats().addArmour(-obj1.getStats().getContactDamage());
    }

    private static boolean isOneObstacle(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Obstacle || obj2 instanceof Obstacle) {
            if (obj1 instanceof Obstacle) {
                HitObstacle((Obstacle) obj1, obj2);
            } else {
                HitObstacle((Obstacle) obj2, obj1);
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOneProjectile(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Projectile || obj2 instanceof Projectile) {
            if (obj1 instanceof Bullet) {
                ProjectileHitSomething((Projectile) obj1, obj2);
            } else {
                ProjectileHitSomething((Projectile) obj2, obj1);
            }
            return true;
        } else {
            return false;
        }
    }
}
