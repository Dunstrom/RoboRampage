package io;

import board.Board;

import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class GameFrame extends JFrame{

    private BoardComponent boardComponent;

    public GameFrame(Board board) {

        super("RoboRampage");

        boardComponent = new BoardComponent(board);
        add(boardComponent);

        setSize(boardComponent.getPreferredSize());
        requestFocus();
        setVisible(true);
        pack();

    }

}
