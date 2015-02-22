package board;

/**
 * Keeps tracks of all the tiles on the board.
 */
public class Board {

    private AbstractTile[][] tiles;
    private int width;
    private int height;

    public Board(int width, int height){

        this.width = width;
        this.height = height;
        initBoard(width, height);

    }

    /**
     * <h1>initBoard</h1><br>
     *     <p>Initialize the board with all empty tiles</p>
     * @param width
     * @param height
     */
    private void initBoard(int width, int height) {

        tiles = new AbstractTile[height][width];

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++){

                tiles[row][col] = new EmptyTile(col * AbstractTile.getTileSize(), row * AbstractTile.getTileSize());

            }

        }

    }

    /**
     * <h1>updateTiles</h1><br>
     *     <p>Goes through all the tiles on the board and updates them. So the tiles for example, shoot, moves or kills the robots</p>
     */
    public void updateTiles() {

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++) {

                tiles[row][col].update();

            }

        }

    }

}
