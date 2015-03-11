package board;

import java.awt.*;

/**
 *The basic empty tile that don't do much.
 */
public class EmptyTile extends AbstractTile{

    public EmptyTile(int x, int y){
        super(x, y);
    }

    /**
     * @return boolean false because EmptyTile don't block anything.
     */
    public boolean isBlocking() {
        return false;
    }

    /**
     * Draws nothing else then super does because the tile is empty.
     * @param g a Graphics object.
     */
    public void draw(Graphics g){
        super.draw(g);
    }

    /**
     * Empty tiles do nothing
     */
    public void update() {

    }

}
