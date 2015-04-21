package board;

import entity.AbstractRobot;
import entity.Orientation;

import java.awt.Color;

public class RotatorTile extends Tile
{
    private Orientation orientation;

    public RotatorTile(final int x, final int y, Orientation orientation) {
	super(x, y, Color.BLUE);
	this.orientation = orientation;
    }

    @Override public boolean willMoveRobot() {
	super.willMoveRobot();
	return true;
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
    }
}
