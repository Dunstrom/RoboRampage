package io;


import javax.swing.JComponent;
import java.awt.Font;

/** An abstract class for every Component used in the game. */
public abstract class GameComponent extends JComponent {

    protected final static int BUTTON_FONT_SIZE = 14;
    protected final static Font BUTTON_FONT = new Font("Arial",Font.BOLD, BUTTON_FONT_SIZE);
    protected final static int HEADLINE_FONT_SIZE = 25;
    protected final static Font HEADLINE_FONT = new Font("Arial",Font.BOLD, HEADLINE_FONT_SIZE);

}
