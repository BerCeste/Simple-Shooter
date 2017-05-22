/**
 *              Created by Batuhan on 6.05.2017.
 */

final class AI {
    private Player player;

    private Enemy enemy = Main.enemy;

    AI(Player player) {
        this.player = player;
    }

    void moveToPlayer() {
        if(!horizontallyEqual()) {
            enemy.setVelY(0);

            if(enemy.getX() < player.getX() - 25) {
                enemy.setVelX(Moveable.MOVEMENT);
                enemy.direction = "+x";
            } else if(enemy.getX() > player.getX() + 25) {
                enemy.setVelX(-Moveable.MOVEMENT);
                enemy.direction = "-x";
            } else {
                enemy.setVelX(0);
            }
        } else {
            enemy.setVelX(0);

            if(enemy.getY() < player.getY() - 25) {
                enemy.setVelY(Moveable.MOVEMENT);
                enemy.direction = "+y";
            } else if(enemy.getY() > player.getY() + 25) {
                enemy.setVelY(-Moveable.MOVEMENT);
                enemy.direction = "-y";
            } else {
                enemy.setVelY(0);
            }
        }
    }

    private boolean horizontallyEqual() {
        return enemy.getX() <= player.getX() + 25 && enemy.getX() >= player.getX() - 25;
    }
}
