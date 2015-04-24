package io;

import board.Board;
import game.DoneListener;

import java.awt.*;
import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class GameFrame extends JFrame
{

    private InterfaceComponent currentPlayerInterface;
    private BoardComponent boardComponent;

    /**
     * Sets the activePlayer to a a entity.robot. The activePlayer is the one who's interface is displayed.
     * @param nextPlayerInterface the player who's turn it is next.
     */
    public void setActivePlayer(InterfaceComponent nextPlayerInterface) {
        remove(currentPlayerInterface);
        currentPlayerInterface = nextPlayerInterface;
        add(currentPlayerInterface, BorderLayout.PAGE_END);
       	pack();

    }

    public GameFrame() {
        super("RoboRampage");

        currentPlayerInterface = null;
        boardComponent = null;
        setLayout(new BorderLayout());

       	pack();
        requestFocus();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Repaints the playerInterface
     */
    public void repaintPlayerInterface() {
        repaint(0, 0, boardComponent.getHeight(), getWidth(), getHeight());
    }

    public void runGameScreen(Board board, InterfaceComponent playerInterface) {
        currentPlayerInterface = playerInterface;
        boardComponent = new BoardComponent(board);

        board.addBoardListener(boardComponent);
        resetFrame();
        add(currentPlayerInterface, BorderLayout.PAGE_END);
        add(boardComponent, BorderLayout.CENTER);
        pack();

    }

    public void runwinScreen(String winner, DoneListener[] listeners) {
        WinScreenComponent winScreenComponent = new WinScreenComponent(winner);
        resetFrame();
        add(winScreenComponent, BorderLayout.CENTER);
        for (DoneListener listener : listeners) {
            winScreenComponent.addListener(listener);
        }
        pack();
    }

    private void resetFrame() {
        getContentPane().removeAll();
    }

}
