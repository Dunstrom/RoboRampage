package io;

import board.Board;

import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class Frame extends JFrame{

    private IOComponent iOComponent;

    public Frame(Board board) {

        iOComponent = new IOComponent(board);
        add(iOComponent);

    }

}
