package entity;

import board.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * Class to create test robots
 */
public class TestRobot extends AbstractRobot {

    private final static int HEALTH = 10;

    public BufferedImage getRobotSprite() {
        return robotSprite;
    }

    public TestRobot(final int x, final int y, final Orientation orientation, String name, String spriteFileName) {
        super(x, y, orientation, name, HEALTH);
        robotSprite = loadImage("../Resources/" + spriteFileName);

        //Setup player interface
        playerInterface.addMove(forwadButton);
        playerInterface.addMove(turnLeftButton);
        playerInterface.addMove(turnRightButton);

    }

    AbstractButton forwadButton = new AbstractButton() {
        @Override
        public String display() {
            return "Forward";
        }

        @Override
        public void run() {
            addProgrammedMove(moveForwardOne);
        }
    };

    AbstractButton turnLeftButton = new AbstractButton() {
        @Override
        public String display() {
            return "Turn Left";
        }

        @Override
        public void run() {
            addProgrammedMove(turnLeft);
        }
    };

    AbstractButton turnRightButton = new AbstractButton() {
        @Override
        public String display() {
            return "Turn Right";
        }

        @Override
        public void run() {
            addProgrammedMove(turnRight);
        }
    };

    /**
     * Runnable that moves the entity.robot one step forward.
     */
    AbstractButton moveForwardOne = new AbstractButton() {
        @Override
        public String display() {
            return "Forward";
        }

        @Override
        public void run() {
            switch (orientation) {
                case NORTH:
                    tempX = x;
                    tempY = y - Tile.TILE_SIZE;
                    break;
                case SOUTH:
                    tempX = x;
                    tempY = y + Tile.TILE_SIZE;
                    break;
                case WEST:
                    tempX = x - Tile.TILE_SIZE;
                    tempY = y;
                    break;
                case EAST:
                    tempX = x + Tile.TILE_SIZE;
                    tempY = y;
                    break;
            }
        }
    };

    /**
     * Runnable that turns the entity.robot left 90 degrees
     */
    AbstractButton turnLeft = new AbstractButton() {
        @Override
        public String display() {
            return "Turn Left";
        }

        @Override
        public void run() {
            switch (orientation) {
                case NORTH:
                    orientation = Orientation.WEST;
                    tempX = x;
                    tempY = y;
                    break;
                case SOUTH:
                    orientation = Orientation.EAST;
                    tempX = x;
                    tempY = y;
                    break;
                case WEST:
                    orientation = Orientation.SOUTH;
                    tempX = x;
                    tempY = y;
                    break;
                case EAST:
                    orientation = Orientation.NORTH;
                    tempX = x;
                    tempY = y;
                    break;
            }
        }
    };


    /**
     * Runnable that turns the entity.robot right 90 degrees
     */
    AbstractButton turnRight = new AbstractButton() {
        @Override
        public String display() {
            return "Turn Right";
        }

        @Override
        public void run() {
            switch (orientation) {
                case NORTH:
                    orientation = Orientation.EAST;
                    tempX = x;
                    tempY = y;
                    break;
                case EAST:
                    orientation = Orientation.SOUTH;
                    tempX = x;
                    tempY = y;
                    break;
                case SOUTH:
                    orientation = Orientation.WEST;
                    tempX = x;
                    tempY = y;
                    break;
                case WEST:
                    orientation = Orientation.NORTH;
                    tempX = x;
                    tempY = y;
                    break;
            }
        }

    };


    /**
     * Draws the entity.robot by drawing it's sprite rotated in the right direction. Completely overrides supermethod since we don't want to draw that at all.
     *
     * @param g a Graphics object
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        final int degreesToRotate;
        switch (orientation) {
            case NORTH:
                degreesToRotate = 0;
                break;
            case WEST:
                degreesToRotate = 270;
                break;
            case SOUTH:
                degreesToRotate = 180;
                break;
            case EAST:
                degreesToRotate = 90;
                break;
            default:
                degreesToRotate = 0;
        }

        drawImageRotated(robotSprite, degreesToRotate, getX(), getY(), g2d);

    }

}
