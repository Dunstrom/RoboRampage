package board;

import java.awt.*;

/**
 * An abstract class that implements the most basic methods and fields every tile needs
 */
public abstract class AbstractTile implements Tile{

    private final static int TILE_SIZE = 50;
    private int x;
    private int y;
    private Color backgroundColor = Color.GRAY;
    private Color borderColor = Color.WHITE;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    /**
     * Abstract tile does nothing.
     */
    @Override
    public abstract void update();

}

