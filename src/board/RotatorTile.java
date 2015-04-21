package board;

import entity.AbstractRobot;
import entity.Orientation;

import java.awt.*;

public class RotatorTile extends Tile
{
    private Orientation orientation;

    public RotatorTile(final int x, final int y, Orientation orientation, int tileSize) {
		super(x, y, tileSize);
		this.orientation = orientation;
		assert orientation == Orientation.NORTH || orientation == Orientation.SOUTH;
		if(orientation == Orientation.EAST) {
			sprite = loadImage("../Resources/rotateEast.png");
		}else {
			sprite = loadImage("../Resources/rotateWest.png");
		}
    }

    @Override public boolean willMoveRobot() {
	super.willMoveRobot();
	return true;
    }

	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}

	@Override public void update(final AbstractRobot robot) {
	super.update(robot);
	if(orientation == Orientation.EAST) {
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
	else{
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
}
