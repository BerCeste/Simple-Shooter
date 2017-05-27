import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *              Created by Batuhan on 2.05.2017.
 */

abstract class GameCreature extends GameObject implements Moveable {
    private double velX, velY;
    protected String direction;

    GameCreature(BufferedImage image) {
        super(image);
    }

    @Override
    public abstract void tick();

    @Override
    public abstract void render(Graphics g);

    double getVelX() {
        return velX;
    }

    void setVelX(double velX) {
        this.velX = velX;
    }

    double getVelY() {
        return velY;
    }

    void setVelY(double velY) {
        this.velY = velY;
    }
}
