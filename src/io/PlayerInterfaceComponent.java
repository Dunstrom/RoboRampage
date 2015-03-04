package io;

import board.AbstractTile;
import board.Board;
import game.Player;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>PlayerInterfaceComponent</h1><br>
 *     <p>Responsible for drawing and handling input from the player interface.</p>
 */
public class PlayerInterfaceComponent extends JComponent {

    private Player player;
    private Color backgroundColor = Color.WHITE;
    private Color buttonColor = Color.BLACK;

    public PlayerInterfaceComponent(Player player) {

        this.player = player;

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawButtons(g);

        drawHealthBar(g);

    }

    private void drawButtons(Graphics g) {

        Rectangle moveUpButton;
        Rectangle turnLeftButton;
        Rectangle turnRightButton;
        Rectangle endTurnButton;

    }

    private void drawHealthBar(Graphics g) {

    }

}
