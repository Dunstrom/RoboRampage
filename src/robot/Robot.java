package robot;

import java.awt.*;

/**
 * Interface for the robots
 */
public interface Robot {

    /**
     * Sets up the interface in which the player interacts with the game.
     * @param name Name of the player using the interface
     */
    void setupPlayerInterface(String name);

    /**
     * changes the robots position
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    void place(int newX, int newY);

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
