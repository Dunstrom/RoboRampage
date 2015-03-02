package robot;

/**
 * <h1>TestRobot</h1><br>
 *     <p>Class to create test robots</p>
 */
public class TestRobot extends AbstractRobot {

    public TestRobot(final int x, final int y, final char orientation){
	super(x,y, orientation);
	hitPoints = 10;
    }

    /**
     * <h1>moveForwardOne</h1><br>
     *     <p>Makes the robot move one step in the direction it is facing</p>
     */
    public void moveForwardOne(){
	switch (orientation){
	    case 'N':
		move(0,1);
		break;
	    case 'S':
		move(0,-1);
		break;
	    case 'W':
		move(-1,0);
		break;
	    case 'E':
		move(1,0);
		break;
	}
    }
    
    /**
     * <h1>turnLeft</h1><br>
     *     <p>Turns the robot left 90 degrees</p>
     */
    public void turnLeft() {
	switch (orientation) {
	    case 'N':
		move(0, 1);
		break;
	    case 'S':
		move(0, -1);
		break;
	    case 'W':
		move(-1, 0);
		break;
	    case 'E':
		move(1, 0);
		break;
	}
    }

    /**
     * <h1>turnRight</h1><br>
     *     <p>Turns the robot right 90 degrees</p>
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
