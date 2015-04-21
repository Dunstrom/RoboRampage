package board;

import entity.AbstractRobot;

import java.awt.Color;

public class RepairTile extends Tile
{
    public RepairTile(final int x, final int y) {
	super(x, y, Color.GREEN);
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.heal(1);
    }
}
