package robot;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Queue;
import java.util.LinkedList;

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
    protected JPanel panel;
    protected boolean endable;
    protected boolean done;
    protected JLabel infoBox;
    protected JLabel displayedMoves;
    protected Color color;

    // Stats
    protected Queue<AbstractMove> programmedMoves;
    protected final static int MAX_QUEUED_MOVES = 3;
    protected int hitPoints;

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

    public AbstractRobot(final int x, final int y, final Orientation orientation, final String name, final Color color) {

        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.color = color;

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
        displayedMoves = new JLabel("Hej");
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

        final int robotSize = 20; //Size of the robot in pixels

        g.setColor(color);
        final int yRobotOffset = 10;
        final int xRobotOffset = 10;
        g.fillRect(x + xRobotOffset, y + yRobotOffset, robotSize, robotSize);
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

}

