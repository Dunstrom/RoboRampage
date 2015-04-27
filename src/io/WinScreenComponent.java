package io;

import entity.Button;
import game.DoneListener;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** Knows how to handle io when the game is in the winscreen state. */
public class WinScreenComponent extends GameComponent implements MouseListener {

    private String winner;
    private List<DoneListener> listeners;
    private Map<Rectangle, Button> buttons;
    private Rectangle restartButtonArea;
    private Rectangle endGameButtonArea;
    private BufferedImage buttonSprite;
    private BufferedImage backgroundImage;
    private final static int WIDTH = 1000;
    private final static int HEIGHT = 750;

    public void addListener(DoneListener listener) {
        listeners.add(listener);
    }

    public WinScreenComponent(String winner) {

        buttonSprite = loadImage("Button.png");
        backgroundImage = loadImage("winscreen.png");
        this.winner = winner;
        listeners = new ArrayList<>();
        buttons = new HashMap<>();
        addMouseListener(this);

        Button restart = new Button() {
            @Override
            public String display() {
                return "Restart";
            }

            @Override
            public void run() {
                done();
            }
        };

        Button endGame = new Button() {
            @Override
            public String display() {
                return "End Game";
            }

            @Override
            public void run() {
                System.exit(0);
            }
        };

        restartButtonArea = new Rectangle(WIDTH / 3, HEIGHT - 100, buttonSprite.getWidth(), buttonSprite.getWidth());
        endGameButtonArea = new Rectangle(WIDTH / 3 + buttonSprite.getWidth() * 2, HEIGHT - 100, buttonSprite.getWidth(), buttonSprite.getWidth());

        buttons.put(restartButtonArea, restart);
        buttons.put(endGameButtonArea, endGame);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int buttonMargin = 15;
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(buttonSprite, (int) restartButtonArea.getX(), (int) restartButtonArea.getY(), null);
        g.drawImage(buttonSprite, (int) endGameButtonArea.getX(), (int) endGameButtonArea.getY(), null);
        g.setFont(BUTTON_FONT);
        g.drawString(buttons.get(restartButtonArea).display(), (int) restartButtonArea.getX() + buttonMargin, (int) restartButtonArea.getY() + (int)restartButtonArea.getHeight()/4 );
        g.drawString(buttons.get(endGameButtonArea).display(), (int) endGameButtonArea.getX() + buttonMargin, (int) endGameButtonArea.getY() + (int)restartButtonArea.getHeight()/4 );
        g.setFont(HEADLINE_FONT);
        g.drawString(winner, WIDTH / 2, HEIGHT / 2);

    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        return new Dimension(WIDTH, HEIGHT);
    }

    private void done() {
        listeners.forEach(DoneListener::whenDone);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Entry<Rectangle, Button> button : buttons.entrySet()){
            if(button.getKey().contains(e.getX(), e.getY())){
                button.getValue().run();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
