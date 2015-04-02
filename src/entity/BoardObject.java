package entity;

import java.awt.Graphics;

/**
 * Interface for all objects present on the board
 */
public interface BoardObject {

    public int getX();

    public int getY();

    public void draw(Graphics g);

    public boolean collide(BoardObject obj);

}
