package entity;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Created by Hampus on 2015-04-01.
 */
public class Flag extends AbstractBoardObject {

    final static int WIDTH = 20;
    final static int HEIGHT = 15;
    private Color color;

    public Flag(int x, int y, Color color) {
        super(x, Orientation.NORTH, y);
        this.color = color;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, WIDTH, HEIGHT);

    }

}
