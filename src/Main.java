import Paths.ImagePaths.Enemies.EnemyPaths;
import Paths.ImagePaths.Player.PlayerPaths;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *              Created by Batuhan on 2.05.2017.
 */

class Main extends Engine implements PlayerPaths {
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
    protected void gameCodes(){
        tick();
    }

    @Override
    protected void reset(){

    }

    @Override
    protected void menuBar(){

    }

    @Override
    protected void actions(ActionEvent e){

    }

    @Override
    void resolutions(){
        resolutions.add("800*600");
    }

    // Initialize game
    void initialize() {
    	super.initialize();
        addKeyListener(new KeyInput(player));
    }

    private void tick() {
        player.tick();

        enemy.tick();
    }


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        // TODO: Draw everything between here...

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // Black background

        player.render(g);

        enemy.render(g);

        // ...and here

        g.dispose();
    }


    // Starts the game
    private void start() {
        if(running) return;

        try {
            player = new Player(ImageLoader.loadImage(MACHINE_GUN_R));
        } catch (IOException e) { e.printStackTrace(); }

        setEnemy();

        running = true;
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
        Main engine=new Main();
        engine.start();
        engine.run();
    }

    static void setEnemy() {
        try {
            enemy = new Enemy(ImageLoader.loadImage(EnemyPaths.ZOMBIE_L));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
