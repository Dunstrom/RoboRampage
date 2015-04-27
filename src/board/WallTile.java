package board;

/**
 * Impassable tile. No robots or weapons can pass through.
 */
public class WallTile extends Tile {

    public WallTile(int x, int y, int tileSize) {
        super(x, y, tileSize, "../Resources/wall.png");
    }

    @Override
    boolean isBlocking() {
        super.isBlocking();
        return true;
    }

}
