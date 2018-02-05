package game2.game;

import game2.utilities.Vector2D;

import java.awt.*;

/**
 * Created by el16035 on 05/02/2018.
 */
class Bullet extends GameObject{

    private static final Color COLOR = Color.yellow;
    private static final int RADIUS = 3;
    static final double MUZZLE_VEL = 20;

    Bullet(Vector2D position, Vector2D velocity){
        super(position, velocity, RADIUS);
    }

    public void draw(Graphics2D g) {
        Vector2D startPos = new Vector2D(position);
        startPos.addScaled(velocity, .99);

        //System.out.println("s X:" + startPos.x + ", s Y:" + startPos.y+ ", p X:" + position.x + ", p Y:" + position.y);
        g.setColor(COLOR);
        g.drawLine((int) startPos.x, (int) startPos.y, (int) position.x, (int) position.y);

    }
}
