package robot;


import io.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Abstract class for all robots in the game.
 */
public abstract class AbstractRobot implements Robot
{
    protected int x;
    protected int y;
    protected Orientation orientation;
    protected int hitPoints;

    //For the collisionhandling
    protected int tempX;
    protected int tempY;

    protected JPanel panel;
    protected boolean endable;
    protected boolean done;
    protected JLabel infoBox;
    protected JLabel displayedMoves;

    protected Queue<AbstractMove> programmedMoves;
    protected final static int maxQueuedMoves = 3;

    public static int getMaxQueuedMoves() {
        return maxQueuedMoves;
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

    public Orientation getOrientation() {
        return orientation;
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

    public JPanel getPanel() {
        return panel;
    }

    public AbstractRobot(final int x, final int y, final Orientation orientation, String name) {

        this.x = x;
        this.y = y;
        this.orientation = orientation;

        programmedMoves = new LinkedList<AbstractMove>();

        setupPlayerInterface(name);

    }

    /**
     * Sets up the interface in which the player interacts with the game.
     * @param name Name of the player using the interface
     */
    @Override public void setupPlayerInterface(String name) {

        done = false;

        panel  = new JPanel();
        JButton endTurnButton  = new JButton();
        JButton removeMoveButton = new JButton();
        infoBox = new JLabel("It's " + name + "s turn");
        displayedMoves = new JLabel();
        updateDisplayedMoves();

        removeMoveButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        panel.add(removeMoveButton);
        panel.add(endTurnButton);
        panel.add(infoBox);
        panel.add(displayedMoves);

    }

    /**
     * Constructs a string and sets it to the JLabel displayedMoves.
     */
    private void updateDisplayedMoves(){

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < programmedMoves.size(); i++){
            //builder.append(programmedMoves.get(i).display()); TODO: The documentation says LinkedList has a get method but apperantly idea don't agree... needs to be fixed
        }

        displayedMoves.setText(builder.toString());

    }

    /**
     * checks if robot can be moved
     * @return true if robot is movable
     */
    @Override public boolean movable() {return false;}

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

        int robotSize = 20; //Size of the robot in pixels
        ImageLoader imageLoader = new ImageLoader();
        /*BufferedImage robotImage = imageLoader.loadImage("Robot.png");
        if(robotImage != null) {
            g.drawImage(robotImage, x, y, null);
        } else {

            g.setColor(Color.BLUE);
            g.fillRect(x, y, robotSize, robotSize);

            g.setColor(Color.BLACK);
            switch (orientation) {
                case NORTH:
                    g.drawString("N", x, y);
                    break;
                case SOUTH:
                    g.drawString("S", x, y);
                    break;
                case WEST:
                    g.drawString("W", x, y);
                    break;
                case EAST:
                    g.drawString("E", x, y);
                    break;


            }
        }
        */
        g.setColor(Color.BLUE);
        g.fillRect(x, y, robotSize, robotSize);

        g.setColor(Color.BLACK);
        switch (orientation) {
            case NORTH:
                g.drawString("N", x, y);
                break;
            case SOUTH:
                g.drawString("S", x, y);
                break;
            case WEST:
                g.drawString("W", x, y);
                break;
            case EAST:
                g.drawString("E", x, y);
                break;


        }
    }

    /**
     * Adds a move to the list of pre programmed moves.
     * @param move a Runnable that is supposed to be executed in the sequential move phase of the game.
     */
    protected void addProgrammedMove(AbstractMove move){

        if(programmedMoves.size() < maxQueuedMoves){
            programmedMoves.add(move);
            if(programmedMoves.size() == maxQueuedMoves) {
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
        if(programmedMoves.size() > 0){
            programmedMoves.remove();
            endable = false;
        }
    }

}

