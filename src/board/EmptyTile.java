package board;

import java.awt.*;

/**
 *<h1>EmptyTile</h1><br>
 *     <p>The basic empty tile that don't do much.</p>
 */
public class EmptyTile extends AbstractTile{

    public EmptyTile(int x, int y){
        super(x, y);
    }

    /**
     * <h1>draw</h1><br>
     *     <p>Draws nothing else then super does because the tile is empty.</p>
     * @param g a Graphics object.
     */
    public void draw(Graphics g){
        super.draw(g);
    }

    /**
     * <h1>update</h1><br>
     *     <p>Empty tiles do nothing</p>
     */
    public void update() {

    }

}
