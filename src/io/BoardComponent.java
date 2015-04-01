package io;

import board.Tile;
import board.Board;
import board.BoardListener;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the input and output for the board.
 */
public class BoardComponent extends JComponent implements BoardListener {

    private Board board;

    public BoardComponent(Board board) {

        this.board = board;
        board.addBoardListener(this);

    }

    /**
     * Takes the board width, height and the tileSize and makes a Dimension object.
     * @return a Dimension object.
     */
    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        int width = board.getWidth() * Tile.TILE_SIZE;
        int height = board.getHeight() * Tile.TILE_SIZE;
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
        paintImmediately(0,0,board.getWidth() * Tile.TILE_SIZE, board.getHeight() * Tile.TILE_SIZE);
    }

}
