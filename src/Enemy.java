import Paths.ImagePaths.Enemies.EnemyPaths;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class Enemy extends GameCreature implements Moveable {
    String direction;

    private final int ENEMY_SIZE = 32;

    Enemy(BufferedImage image) {
        super(image);

        setImageWidth(ENEMY_SIZE);
        setImageHeight(ENEMY_SIZE);

        direction = "-x";

        setX(getRandNum(Main.WIDTH - 300, Main.WIDTH - 50));
        setY(getRandNum(Main.HEIGHT - 360, Main.HEIGHT - 110));
    }

    @Override
    public void tick() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        getImageDir();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int) getX(), (int) getY(), getImageWidth(), getImageHeight(), null);
    }

    private void getImageDir() {
        try {
            if (direction.equals("+x")) {
                setImage(ImageLoader.loadImage(EnemyPaths.ZOMBIE_R));
            } else if (direction.equals("-x")) {
                setImage(ImageLoader.loadImage(EnemyPaths.ZOMBIE_L));
            } else if (direction.equals("+y")) {
                setImage(ImageLoader.loadImage(EnemyPaths.ZOMBIE_D));
            } else if (direction.equals("-y")) {
                setImage(ImageLoader.loadImage(EnemyPaths.ZOMBIE_U));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getRandNum(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    void implement(AI ai) {
        ai.moveToPlayer();
    }

    boolean outOfBounds() {
        return (getX() <= 0) || (getX() >= Main.WIDTH - 8) || (getY() <= 40) || (getY() >= Main.WIDTH - 70);
    }

    void destroy() {
        Main.setEnemy();
    }

    Circle getCircle() {
        return new Circle(getX(), getY(), getImageWidth());
    }
}
