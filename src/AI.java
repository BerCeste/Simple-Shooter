import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *              Created by Batuhan on 6.05.2017.
 */


abstract class AI extends GameCreature{
    private Player player=Main.player;

	AI(BufferedImage image){
		super(image);
	}


   /* AI(Player player) {
        this.player = player;
    }*/

	void moveToPlayer() {
		if(!horizontallyEqual()) {
			setVelY(0);

			if(getX() < player.getX() - 25) {
				setVelX(Moveable.MOVEMENT);
				direction = "+x";
			} else if(getX() > player.getX() + 25) {
				setVelX(-Moveable.MOVEMENT);
				direction = "-x";
			} else {
				setVelX(0);
			}
		} else {
			setVelX(0);

			if(getY() < player.getY() - 25) {
				setVelY(Moveable.MOVEMENT);
				direction = "+y";
			} else if(getY() > player.getY() + 25) {
				setVelY(-Moveable.MOVEMENT);
				direction = "-y";
			} else {
				setVelY(0);
			}
		}
	}

	private boolean horizontallyEqual() {
		return getX() <= player.getX() + 25 && getX() >= player.getX() - 25;
	}

	@Override
	public void tick(){

		setX(getX() + getVelX());
		setY(getY() + getVelY());
		moveToPlayer();
		getImageDir();
	}

	abstract void getImageDir();

	public abstract void render(Graphics g);
}
