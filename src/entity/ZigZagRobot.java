package entity;

import board.BoardNotFoundException;
import board.SettingsFailiureException;
import io.Settings;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Created by Hampus on 2015-04-21.
 */
public class ZigZagRobot extends AbstractRobot
{

    private final static int HEALTH = 10;

    public ZigZagRobot(int x, int y, Orientation orientation, String name, String spriteFileName, Settings settings)
	    throws BoardNotFoundException, SettingsFailiureException
    {
	super(x, y, orientation, name, HEALTH, settings, "../Resources/" + spriteFileName);

	Button turnButton = new Button()
	{
	    @Override public String display() {
		return "Turn";
	    }

	    @Override public void run() {
		addProgrammedMove(turn);
	    }
	};

	Button zagRightButton = new Button()
	{
	    @Override public String display() {
		return "Zag Right";
	    }

	    @Override public void run() {
		addProgrammedMove(zagRight);
	    }
	};

	Button zagLeftButton = new Button()
	{
	    @Override public String display() {
		return "Zag Left";
	    }

	    @Override public void run() {
		addProgrammedMove(zagLeft);
	    }
	};

	playerInterface.addMove(turnButton);
	playerInterface.addMove(zagLeftButton);
	playerInterface.addMove(zagRightButton);

    }


    private Move turn = new Move()
    {
	@Override public void execute() {
	    switch (orientation) {
		case NORTH:
		    tempX = x;
		    tempY = y;
		    orientation = Orientation.SOUTH;
		    break;
		case SOUTH:
		    tempX = x;
		    tempY = y;
		    orientation = Orientation.NORTH;
		    break;
		case WEST:
		    tempX = x;
		    tempY = y;
		    orientation = Orientation.EAST;
		    break;
		case EAST:
		    tempX = x;
		    tempY = y;
		    orientation = Orientation.WEST;
		    break;
	    }
	}

	@Override public String display() {
	    return "Turn";
	}

	@Override public void run() {

	}
    };

    private Move zagRight = new Move()
    {
	@Override public void execute() {
	    switch (orientation) {
		case NORTH:
		    tempX = x + tileSize;
		    tempY = y - tileSize;
		    break;
		case SOUTH:
		    tempX = x - tileSize;
		    tempY = y + tileSize;
		    break;
		case WEST:
		    tempX = x - tileSize;
		    tempY = y - tileSize;
		    break;
		case EAST:
		    tempX = x + tileSize;
		    tempY = y + tileSize;
		    break;
	    }
	}

	@Override public String display() {
	    return "Zag Right";
	}

	@Override public void run() {

	}
    };

    private Move zagLeft = new Move()
    {
	@Override public void execute() {
	    switch (orientation) {
		case NORTH:
		    tempX = x - tileSize;
		    tempY = y - tileSize;
		    break;
		case SOUTH:
		    tempX = x + tileSize;
		    tempY = y + tileSize;
		    break;
		case WEST:
		    tempX = x - tileSize;
		    tempY = y + tileSize;
		    break;
		case EAST:
		    tempX = x + tileSize;
		    tempY = y - tileSize;
		    break;
	    }
	}

	@Override public String display() {
	    return "Zag Left";
	}

	@Override public void run() {

	}
    };

    public void draw(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	final int degreesToRotate;
	switch (orientation) {
	    case NORTH:
		degreesToRotate = 0;
		break;
	    case WEST:
		degreesToRotate = 270;
		break;
	    case SOUTH:
		degreesToRotate = 180;
		break;
	    case EAST:
		degreesToRotate = 90;
		break;
	    default:
		degreesToRotate = 0;
	}

	drawImageRotated(robotSprite, degreesToRotate, getX(), getY(), g2d);
    }

}
