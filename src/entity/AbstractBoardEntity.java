package entity;


import io.OutputEntity;

/**
 * A abstract class for every object pressent on the board.
 */
public abstract class AbstractBoardEntity extends OutputEntity implements BoardEntity {


    // Position
    protected int x;
    protected int y;
    protected Orientation orientation;

    protected AbstractBoardEntity(final int x, final Orientation orientation, final int y) {
	this.x = x;
	this.orientation = orientation;
	this.y = y;
    }

    /**
     * changes the robots position
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    public void place(final int newX, final int newY) {

        x = newX;
        y = newY;

    }

    public boolean collide(BoardEntity obj) {
        if(x == obj.getX() && y == obj.getY()) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation){
	this.orientation = orientation;
    }
}
