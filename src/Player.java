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
	ArrayList<Bullet> bulletsToRemove=new ArrayList<>();
	double lastShot;

    static int score = 0;

    Player(BufferedImage image) {
        super(image);

        setImageWidth(PLAYER_SIZE);
        setImageHeight(PLAYER_SIZE);

        setX(20);
        setY((Main.height / 2) - 50);

        reloading = false;
    }

    @Override
    public void fire(){
	    if(lastShot+500000000<System.nanoTime()){
		    try{
			    bullets.add(new Bullet(ImageLoader.loadImage(BULLET),getX(),getY(),direction));

			    for(Bullet bullet : bullets){
				    if(bullet.getX()<0||bullet.getX()>=Main.width-getImageWidth()-8||bullet.getY()<0||bullet.getY()>=Main.height-70){
					    bulletsToRemove.add(bullet);
				    }
			    }
			    for(Bullet b : bulletsToRemove) bullets.remove(b);
			    bulletsToRemove.clear();
		    }catch(IOException e){
			    e.printStackTrace();
		    }

		    ammo--;
		    lastShot=System.nanoTime();
	    }
    }

    @Override
    public void tick() {
	    for(Bullet bullet : bullets){
		    bullet.tick();
	    }

        if(getX()+getVelX() <= 0) {
            setX(0);
        }
        else if(getX()+getVelX() >= Main.WIDTH - getImageWidth() - 8) {
            setX(Main.WIDTH - getImageWidth() - 8);
        }else setX(getX() + getVelX());

        if(getY()+getVelY() <= 40) {
            setY(40);
        }
        else if(getY()+getVelY() >= Main.HEIGHT - 70) {
            setY(Main.HEIGHT - 70);
        }else setY(getY() + getVelY());

        reload();

        if(!reloading) {
            getImageDir();
        }

        for(Enemy enemy:Main.enemies){
	        if(intersects(enemy)){
		        Player.score--;
		        enemy.destroy();
	        }

	        //enemy.implement(new AI(this));
        }
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
        g.drawImage(getImage(), Main.engine.scaleX(getX()), Main.engine.scaleY(getY()),  Main.engine.scaleSize(getImageWidth()), Main.engine.scaleSize(getImageHeight()), null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Sathu", Font.PLAIN, 20));
        g.drawString(String.valueOf(ammo) + "/" + String.valueOf(maxAmmo), 10, 30);

        g.drawString(String.valueOf(score), Main.width - 40, 30);

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
