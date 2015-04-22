package board;

import entity.AbstractRobot;
import entity.Orientation;


public class RotateLeftTile extends Tile
{
    public RotateLeftTile(final int x, final int y, int tileSize) {
    	super(x, y, tileSize, "../Resources/rotateWest.png");
    }

    @Override public boolean willMoveRobot() {
    	super.willMoveRobot();
    	return true;
    }

    @Override public void update(final AbstractRobot robot) {
    	super.update(robot);

	switch (robot.getOrientation()) {
	    case NORTH:
		robot.setOrientation(Orientation.WEST);
		break;
	    case WEST:
		robot.setOrientation(Orientation.SOUTH);
		break;
	    case SOUTH:
		robot.setOrientation(Orientation.EAST);
		break;
	    case EAST:
		robot.setOrientation(Orientation.NORTH);
		break;
    	}
    }
}
