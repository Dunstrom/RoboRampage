package robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
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
public abstract class AbstractRobot {

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
    protected JLabel healthLabel;

    // Stats
    protected String name;
    protected Queue<AbstractMove> programmedMoves;
    /** The maximum amount of moves allowed for one robot */
    public final static int MAX_QUEUED_MOVES = 3;
    protected int hitpoints;
    protected int damage;
    protected boolean dead;

    // Getters

    public String getName(){
        return name;
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

    public int getTempX() {
        return tempX;
    }

    public Runnable getNextMove() {
        return programmedMoves.poll();
    }

    public void setDone(boolean bl) {
        done = bl;
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

    public boolean isDead() { return dead; }

    // Setters

    public void setTempX(int newTempX) { tempX = newTempX; }

    public void setTempY(int newTempY) {
        tempY = newTempY;
    }



    protected AbstractRobot(final int x, final int y, final Orientation orientation, final String name, final int hitpoints) {

        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.hitpoints = hitpoints;
        damage = 1;
        dead = false;
        this.name = name;
        programmedMoves = new LinkedList<>();

        done = false;

        mainPanel = new JPanel(new BorderLayout());
        turnButtonPanel = new JPanel(new GridLayout());
        moveButtonPanel = new JPanel(new FlowLayout());
        JButton endTurnButton  = new JButton();
        JButton removeMoveButton = new JButton();
        infoBox = new JLabel("It's " + name + "s turn");
        displayedMoves = new JLabel();

        // Temporary
        healthLabel = new JLabel(Integer.toString(hitpoints));
        mainPanel.add(healthLabel, BorderLayout.LINE_END);

        updateDisplayedMoves();
        removeMoveButton.setAction(new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                removeProgrammedMove();
            }
        });
        endTurnButton.setAction(new AbstractAction() {
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
        healthLabel.setText(Integer.toString(hitpoints));
    }

    /**
     * changes the robots position
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    public void place(final int newX, final int newY) {

        x = newX;
        y = newY;

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
        int locationX = image.getWidth() / 2;
        int locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter(image, null), x, y, null);

    }

    public void takeDamage(int damage) {
        hitpoints -= damage;
        if(hitpoints < 1) {
            dead = true;
        }
    }

    public int getDamage() {
        return damage;
    }

    public abstract void draw(Graphics g);

}

