package robot;

import java.awt.*;

/**
 * Interface for the robots
 */
public interface Robot
{

    /**
     * checks if robot can be moved
     * @return true if robot is movable
     */
    boolean movable();

    /**
     * changes the robots position
     * @param xChange amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param yChange amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    void place(int xChange, int yChange);

    /**
     * Makes the robot attack
     */
    void attack();

    /**
     * Draws the robot on the screen
     * NOTE: This is a very temporary draw method for testing, sould be reimplemented later
     * @param g a Graphics object
     */
    void draw(Graphics g);

}
