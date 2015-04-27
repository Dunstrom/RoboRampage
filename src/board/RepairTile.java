package board;

import entity.AbstractRobot;

/** A tile that repairs the robot on touch */
public class RepairTile extends Tile
{
    public RepairTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize, "../Resources/repairTile.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.heal(1);
    }
}
