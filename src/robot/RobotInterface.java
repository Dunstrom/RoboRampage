package robot;

import java.awt.*;

/**
 * <h1>RobotInterface</h1><br>
 *     <p>Interface for the robots</p>
 */
public interface RobotInterface
{

    /**
     * <h1>movable</h1><br>
     *     <p>checks if robot can be moved</p>
     * @return true if robot is movable
     */
    boolean movable();

    /**
     * <h1>move</h1><br>
     *     <p>changes the robots position</p>
     * @param xChange amount of tiles to move on the x-axis. pos. x -> right, neg. x -> left.
     * @param yChange amount of tiles to move on the y-axis. pos. y -> down, neg. y -> up.
     */
    void move(int xChange, int yChange);

    /**
     * <h1>attack</h1><br>
     *     <p>Makes the robot attack</p>
     */
    void attack();

    /**
     * <h1>draw</h1><br>
     *     <p>Draws the robot on the screen</p>
     * @param g a Graphics object
     */
    void draw(Graphics g);

}
