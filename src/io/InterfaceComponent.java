package io;

import board.Tile;
import game.Game;
import entity.AbstractButton;
import entity.AbstractRobot;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hampus on 2015-04-20.
 */
public class InterfaceComponent extends JComponent implements MouseListener {

    private AbstractRobot robot;
    private final static int HEIGHT = 150;
    private final static int WIDTH = Game.BOARD_WIDTH * Tile.TILE_SIZE;
    private List<AbstractButton> moves;
    private Map<Rectangle, AbstractButton> buttons;

    public void addMove(AbstractButton move) {
        moves.add(move);
    }

    public InterfaceComponent(AbstractRobot robot) {
        this.robot = robot;
        moves = new ArrayList<>();
        buttons = new HashMap<>();
        addMouseListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        return new Dimension(WIDTH, HEIGHT);
    }

    private void drawHealthBar(Graphics g) {
        BufferedImage sprite = robot.getHpBarSprite();
        final int marginRight = 10;
        final int marginBottom = 20;
        final int x = WIDTH - marginRight - sprite.getWidth();
        int spriteHeight = sprite.getHeight();
        int padding = 1;
        for(int i = 0; i < robot.getHitpoints(); i++) {
            int y = HEIGHT - (i * (spriteHeight + padding) + marginBottom);
            g.drawImage(sprite, x, y, null);
        }
    }

    private void drawStandardButtons(Graphics g) {
        AbstractButton[] standardButtons = {new EndTurn(), new RemoveMove()};
        BufferedImage sprite = robot.getButtonSprite();
        final int y = sprite.getHeight()/2;
        final int marginLeft = 25;
        int x = marginLeft;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2;
        for(AbstractButton button : standardButtons) {
            g.drawImage(sprite, x, y, null);
            g.drawString(button.display(), x + stringMarginLeft, y + stringMarginTop);
            buttons.put(new Rectangle(x, y, sprite.getWidth(), sprite.getHeight()), button);
            x += sprite.getWidth() + marginLeft;

        }
    }

    public class EndTurn implements AbstractButton {

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

    public class RemoveMove implements AbstractButton {
        public void run() {
            robot.removeProgrammedMove();
        }

        @Override
        public String display() {
            return "Remove Move";
        }
    }

    private void drawMoveButtons(Graphics g) {
        BufferedImage sprite = robot.getButtonSprite();
        int x = WIDTH/2;
        final int y = sprite.getHeight()/2;
        final int marginRight = 20;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2;
        for(AbstractButton button : moves) {
            String text = button.display();
            g.drawImage(sprite, x, y, null);
            g.drawString(text, x + stringMarginLeft, y + stringMarginTop);
            buttons.put(new Rectangle(x, y, sprite.getWidth(), sprite.getHeight()), button);
            x += sprite.getWidth() + marginRight;
        }
    }

    private void drawChoosenMoves(Graphics g) {
        BufferedImage sprite = robot.getChoosenMoveSprite();
        int x = WIDTH/2;
        final int y = HEIGHT - sprite.getHeight() - 10;
        final int stringMarginLeft = 15;
        final int marginRight = 20;
        final int stringMarginTop = sprite.getHeight()/2;
        for(AbstractButton move : robot.getProgrammedMoves()) {
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
        g.drawImage(sprite, x, y, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHealthBar(g);
        drawStandardButtons(g);
        drawMoveButtons(g);
        drawRobot(g);
        drawChoosenMoves(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for(Map.Entry<Rectangle, AbstractButton> button : buttons.entrySet()){
            if(button.getKey().contains(e.getX(), e.getY())){
                button.getValue().run();
                System.out.println(button.getValue().display());
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
