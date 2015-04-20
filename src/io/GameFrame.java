package io;

import board.Board;

import java.awt.*;
import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class GameFrame extends JFrame
{

    private JPanel currentPlayerInterface;
    private BoardComponent boardComponent;

    /**
     * Sets the activePlayer to a a entity.robot. The activePlayer is the one who's interface is displayed.
     * @param nextPlayerInterface the player who's turn it is next.
     */
    public void setActivePlayer(JPanel nextPlayerInterface) {
        remove(currentPlayerInterface);
        currentPlayerInterface = nextPlayerInterface;
        add(currentPlayerInterface, BorderLayout.PAGE_END);
       	pack();

    }

    public GameFrame(Board board, JPanel playerInterface) {

        super("RoboRampage");

        currentPlayerInterface = playerInterface;

        boardComponent = new BoardComponent(board);
        board.addBoardListener(boardComponent);
        setLayout(new BorderLayout());
        add(currentPlayerInterface, BorderLayout.PAGE_END);
        add(boardComponent,BorderLayout.CENTER);

       	pack();
        requestFocus();
        setVisible(true);
    }

    /**
     * Repaints the playerInterface
     */
    public void repaintPlayerInterface() {
        repaint(0, 0, boardComponent.getHeight(), getWidth(), getHeight());
    }

}
