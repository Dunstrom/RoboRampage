package board;

/**
 *<h1>BoardListener</h1><br>
 *     <p>A interface for classes who wants to know when the board changes in some way.</p>
 */
public interface BoardListener {

    /**
     * <h1>boardChanged</h1><br>
     *     <p>Method to be implemented and called every time the board changes.</p>
     */
    public void boardChanged();

}
