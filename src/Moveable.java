import java.awt.*;

/**
 *              Created by Batuhan on 2.05.2017.
 */

interface Moveable {
    double MOVEMENT = 1.9;

    void tick();

    void render(Graphics g);
}
