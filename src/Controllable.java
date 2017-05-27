import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *              Created by Batuhan on 3.05.2017.
 */

interface Controllable extends KeyListener, Moveable {
    int UP = KeyEvent.VK_UP;
    int DOWN = KeyEvent.VK_DOWN;
    int RIGHT = KeyEvent.VK_RIGHT;
    int LEFT = KeyEvent.VK_LEFT;

    int RELOAD = KeyEvent.VK_R;
    int FIRE = KeyEvent.VK_SPACE;

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    void fire();

    @Override
    default void keyTyped(KeyEvent e) { }
}
