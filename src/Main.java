import Paths.ImagePaths.Enemies.EnemyPaths;
import Paths.ImagePaths.Player.PlayerPaths;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class Main extends Canvas implements Runnable,
                                     PlayerPaths {
    // Frame constants
    static final int HEIGHT = 600;
    static final int WIDTH = 800;
    static final String TITLE = "Game Test";

    // Variable that holds if the game is running or not
    private boolean running;

    // Main thread
    private Thread thread;

    // Background image for black
    private BufferedImage bg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private static Player player;
    static Enemy enemy;

    @Override
    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                tick();
                delta--;
            }
            render();

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }

            if(enemy.outOfBounds()) {
                setEnemy();
            }
        }
        stop();
    }

    // Initialize game
    private void init() {
        addKeyListener(new KeyInput(player));
    }

    private void tick() {
        player.tick();

        enemy.tick();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // TODO: Draw everything between here...

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // Black background

        player.render(g);

        enemy.render(g);

        // ...and here

        g.dispose();
        bs.show();
    }

    // Constructs the frame
    private Main() {
        running = false;

        JFrame frame = new JFrame(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        requestFocus();

        frame.add(this);

        frame.setVisible(true);

        start();
    }

    // Starts the game
    private void start() {
        if(running) return;

        try {
            player = new Player(ImageLoader.loadImage(MACHINE_GUN_R));
        } catch (IOException e) { e.printStackTrace(); }

        setEnemy();

        running = true;

        thread = new Thread(this);
        thread.start();
    }

    // Stops the game
    private void stop() {
        if(!running) return;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        running = false;
        System.exit(0);
    }

    public static void main(String... args) {
        new Main();
    }

    static void setEnemy() {
        try {
            enemy = new Enemy(ImageLoader.loadImage(EnemyPaths.ZOMBIE_L));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
