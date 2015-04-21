package board;

import entity.AbstractRobot;

import java.awt.*;

public class FireTile extends Tile
{
    public FireTile(final int x, final int y, int tileSize) {
	super(x, y, tileSize);
        sprite = loadImage("../Resources/lavapit.png");
    }

    @Override public void update(final AbstractRobot robot) {
	super.update(robot);
	robot.takeDamage(1);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
        g.setColor(borderColor);
        g.drawRect(x, y, tileSize, tileSize);
    }
}
