package board;

/**
 * A interface for classes who wants to know when the board changes in some way.
 */
public interface BoardListener {

    /**
     * Method to be implemented and called every time the board changes.
     */
    void boardChanged();

}
