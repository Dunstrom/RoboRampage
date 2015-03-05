package robot;

import board.AbstractTile;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class to create test robots
 */
public class TestRobot extends AbstractRobot {

	public TestRobot(final int x, final int y, final Orientation orientation, String name) {
		super(x, y, orientation, name);
		hitPoints = 10;

	}

	/**
	 * Adds move forward, turn left and turn right buttons to the panel.
	 * @param name Name of the player using the interface
	 */
	@Override
	public void setupPlayerInterface(String name) {
		super.setupPlayerInterface(name);
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

		panel.add(moveForwardButton);
		panel.add(turnLeftButton);
		panel.add(turnRightButton);

	}

	/**
	 * Runnable that moves the robot one step forward.
	 */
	Runnable moveForwardOne = new Runnable() {
		@Override
		public void run() {
			switch (orientation) {
				case NORTH:
					tempX = x;
					tempY = y + AbstractTile.getTileSize();
					break;
				case SOUTH:
					tempX = x;
					tempY = y - AbstractTile.getTileSize();
					break;
				case WEST:
					tempX = x - AbstractTile.getTileSize();
					tempY = y;
					break;
				case EAST:
					tempX = x + AbstractTile.getTileSize();
					tempY = y;
					break;
			}
		}
	};

	/**
	 * Runnable that turns the robot left 90 degrees
	 */
	Runnable turnLeft = new Runnable() {
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
	};


	/**
	 * Runnable that turns the robot right 90 degrees
	 */
	Runnable turnRight = new Runnable() {
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
	};

}
