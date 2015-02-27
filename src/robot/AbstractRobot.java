package robot;


import java.awt.*;

/**
 * <h>AbstractRobot</h><br>
 *     <p>Abstract class for all robots in the game.</p>
 */
public abstract class AbstractRobot implements Robot
{

    protected int x;
    protected int y;
    protected char orientation;//Orientation 'N' = UP (NORTH), 'S' = DOWN (SOUTH), 'W' = LEFT (WEST), 'E' = RIGHT (EAST)

    //For the collisionhandling
    private int tempX;
    private int tempY;

    public AbstractRobot(final int x, final int y, final char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

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

        tempX = x + xChange;
        tempY = y + yChange;

        if (movable()){
            x = tempX;
            y = tempY;
        }


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

        g.setColor(Color.BLUE);
        g.fillRect(x*30+5, y*30+5, 20, 20);

        g.setColor(Color.BLACK);
        switch (orientation) {
            case 'N':
                g.drawString("N", x*30+5, y*30+5);
                break;
            case 'S':
                g.drawString("S", x*30+5, y*30+5);
                break;
            case 'W':
                g.drawString("W", x*30+5, y*30+5);
                break;
            case 'E':
                g.drawString("E", x*30+5, y*30+5);
                break;

        }


    }
}

