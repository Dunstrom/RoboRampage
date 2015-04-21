package board;

import entity.AbstractRobot;

import java.awt.Color;

public class FireTile extends Tile
{
    public FireTile(final int x, final int y) {
	super(x, y, Color.ORANGE);
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.takeDamage(1);
    }
}
