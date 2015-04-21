package board;

import entity.AbstractRobot;
/**
 * Impassable tile. No robots or weapons can pass through.
 */
import java.awt.Color;

public class WallTile extends Tile
{

    public WallTile(int x, int y) {
	super(x, y, Color.WHITE);
    }

    @Override boolean isBlocking() {
	super.isBlocking();
	return true;
    }
}
