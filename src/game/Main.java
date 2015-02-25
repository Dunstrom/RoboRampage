package game;

import javax.swing.*;

/**
 *<h1>Main</h1><br>
 *     <p>Keeps track of the mainloop that makes the game go forward.</p>
 */
public class Main {

    public static void main(String[] args) {

        final int updateTime = 500;

        final Game game = new Game();

        final Action doOneFrame = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                game.update();
            }
        };

        final Timer timer = new Timer(updateTime, doOneFrame);
        timer.setCoalesce(true);
        timer.start();

    }

}

