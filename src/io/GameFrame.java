package io;

import board.Board;
import robot.AbstractRobot;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class GameFrame extends JFrame{

    private AbstractRobot activePlayer;
    private BoardComponent boardComponent;


    /**
     * Sets the activePlayer to a a robot. The activePlayer is the one who's interface is displayed.
     * @param player the player who's turn it is.
     */
    public void setActivePlayer(AbstractRobot player) {
        remove(activePlayer.getPanel());
        activePlayer = player;
        add(activePlayer.getPanel(), BorderLayout.PAGE_END);

	setSize(boardComponent.getPreferredSize());
 	requestFocus();
 	setVisible(true);
	pack();

    }

    public GameFrame(Board board, AbstractRobot player) {

        super("RoboRampage");

        activePlayer = player;

        boardComponent = new BoardComponent(board);
        board.addBoardListener(boardComponent);
        setLayout(new BorderLayout());
        add(activePlayer.getPanel(), BorderLayout.PAGE_END);
        add(boardComponent,BorderLayout.CENTER);



        setSize(boardComponent.getPreferredSize());
        requestFocus();
        setVisible(true);
        pack();

    }

}
