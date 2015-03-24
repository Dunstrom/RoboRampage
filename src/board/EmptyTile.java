package board;

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
     * Empty tiles do nothing
     */
    public void update() {

    }

}
