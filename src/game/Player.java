package game;

import robot.Orientation;

public class Player {

    private String name;
    private int startCol;
    private int startRow;
    private Orientation orientation = Orientation.EAST;

    public String getName() {
	return name;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Player(String name, int startCol, int startRow) {

	this.name = name;
        this.startCol = startCol;
        this.startRow = startRow;

    }

}
