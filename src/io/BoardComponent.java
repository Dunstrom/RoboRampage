package io;

import board.Board;
import board.BoardListener;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Dimension;

/**
 * Handles the input and output for the board.
 */
public class BoardComponent extends JComponent implements BoardListener {

    private Board board;
    private int tileSize;

    public BoardComponent(Board board) {

        this.board = board;
        tileSize = board.getTileSize();
        board.addBoardListener(this);

    }

    /**
     * Takes the board width, height and the tileSize and makes a Dimension object.
     * @return a Dimension object.
     */
    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        int width = board.getWidth() * tileSize;
        int height = board.getHeight() * tileSize;
        return new Dimension(width, height);
    }

    /**
     * Overrides JComponents paintComponent and draws the board.
     * @param g a Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);

    }

    /**
     * Repaints the board immediately whenever it changes.
     */
    public void boardChanged() {
        paintImmediately(0,0,board.getWidth() * tileSize, board.getHeight() * tileSize);
    }

}
