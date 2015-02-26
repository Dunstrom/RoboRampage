package robot;

import java.awt.*;

/**
 * <h>AbstractRobot</h><br>
 *     <p>Abstract class for all robots in the game.</p>
 */
public class AbstractRobot implements RobotInterface {

    private int x;
    private int y;
    private char orientation;//Orientation 'N' = UP (NORTH), 'S' = DOWN (SOUTH), 'W' = LEFT (WEST), 'E' = RIGHT (EAST)

    //For the collisionhandling
    private int tempX;
    private int tempY;

    /**
     * <h1>movable</h1><br>
     *     <p>checks if robot can be moved</p>
     * @return true if robot is movable
     */
    @Override public boolean movable() {
	return false;
    }

    /**
     * <h1>move</h1><br>
     *     <p>changes the robots position</p>
     * @param xChange amount of tiles to move on the x-axis. pos. x -> right, neg. x -> left.
     * @param yChange amount of tiles to move on the y-axis. pos. y -> down, neg. y -> up.
     */
    @Override public void move(final int xChange, final int yChange) {

    }

    /**
     * <h1>attack</h1><br>
     *     <p>Makes the robot attack</p>
     */
    @Override public void attack() {

    }

    /**
     * <h1>draw</h1><br>
     *     <p>Draws the robot on the screen</p>
     * @param g a Graphics object
     */
    @Override public void draw(final Graphics g) {

    }
}

