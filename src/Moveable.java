import java.awt.*;

/**
 *              Created by Batuhan on 2.05.2017.
 */

interface Moveable {
    double MOVEMENT = 3.7;

    void tick();

    void render(Graphics g);
}
