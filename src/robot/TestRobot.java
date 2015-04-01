package robot;

import board.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * Class to create test robots
 */
public class TestRobot extends AbstractRobot {

    private BufferedImage sprite;
    private final static int HEALTH = 10;

    public TestRobot(final int x, final int y, final Orientation orientation, String name) {
	super(x, y, orientation, name, HEALTH);
	sprite = loadImage("../Resources/Robot.png");

	//Setup player interface
	JButton moveForwardButton = new JButton();
	JButton turnLeftButton = new JButton();
	JButton turnRightButton = new JButton();

	moveForwardButton.setAction(new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			    addProgrammedMove(moveForwardOne);
		    }
	    });

	turnLeftButton.setAction(new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			    addProgrammedMove(turnLeft);
		    }
	    });

	turnRightButton.setAction(new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			    addProgrammedMove(turnRight);
		    }
	    });

	moveForwardButton.setText("Forward");
	turnLeftButton.setText("Turn Left");
	turnRightButton.setText("Turn Right");

	moveButtonPanel.add(moveForwardButton);
	moveButtonPanel.add(turnLeftButton);
	moveButtonPanel.add(turnRightButton);
    }

    /**
     * Runnable that moves the robot one step forward.
     */
    AbstractMove moveForwardOne = new AbstractMove() {
	@Override
	public void run() {
	    switch (orientation) {
		case NORTH:
		    tempX = x;
		    tempY = y - Tile.TILE_SIZE;
		    break;
		case SOUTH:
		    tempX = x;
		    tempY = y + Tile.TILE_SIZE;
		    break;
		case WEST:
		    tempX = x - Tile.TILE_SIZE;
		    tempY = y;
		    break;
		case EAST:
		    tempX = x + Tile.TILE_SIZE;
		    tempY = y;
		    break;
	    }
	}
	@Override
	public String display() {
		    return "Forward 1";
	    }
    };

    /**
     * Runnable that turns the robot left 90 degrees
     */
    AbstractMove turnLeft = new AbstractMove() {
	@Override
	public void run() {
	    switch (orientation) {
		case NORTH:
		    orientation = Orientation.WEST;
		    tempX = x;
		    tempY = y;
		    break;
		case SOUTH:
		    orientation = Orientation.EAST;
		    tempX = x;
		    tempY = y;
		    break;
		case WEST:
		    orientation = Orientation.SOUTH;
		    tempX = x;
		    tempY = y;
		    break;
		case EAST:
		    orientation = Orientation.NORTH;
		    tempX = x;
		    tempY = y;
		    break;
	    }
	}
	@Override
	public String display() {
		    return "Turn Left";
	    }
    };


    /**
     * Runnable that turns the robot right 90 degrees
     */
    AbstractMove turnRight = new AbstractMove() {
	@Override
	public void run() {
	    switch (orientation) {
		case NORTH:
		    orientation = Orientation.EAST;
		    tempX = x;
		    tempY = y;
		    break;
		case EAST:
		    orientation = Orientation.SOUTH;
		    tempX = x;
		    tempY = y;
		    break;
		case SOUTH:
		    orientation = Orientation.WEST;
		    tempX = x;
		    tempY = y;
		    break;
		case WEST:
		    orientation = Orientation.NORTH;
		    tempX = x;
		    tempY = y;
		    break;
	    }
	}
	@Override
	public String display() {
		    return "Turn Right";
	    }

    };


    /**
     * Draws the robot by drawing it's sprite rotated in the right direction. Completely overrides supermethod since we don't want to draw that at all.
     * @param g a Graphics object
     */
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

	drawImageRotated(sprite, degreesToRotate, x, y, g2d);

    }

}
