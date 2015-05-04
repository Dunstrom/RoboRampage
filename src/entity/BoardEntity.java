package entity;

import java.awt.Graphics;

/**
 * Interface for all objects present on the board
 */
public interface BoardEntity {

    int getX();

    int getY();

    void draw(Graphics g);

}
