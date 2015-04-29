package game;

import board.SettingsFailiureException;
import entity.Orientation;
import io.Settings;

/**
 * A class that holds all the information about the player choosen in the menu and generated in the playerfactory
 */
public class Player {

    private String name;
    private String spriteFileName;
    private int startX;
    private int startY;
    private Orientation orientation;
    private String robotType;

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public String getRobotType() {
        return robotType;
    }

    public String getName() {
	return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getSpriteFileName() {
        return spriteFileName;
    }

    public Player(String name, int x, int y, String spriteFileName, String robotType, Settings settings) throws SettingsFailiureException {

        this.name = name;
        this.spriteFileName = spriteFileName;
        this.robotType = robotType;
        startX = x;
        startY = y;
        int middleOfBoard = (settings.getBoardWidth() * settings.getTileSize())/2;
        if(x < middleOfBoard ) {
            orientation = Orientation.EAST;
        } else {
            orientation = Orientation.WEST;
        }

    }

}
