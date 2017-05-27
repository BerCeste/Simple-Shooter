import Paths.ImagePaths.Player.PlayerPaths;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class Player extends GameCreature implements Controllable,
                                             PlayerPaths {
    private final int PLAYER_SIZE = 32;
    private final int RELOAD_TIME = 1500;
    private boolean reloading;
    private long beforeReload;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    private String direction = "+x";

    private int maxAmmo = 30;
    private int ammo = maxAmmo;

    static int score = 0;

    Player(BufferedImage image) {
        super(image);

        setImageWidth(PLAYER_SIZE);
        setImageHeight(PLAYER_SIZE);

        setX(20);
        setY((Main.HEIGHT / 2) - 50);

        reloading = false;
    }

    @Override
    public void fire() {
        try {
            bullets.add(new Bullet(ImageLoader.loadImage(BULLET), getX(), getY(), direction));

            for(Bullet bullet : bullets) {
                if(bullet.getX() < 0 || bullet.getX() >= Main.WIDTH - getImageWidth() - 8 || bullet.getY() < 0 || bullet.getY() >= Main.HEIGHT - 70) {
                    bullets.remove(bullet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ConcurrentModificationException ex) { }

        ammo--;
    }

    @Override
    public void tick() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        try {
            for(Bullet bullet : bullets)
                bullet.tick();
        } catch (ConcurrentModificationException e) { }

        /* Prevents the player to get out of frame */
        if(getX() <= 0) {
            setX(0);
        }
        if(getX() >= Main.WIDTH - getImageWidth() - 8) {
            setX(Main.WIDTH - getImageWidth() - 8);
        }
        if(getY() <= 40) {
            setY(40);
        }
        if(getY() >= Main.HEIGHT - 70) {
            setY(Main.HEIGHT - 70);
        }
        /* */

        reload();

        if(!reloading) {
            getImageDir();
        }

        if(intersects(Main.enemy)) {
            Player.score--;
            Main.enemy.destroy();
        }

        Main.enemy.implement(new AI(this));
    }

    private void reload() {
        if(reloading) {
            playReloadSound();

            getImageDir();

            if(System.currentTimeMillis() - beforeReload >= RELOAD_TIME) {
                getImageDir();

                ammo = maxAmmo;

                reloading = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int)getX(), (int)getY(), getImageWidth(), getImageHeight(), null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Sathu", Font.PLAIN, 20));
        g.drawString(String.valueOf(ammo) + "/" + String.valueOf(maxAmmo), 10, 30);

        g.drawString(String.valueOf(score), Main.WIDTH - 40, 30);

        try {
            for (Bullet bullet : bullets) {
                bullet.render(g);
            }
        } catch (ConcurrentModificationException e) { }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == UP) {
            setVelY(-MOVEMENT);
            direction = "-y";
        } else if(key == DOWN) {
            setVelY(MOVEMENT);
            direction = "+y";
        } if(key == RIGHT) {
            setVelX(MOVEMENT);
            direction = "+x";
        } else if(key == LEFT) {
            setVelX(-MOVEMENT);
            direction = "-x";
        }

        if(key == FIRE && !reloading && hasEnoughAmmo()) {
            fire();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == UP || key == DOWN) {
            setVelY(0);
        } else if(key == LEFT || key == RIGHT) {
            setVelX(0);
        } else if(key == Controllable.RELOAD) {
            if(!reloading && (ammo != maxAmmo)) {
                beforeReload = System.currentTimeMillis();
                reloading = true;
            }
        }
    }

    private void getImageDir() {
        try {
            if(direction.equals("+x")) {
                setImage(reloading ? ImageLoader.loadImage(RELOAD_R) : ImageLoader.loadImage(MACHINE_GUN_R));
            } else if(direction.equals("-x")) {
                setImage(reloading ? ImageLoader.loadImage(RELOAD_L) : ImageLoader.loadImage(MACHINE_GUN_L));
            } else if(direction.equals("-y")) {
                setImage(reloading ? ImageLoader.loadImage(RELOAD_U) : ImageLoader.loadImage(MACHINE_GUN_U));
            } else if(direction.equals("+y")) {
                setImage(reloading ? ImageLoader.loadImage(RELOAD_D) : ImageLoader.loadImage(MACHINE_GUN_D));
            }
        } catch (IOException e) { }
    }

    // TODO: Plays reload sound
    private void playReloadSound() {

    }

    private boolean hasEnoughAmmo() {
        return ammo > 0;
    }

    private boolean intersects(Enemy enemy) {
        /*
        return getX() >= enemy.getX() - (enemy.getImageWidth() / 2) &&
                getX() <= enemy.getX() + (enemy.getImageWidth() / 2) &&
                getY() >= enemy.getY() - (enemy.getImageHeight() / 2) &&
                getY() <= enemy.getY() + (enemy.getImageHeight() / 2);
                */
        return getCircle().intersects(enemy.getCircle().getCenterX(), enemy.getCircle().getCenterY(), enemy.getCircle().getRadius(), enemy.getCircle().getRadius());
    }

    Circle getCircle() {
        return new Circle(getX(), getY(), getImageWidth());
    }
}
