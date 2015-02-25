package board;

import java.awt.*;

/**
 * <h1>AbstractTile</h1><br>
 *     <p>An abstract class that implements the most basic methods and fields every tile needs</p>
 */
public abstract class AbstractTile implements Tile{

    private final static int TILE_SIZE = 30;
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
     * <h1>draw()</h1><br>
     *     <p>Draws the outline and background of every tile</p>
     */
    public void draw(Graphics g) {

        //Draws the background of the tile
        g.setColor(backgroundColor);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        //Draws the border of the tiles
        g.setColor(borderColor);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    public abstract void update();

}

