package board;

import java.awt.*;

/**
 * A interface for the tiles implemented by the AbstractTile
 */
public interface Tile {

    /**
     * A method that should be implemented to draw the tile.
     * @param g a Graphics object.
     */
    public void draw(Graphics g);

    /**
     * A method tat should be implemented to update the tile.
     */
    public void update();

}
