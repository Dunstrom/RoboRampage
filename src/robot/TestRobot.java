package robot;

import board.AbstractTile;

/**
 * Class to create test robots
 */
public class TestRobot extends AbstractRobot {

    public TestRobot(final int x, final int y, final char orientation){
	super(x,y, orientation);
	hitPoints = 10;

    }

	/**
	 * Adds a moveforwardOne to the queue of pre programmed moves.
	 */
    public void addMoveForwardOne(){
	programmedMoves.add(moveForward);
    }

    Runnable moveForward = new Runnable() {
	public void run() {
	    switch (orientation){
	    	    case 'N':
			tempX = x;
			tempY = y + AbstractTile.getTileSize();
	    		break;
	    	    case 'S':
			tempX = x;
			tempY = y - AbstractTile.getTileSize();
	    		break;
	    	    case 'W':
			tempX = x - AbstractTile.getTileSize();
			tempY = y;
	    		break;
	    	    case 'E':
			tempX = x + AbstractTile.getTileSize();
			tempY = y;
	    		break;
	    	}
	}
    };
    
    /**
     * Turns the robot left 90 degrees
     */
    public void turnLeft() {
	switch (orientation) {
	    case 'N':
		place(0, 1);
		break;
	    case 'S':
		place(0, -1);
		break;
	    case 'W':
		place(-1, 0);
		break;
	    case 'E':
		place(1, 0);
		break;
	}
    }

    /**
     * Turns the robot right 90 degrees
     */
    public void turnRight() {
	switch (orientation) {
	    case 'N':
		orientation = 'E';
		break;
	    case 'E':
		orientation = 'S';
		break;
	    case 'S':
		orientation = 'W';
		break;
	    case 'W':
		orientation = 'N';
		break;
	}
    }

}
