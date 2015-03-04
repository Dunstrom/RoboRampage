package io;

import board.Board;
import game.Player;
import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Creates and updates the frame.
 */
public class GameFrame extends JFrame{

    private BoardComponent boardComponent;
    private Player activePlayer;

    /**
     * <h1>setActivePlayer</h1><br>
     *     <p>Sets the activePlayer to a a player. The active player is the one whom's interface is displayed</p>
     * @param player the player whom's turn it is.
     */
    public void setActivePlayer(Player player) {
        remove(activePlayer.getPanel());
        activePlayer = player;
        add(activePlayer.getPanel());
    }

    public GameFrame(Board board, Player player) {

        super("RoboRampage");

        activePlayer = player;

        boardComponent = new BoardComponent(board);
        setLayout(new BorderLayout());
        add(activePlayer.getPanel(), BorderLayout.PAGE_END);
        add(boardComponent,BorderLayout.CENTER);



        setSize(boardComponent.getPreferredSize());
        requestFocus();
        setVisible(true);
        pack();

    }

}
