package board;

import entity.AbstractRobot;

import java.awt.*;

public class RepairTile extends Tile
{
    public RepairTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize);
        sprite = loadImage("../Resources/repairTile.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.heal(1);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
        g.setColor(borderColor);
        g.drawRect(x, y, tileSize, tileSize);
    }
}
