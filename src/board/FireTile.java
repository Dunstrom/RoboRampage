package board;

import entity.AbstractRobot;



public class FireTile extends Tile
{
    public FireTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize, "../Resources/lavapit.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.takeDamage(1);
    }
}
