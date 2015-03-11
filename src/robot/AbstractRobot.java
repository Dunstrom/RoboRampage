package robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.*;
import java.net.URL;
import java.util.Queue;
import java.util.LinkedList;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Abstract class for all robots in the game.
 */
public abstract class AbstractRobot implements Robot {

    // Position
    protected int x;
    protected int y;
    protected Orientation orientation;

    //For the collisionhandling
    protected int tempX;
    protected int tempY;

    //Interface and output
    protected JPanel mainPanel;
    protected JPanel moveButtonPanel;
    protected JPanel turnButtonPanel;
    protected boolean endable;
    protected boolean done;
    protected JLabel infoBox;
    protected JLabel displayedMoves;
    protected Color color;
    public final static int ROBOT_SIZE = 20;

    // Stats
    protected Queue<AbstractMove> programmedMoves;
    protected final static int MAX_QUEUED_MOVES = 3;
    protected int hitPoints;

    // Getters

    public static int getMaxQueuedMoves() {
        return MAX_QUEUED_MOVES;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTempY() {
        return tempY;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getTempX() {
        return tempX;
    }

    public Runnable getNextMove() {
        return programmedMoves.poll();
    }

    public void setDone(boolean bool) {
        done = bool;
    }

    public boolean getDone() {
        return done;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    // Setters

    public void setTempX(int newTempX) { tempX = newTempX; }

    public void setTempY(int newTempY) {
        tempY = newTempY;
    }



    public AbstractRobot(final int x, final int y, final Orientation orientation, final String name, final Color color) {

        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.color = color;

        programmedMoves = new LinkedList<>();

        setupPlayerInterface(name);

    }

    /**
     * Sets up the interface in which the player interacts with the game.
     * @param name Name of the player using the interface
     */
    @Override public void setupPlayerInterface(String name) {

        done = false;

        mainPanel = new JPanel(new BorderLayout());
        turnButtonPanel = new JPanel(new GridLayout());
        moveButtonPanel = new JPanel(new FlowLayout());

        JButton endTurnButton  = new JButton();
        JButton removeMoveButton = new JButton();

        infoBox = new JLabel("It's " + name + "s turn");
        displayedMoves = new JLabel();

        updateDisplayedMoves();

        removeMoveButton.setAction(new AbstractAction()
        {
            @Override public void actionPerformed(ActionEvent e) {
                removeProgrammedMove();
            }
        });
        endTurnButton.setAction(new AbstractAction(){
            @Override public void actionPerformed(final ActionEvent e) {
                if(endable){
                    done = true;
                    endable = false;
                }
            }
        });

        removeMoveButton.setText("Remove Move");
        endTurnButton.setText("End Turn");

        turnButtonPanel.add(removeMoveButton);
        turnButtonPanel.add(endTurnButton);

        mainPanel.add(turnButtonPanel, BorderLayout.LINE_START);
        mainPanel.add(moveButtonPanel, BorderLayout.CENTER);
        mainPanel.add(infoBox, BorderLayout.PAGE_START);
        mainPanel.add(displayedMoves, BorderLayout.PAGE_END);

    }

    /**
     * Constructs a string and sets it to the JLabel displayedMoves.
     */
    public void updateDisplayedMoves(){

        StringBuilder builder = new StringBuilder();
        builder.append("You have choosen: ");

        for (AbstractMove programmedMove : programmedMoves) {
            builder.append(programmedMove.display());
            builder.append(" ");
        }

        displayedMoves.setText(builder.toString());
    }

    /**
     * changes the robots position
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    @Override public void place(final int newX, final int newY) {

        x = newX;
        y = newY;

    }

    /**
     * Makes the robot attack
     */
    @Override public void attack() {
        //TODO: Implement attack
    }

    /**
     * Draws the robot on the screen
     * @param g a Graphics object
     */
    @Override public void draw(final Graphics g) {



        g.setColor(color);
        final int yRobotOffset = 10;
        final int xRobotOffset = 10;
        g.fillRect(x + xRobotOffset, y + yRobotOffset, ROBOT_SIZE, ROBOT_SIZE);
        final int yTextOffset = 15 + yRobotOffset;
        final int xTextOffset = 5 + xRobotOffset;
        g.setColor(Color.BLACK);
        switch (orientation) {
            case NORTH:
                g.drawString("N", x + xTextOffset, y + yTextOffset);
                break;
            case SOUTH:
                g.drawString("S", x + xTextOffset, y + yTextOffset);
                break;
            case WEST:
                g.drawString("W", x + xTextOffset, y + yTextOffset);
                break;
            case EAST:
                g.drawString("E", x + xTextOffset, y + yTextOffset);
                break;


        }
    }

    /**
     * Adds a move to the list of pre programmed moves.
     * @param move a Runnable that is supposed to be executed in the sequential move phase of the game.
     */
    protected void addProgrammedMove(AbstractMove move){

        if(programmedMoves.size() < MAX_QUEUED_MOVES){
            programmedMoves.add(move);
            if(programmedMoves.size() == MAX_QUEUED_MOVES) {
                endable = true;
            }
            updateDisplayedMoves();
        }else {
            programmedMoves.remove();
            programmedMoves.add(move);
            updateDisplayedMoves();
        }
    }

    /**
     * Removes a the latest added programmed move.
     */
    protected void removeProgrammedMove(){
        if(!programmedMoves.isEmpty()){
            programmedMoves.remove();
            endable = false;
            updateDisplayedMoves();
        }
    }

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    protected BufferedImage loadImage(String fileName) {

        BufferedImage image;
        URL url = AbstractRobot.class.getResource(fileName);
        try {
            image = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
            image = null;
        }


        return image;
    }

    /**
     * Takes an image and draws it rotated to the screen.
     * @param image The image we want to draw.
     * @param degreesToRotate The amount of degrees to rotate the image.
     * @param x the x position where we draw the image.
     * @param y the y position where we draw the image.
     * @param g2d a Graphics2D object that draws the image to the screen.
     */
    protected void drawImageRotated(BufferedImage image, double degreesToRotate, int x, int y, Graphics2D g2d){

        double rotationRequired = Math.toRadians(degreesToRotate);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter(image, null), x, y, null);

    }

}

