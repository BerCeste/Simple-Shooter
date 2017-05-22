import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class KeyInput extends KeyAdapter {
    private Player player;

    KeyInput(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}
