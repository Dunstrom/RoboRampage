package entity;

import io.InterfaceComponent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Abstract class for all robots in the game.
 */
public abstract class AbstractRobot extends AbstractBoardObject{

    //For the collisionhandling
    protected int tempX;
    protected int tempY;

    //Interface and output
    //protected JPanel mainPanel;
    //protected JPanel moveButtonPanel;
    //protected JPanel turnButtonPanel;
    protected boolean endable;
    protected boolean done;
    //protected JLabel infoBox;
    //protected JLabel displayedMoves;
    //protected JLabel healthLabel;
    protected BufferedImage buttonSprite;
    protected BufferedImage hpBarSprite;
    protected InterfaceComponent playerInterface;
    protected BufferedImage robotSprite;
    protected BufferedImage choosenMoveSprite;

    // Stats
    protected String name;
    protected Queue<AbstractButton> programmedMoves;
    /** The maximum amount of moves allowed for one entity.robot */
    public final static int MAX_QUEUED_MOVES = 3;
    protected int hitpoints;
    protected int damage;
    protected boolean dead;
    protected List<Flag> flags;

    // Getters
    public Queue<AbstractButton> getProgrammedMoves() {
        return programmedMoves;
    }

    public BufferedImage getChoosenMoveSprite() {
        return choosenMoveSprite;
    }

    public boolean isEndable() {
        return endable;
    }

    public InterfaceComponent getPlayerInterface() {
        return playerInterface;
    }

    public BufferedImage getButtonSprite() {
        return buttonSprite;
    }

    public String getName(){
        return name;
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

    /*public JPanel getMainPanel() {
        return mainPanel;
    } */

    public boolean isDead() { return dead; }

    public int getFlagCount() { return flags.size(); }

    public BufferedImage getHpBarSprite() {
        return hpBarSprite;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public BufferedImage getRobotSprite() {
        return robotSprite;

    }

    // Setters
    public void setEndable(boolean bl) {
        endable = bl;
    }

    public void setTempX(int newTempX) { tempX = newTempX; }

    public void setTempY(int newTempY) {
        tempY = newTempY;
    }

    public void pickFlag(Flag flag) {
        if(!flags.contains(flag)){
            flags.add(flag);
        }
    }



    protected AbstractRobot(final int x, final int y, final Orientation orientation, final String name, final int hitpoints) {
        super(x, orientation, y);

        //Setup robot
        this.hitpoints = hitpoints;
        damage = 1;
        dead = false;
        this.name = name;
        programmedMoves = new LinkedList<>();
        flags = new ArrayList<>();

        done = false;

        //Setup player interface
        buttonSprite = loadImage("../Resources/Button.png");
        hpBarSprite = loadImage("../Resources/hp_bar.png");
        robotSprite = loadImage("../Resources/Robot.png");
        choosenMoveSprite = loadImage("../Resources/Red_Button.png");
        playerInterface = new InterfaceComponent(this);


        /*
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
        */

    }

    /**
     * Constructs a string and sets it to the JLabel displayedMoves.
     */
    public void updateDisplayedMoves(){

        playerInterface.paintImmediately(0,0,playerInterface.getWidth(), playerInterface.getWidth());

      /*  StringBuilder builder = new StringBuilder();
        builder.append("You have choosen: ");

        for (AbstractMove programmedMove : programmedMoves) {
            builder.append(programmedMove.display());
            builder.append(" ");
        }

        displayedMoves.setText(builder.toString());
        healthLabel.setText(Integer.toString(hitpoints));

        */
    }

    /**
     * Adds a move to the list of pre programmed moves.
     * @param move a Runnable that is supposed to be executed in the sequential move phase of the game.
     */
    protected void addProgrammedMove(AbstractButton move){

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
    public void removeProgrammedMove(){
        if(!programmedMoves.isEmpty()){
            programmedMoves.remove();
            endable = false;
            updateDisplayedMoves();
        }
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

}

