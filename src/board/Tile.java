package board;

import java.awt.*;

/**
 * <h1>Tile</h1><br>
 *     <p>A interface for the tiles implemented by the AbstractTile</p>
 */
public interface Tile {

    /**
     * <h1>draw</h1><br>
     *     <p>A method that should be implemented to draw the tile.</p>
     * @param g a Graphics object.
     */
    public void draw(Graphics g);

    /**
     * <h1>update</h1><br>
     *     <p>A method tat should be implemented to update the tile.</p>
     */
    public void update();

}
