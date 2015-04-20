package io;

import board.Board;
import entity.AbstractMove;
import entity.AbstractRobot;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2015-04-20.
 */
public class InterfaceComponent extends JComponent {

    private AbstractRobot robot;
    private Board board;
    private final static int HEIGHT = 400;
    private int width;
    private List<AbstractMove> moves;

    public void addMove(AbstractMove move) {
        moves.add(move);
    }

    public InterfaceComponent(AbstractRobot robot, Board board) {
        this.robot = robot;
        this.board = board;
        width = board.getWidth();
        moves = new ArrayList<>();
    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        return new Dimension(width, HEIGHT);
    }

    private void drawHealthBar(Graphics g) {
        BufferedImage sprite = robot.getHpBarSprite();
        final int marginRight = 10;
        final int marginBottom = 10;
        final int x = width - marginRight;
        int spriteHeight = sprite.getHeight();
        int padding = 1;
        for(int i = 0; i < robot.getHitpoints(); i++) {
            int y = HEIGHT - (i * (spriteHeight + padding) + marginBottom);
            g.drawImage(sprite, x, y, null);
        }
    }

    private void drawStandardButtons(Graphics g) {
        String[] texts = {"Remove Move", "End Turn"};
        BufferedImage sprite = robot.getButtonSprite();
        final int y = HEIGHT/2 - sprite.getHeight()/2;
        final int marginLeft = 25;
        int x = marginLeft;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2 - 10;
        for(String text : texts) {
            g.drawImage(sprite, x, y, null);
            g.drawString(text, x + stringMarginLeft, y + stringMarginTop);
            x += sprite.getWidth() + marginLeft;
        }
    }

    private void drawMoveButtons(Graphics g) {
        BufferedImage sprite = robot.getButtonSprite();
        int x = width/2;
        final int y = HEIGHT/2 - sprite.getHeight()/2;
        final int marginRight = 20;
        final int stringMarginLeft = 15;
        final int stringMarginTop = sprite.getHeight()/2 - 10;
        for(AbstractMove move : moves) {
            String text = move.display();
            g.drawImage(sprite, x, y, null);
            g.drawString(text, x + stringMarginLeft, y + stringMarginTop);
            x += sprite.getWidth() + marginRight;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHealthBar(g);
        drawStandardButtons(g);
        drawMoveButtons(g);
    }



}
