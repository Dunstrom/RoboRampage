package board;

import entity.AbstractRobot;

import java.awt.*;

/**
 * Instantly kills any robots that goes over it.
 */
public class PitTile extends Tile
{
    public PitTile(final int x, final int y) {
	super(x, y, Color.BLACK);
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.kill();
    }
}
