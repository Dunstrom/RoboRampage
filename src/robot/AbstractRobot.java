package robot;


import java.awt.*;
import java.util.*;

/**
 * Abstract class for all robots in the game.
 */
public abstract class AbstractRobot implements Robot
{
    protected int x;
    protected int y;
    protected char orientation;//Orientation 'N' = UP (NORTH), 'S' = DOWN (SOUTH), 'W' = LEFT (WEST), 'E' = RIGHT (EAST)
    protected int hitPoints;

    //For the collisionhandling
    protected int tempX;
    protected int tempY;

    protected Queue<Runnable> programmedMoves;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTempY() {
        return tempY;
    }

    public char getOrientation() {
        return orientation;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getTempX() {
        return tempX;
    }

    public Runnable getNextMove() {
        return programmedMoves.poll();
    }

    public AbstractRobot(final int x, final int y, final char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        programmedMoves = new LinkedList<Runnable>();
    }

    /**
     * <h1>movable</h1><br>
     *     <p>checks if robot can be moved</p>
     * @return true if robot is movable
     */
    @Override public boolean movable() {return false;}

    /**
     * <h1>place</h1><br>
     *     <p>changes the robots position</p>
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    @Override public void place(final int newX, final int newY) {

        x = newX;
        y = newY;

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
        g.fillRect(x, y, 20, 20);

        g.setColor(Color.BLACK);
        switch (orientation) {
            case 'N':
                g.drawString("N", x, y);
                break;
            case 'S':
                g.drawString("S", x, y);
                break;
            case 'W':
                g.drawString("W", x, y);
                break;
            case 'E':
                g.drawString("E", x, y);
                break;
        }
    }
}

