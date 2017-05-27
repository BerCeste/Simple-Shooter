import Paths.ImagePaths.Enemies.EnemyPaths;
import Paths.ImagePaths.Player.PlayerPaths;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

    static Player player;
    static ArrayList<Enemy> enemies=new ArrayList<>();
    static boolean addEnemy=true;
    public static int killToInc=2;

    Main(){
        start();
    }

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
        resolutions.add("1200*900");
    }

    // Initialize game
    void initialize() {
    	super.initialize();
        addKeyListener(new KeyInput(player));
    }

    private void tick() {
        player.tick();

        for(Enemy enemy:enemies)enemy.tick();
        setEnemy();
    }


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        // TODO: Draw everything between here...

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this); // Black background

        player.render(g);

        for(Enemy enemy:enemies)enemy.render(g);

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
        engine=new Main();
        engine.run();
    }

    void setEnemy(){
	    for(Enemy e : Enemy.destroy){
		    enemies.remove(e);
	    }

	    if(addEnemy){
		    try{
			    enemies.add(new Enemy(ImageLoader.loadImage(EnemyPaths.ZOMBIE_L)));
			    } catch(IOException e){
				    e.printStackTrace();
		    }
		    addEnemy=false;
	    }
	    if(killToInc==0){
		    addEnemy=true;
		    if(killToInc<8)killToInc=enemies.size()+2;
	    }
    }
}
