package board;

import entity.AbstractRobot;

/**
 * Instantly kills any robots that goes over it.
 */
public class PitTile extends Tile
{
    public PitTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize, "../Resources/Pit.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.kill();
    }
}
