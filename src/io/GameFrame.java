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
