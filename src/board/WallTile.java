package board;

import entity.AbstractRobot;
import game.Settings;
/**
 * Impassable tile. No robots or weapons can pass through.
 */
import java.awt.*;
import java.awt.image.BufferedImage;

public class WallTile extends Tile {

    public WallTile(int x, int y, int tileSize) {
        super(x, y, tileSize);
        sprite = loadImage("../Resources/wall.png");
    }

    @Override
    boolean isBlocking() {
        super.isBlocking();
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }
}
