package board;

import java.awt.*;

/**
 * An abstract class that implements the most basic methods and fields every tile needs
 */
public abstract class AbstractTile implements Tile{

    protected final static int TILE_SIZE = 50;
    protected int x;
    protected int y;
    protected Color backgroundColor = Color.GRAY;
    protected Color borderColor = Color.WHITE;

    /**
     * @return boolean true if the tile is blocking if not, false.
     */
    abstract boolean isBlocking();

    public static int getTileSize() {
        return TILE_SIZE;
    }

    protected AbstractTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the outline and background of every tile and if a robot ocupies it tells the robot to draw itself
     */
    public void draw(Graphics g) {

        //Draws the background of the tile
        g.setColor(backgroundColor);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        //Draws the border of the tiles
        g.setColor(borderColor);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);

    }

}

