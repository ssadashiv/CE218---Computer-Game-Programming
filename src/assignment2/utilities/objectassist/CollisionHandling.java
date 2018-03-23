package assignment2.utilities.objectassist;

import assignment2.gameobjects.GameObject;

/**
 * Created by el16035 on 18/03/2018.
 */
//collisionhandling interface. implemented by the GameObjects
public interface CollisionHandling {
    void hitDetected(GameObject other);
}
