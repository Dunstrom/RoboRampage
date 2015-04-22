package board;

import entity.AbstractRobot;
import entity.Orientation;


public class RotateRightTile extends Tile
{
    public RotateRightTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize, "../Resources/rotateEast.png");
    }

    @Override public boolean willMoveRobot() {
	super.willMoveRobot();
	return true;
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);

	switch (robot.getOrientation()) {
	    case NORTH:
		robot.setOrientation(Orientation.EAST);
		break;
	    case EAST:
		robot.setOrientation(Orientation.SOUTH);
		break;
	    case SOUTH:
		robot.setOrientation(Orientation.WEST);
		break;
	    case WEST:
		robot.setOrientation(Orientation.NORTH);
		break;
	}
    }
}


