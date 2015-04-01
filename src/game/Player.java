package game;

import entity.Orientation;

/**
 * A class that holds all the information about the player choosen in the menu and generated in the playerfactory
 */
public class Player {

    private String name;
    private String spriteFileName;
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

    public String getSpriteFileName() {
        return spriteFileName;
    }

    public Player(String name, int startCol, int startRow, String spriteFileName) {

	    this.name = name;
        this.startCol = startCol;
        this.startRow = startRow;
        this.spriteFileName = spriteFileName;


    }

}
