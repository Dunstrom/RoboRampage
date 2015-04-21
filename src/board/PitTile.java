package board;

import entity.AbstractRobot;

import java.awt.*;

/**
 * Instantly kills any robots that goes over it.
 */
public class PitTile extends Tile
{
    public PitTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize);
        sprite = loadImage("../Resources/pit.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.kill();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
        g.setColor(borderColor);
        g.drawRect(x, y, tileSize, tileSize);
    }
}
