package io;

import board.AbstractTile;
import board.Board;
import board.BoardListener;

import javax.swing.*;
import java.awt.*;

/**
 *<h1>BoardComponent</h1><br>
 *     <p>Handles the input and output for the board.</p>
 */
public class BoardComponent extends JComponent implements BoardListener {

    private Board board;

    public BoardComponent(Board board) {

        this.board = board;

    }

    /**
     * <h1>getPreferredSize</h1><br>
     *     <p>Takes the board width, height and the tileSize and makes a Dimension object.</p>
     * @return a Dimension object.
     */
    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        int width = board.getWidth() * AbstractTile.getTileSize();
        int height = board.getHeight() * AbstractTile.getTileSize();
        return new Dimension(width, height);
    }

    /**
     * <h1>paintComponent</h1><br>
     *     <p>Overrides JComponents paintComponent and draws the board.</p>
     * @param g a Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        board.draw(g);

    }

    /**
     * <h1>boardChanged</h1><br>
     *     <p>Repaints the board whenever it changes.</p>
     */
    public void boardChanged() {
        repaint();
    }

}
