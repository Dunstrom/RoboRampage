package board;

import java.awt.*;

/**
 * <h1>AbstractTile</h1><br>
 *     <p>An abstract class that implements the most basic methods and fields every tile needs</p>
 */
public abstract class AbstractTile implements Tile{

    private final static int tileSize = 30;
    private int x;
    private int y;
    private Color backgroundColor = Color.GRAY;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public AbstractTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <h1>draw()</h1><br>
     *     <p>Draws the outline and background of every tile</p>
     */
    public void draw(Graphics g) {
        //Todo: Make every tile draw it's outlines for a better graphical appearance.

        //Draws the background of the tile
        g.setColor(backgroundColor);
        g.fillRect(getX(), getY(), getTileSize(), getTileSize());
    }

    public void update() {

    }

}
