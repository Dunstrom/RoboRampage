package io;

import board.BoardNotFoundException;
import board.SettingsFailiureException;
import entity.Flag;
import entity.Button;
import entity.AbstractRobot;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is the component that handles the players input during the game and draws the interface the player interacts with.
 */
public class InterfaceComponent extends GameComponent implements MouseListener {

    private AbstractRobot robot;
    private final static int HEIGHT = 150;
    private int width;
    private List<Button> moves;
    private Map<Rectangle, Button> buttons;

    public void addMove(Button move) {
        moves.add(move);
    }

    public InterfaceComponent(AbstractRobot robot, Settings settings) throws BoardNotFoundException, SettingsFailiureException {
        width = settings.getBoardWidth() * settings.getTileSize();
        this.robot = robot;
        moves = new ArrayList<>();
        buttons = new HashMap<>();
        addMouseListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        return new Dimension(width, HEIGHT);
    }

    private void drawHealthBar(Graphics g) {
        BufferedImage sprite = robot.getHpBarSprite();
        final int marginRight = 10;
        final int marginBottom = 20;
        final int x = width - marginRight - sprite.getWidth();
        int spriteHeight = sprite.getHeight();
        int padding = 1;
        for(int i = 0; i < robot.getHitpoints(); i++) {
            int y = HEIGHT - (i * (spriteHeight + padding) + marginBottom);
            g.drawImage(sprite, x, y, null);
        }
    }

    private void drawStandardButtons(Graphics g) {
        Button[] standardButtons = {new EndTurn(), new RemoveMove()};
        BufferedImage sprite = robot.getButtonSprite();
        final int y = sprite.getHeight()/2;
        final int marginLeft = 25;
        int x = marginLeft;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2;
        for(Button button : standardButtons) {
            g.drawImage(sprite, x, y, null);
            g.drawString(button.display(), x + stringMarginLeft, y + stringMarginTop);
            if(!buttons.containsValue(button)){
                buttons.put(new Rectangle(x, y, sprite.getWidth(), sprite.getHeight()), button);
            }
            x += sprite.getWidth() + marginLeft;

        }
    }

    public class EndTurn implements Button {

        public void run() {
            if(robot.isEndable()){
                robot.setDone(true);
                robot.setEndable(false);
            }
        }

        @Override
        public String display() {
            return "End Turn";
        }
    }

    public class RemoveMove implements Button {
        @Override
        public String display() {
            return "Remove";
        }

        @Override
        public void run() {
            robot.removeProgrammedMove();
            robot.renderPlayerInterface();
        }
    }

    private void drawMoveButtons(Graphics g) {
        BufferedImage sprite = robot.getButtonSprite();
        int x = width/2;
        final int y = sprite.getHeight()/2;
        final int marginRight = 20;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2;
        for(Button button : moves) {
            String text = button.display();
            g.drawImage(sprite, x, y, null);
            g.drawString(text, x + stringMarginLeft, y + stringMarginTop);
            if(!buttons.containsValue(button)){
                buttons.put(new Rectangle(x, y, sprite.getWidth(), sprite.getHeight()), button);
            }
            x += sprite.getWidth() + marginRight;
        }
    }

    private void drawChoosenMoves(Graphics g) {
        BufferedImage sprite = robot.getChoosenMoveSprite();
        int x = width/2;
        final int y = HEIGHT - sprite.getHeight() - 10;
        final int stringMarginLeft = 15;
        final int marginRight = 20;
        final int stringMarginTop = sprite.getHeight()/2;
        for(Button move : robot.getProgrammedMoves()) {
            String text = move.display();
            g.drawImage(sprite, x, y, null);
            g.drawString(text, x + stringMarginLeft, y + stringMarginTop);
            x += sprite.getWidth() + marginRight;
        }
    }

    private void drawRobot(Graphics g) {
        BufferedImage sprite = robot.getRobotSprite();
        final int x = 25;
        final int y = HEIGHT - sprite.getHeight() - 10;
        final int textX = x + sprite.getWidth() + 10;
        final int textY = y + sprite.getHeight()/2;
        g.drawImage(sprite, x, y, null);
        g.drawString(robot.getName(), textX, textY);
    }

    private void drawFlags(Graphics g) {
        final int flagMarginTop = 10;
        final int marginRight = 75;
        final int x = width - (Flag.WIDTH + marginRight);
        int y = HEIGHT - Flag.HEIGHT - flagMarginTop;
        for(Flag flag : robot.getFlags()) {
            g.setColor(flag.getColor());
            g.fillRect(x, y, Flag.WIDTH, Flag.HEIGHT);
            g.setColor(flag.getBorderColor());
            g.drawRect(x, y, Flag.WIDTH, Flag.HEIGHT);
            y -= Flag.HEIGHT + flagMarginTop;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int fontSize = 14;
        Font font = new Font("Arial",Font.BOLD, fontSize);
        g.setFont(font);
        drawHealthBar(g);
        drawStandardButtons(g);
        drawMoveButtons(g);
        drawRobot(g);
        drawChoosenMoves(g);
        drawFlags(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Entry<Rectangle, Button> button : buttons.entrySet()){//Prefer it this way because of increased readability
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
