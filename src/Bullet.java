import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class Bullet extends GameObject implements Moveable {
    private String direction;

    private final double BULLET_MOVEMENT = MOVEMENT * 3;

    Bullet(BufferedImage image, double x, double y, String direction) {
        super(image);

        this.direction = direction;

        setImageWidth(8);
        setImageHeight(8);

        if(direction.equals("+x")) {
            setX(x + 28);
            setY(y + 20);
        } else if(direction.equals("-x")) {
            setX(x);
            setY(y + 5);
        } else if(direction.equals("-y")) {
            setX(x + 20);
            setY(y);
        } else if(direction.equals("+y")) {
            setX(x + 5);
            setY(y + 27);
        }
    }

    @Override
    public void tick() {
        if(direction.equals("+x")) {
            setX(getX() + BULLET_MOVEMENT);
        } else if(direction.equals("-x")) {
            setX(getX() - BULLET_MOVEMENT);
        } else if(direction.equals("-y")) {
            setY(getY() - BULLET_MOVEMENT);
        } else if(direction.equals("+y")) {
            setY(getY() + BULLET_MOVEMENT);
        }

        for(Enemy enemy:Main.enemies){
            if(intersects(enemy)){
                Player.score++;
                Main.killToInc--;
                enemy.destroy();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), Main.engine.scaleX(getX()), Main.engine.scaleY(getY()), Main.engine.scaleSize(getImageWidth()), Main.engine.scaleSize(getImageHeight()), null);
    }

    boolean intersects(Enemy enemy) {
        return getX() >= enemy.getX() - (enemy.getImageWidth() / 2) + 6 &&
               getX() <= enemy.getX() + (enemy.getImageWidth() / 2) + 16 &&
               getY() >= enemy.getY() - (enemy.getImageHeight() / 2) + 6 &&
               getY() <= enemy.getY() + (enemy.getImageHeight() / 2) + 16;
    }
}
