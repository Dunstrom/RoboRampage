package entity;

import java.awt.Graphics;
import java.awt.Color;

/** A flag is a Boardobject that the robots are supposed tp gather up in order to win the game. Has a unique color and should only be created by the FlagFactory. */
public class Flag extends AbstractBoardObject {

    /** The width of a flag in pixels. */
    public final static int WIDTH = 20;
    /** The height of a flag in pixels. */
    public final static int HEIGHT = 15;
    private Color color;
    private Color borderColor = Color.WHITE;

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Flag(int x, int y, Color color) {
        super(x, Orientation.NORTH, y);
        this.color = color;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(borderColor);
        g.drawRect(x, y, WIDTH, HEIGHT);

    }

}
