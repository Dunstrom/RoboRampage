package entity;

import io.InterfaceComponent;

import javax.sound.sampled.Clip;
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

    protected boolean endable;
    protected boolean done;

    //io
    protected BufferedImage buttonSprite;
    protected BufferedImage hpBarSprite;
    protected InterfaceComponent playerInterface;
    protected BufferedImage robotSprite;
    protected BufferedImage choosenMoveSprite;
    protected Clip testSound;

    // Stats
    protected String name;
    protected Queue<Move> programmedMoves;
    /** The maximum amount of moves allowed for one entity.robot */
    public final static int MAX_QUEUED_MOVES = 3;
    protected int hitpoints;
    protected int damage;
    protected boolean dead;
    protected List<Flag> flags;



    // Getters
    public Queue<Move> getProgrammedMoves() {
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

    public Move getNextMove() {
        return programmedMoves.poll();
    }

    public void setDone(boolean bl) {
        done = bl;
    }

    public boolean getDone() {
        return done;
    }

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

    public List<Flag> getFlags() {
        return flags;
    }

    // Setters
    public void setEndable(boolean bl) {
        endable = bl;
    }

    public void setTempX(int newTempX) { tempX = newTempX; }

    public void setTempY(int newTempY) {
        tempY = newTempY;
    }

    public void kill(){
	dead = true;
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
        choosenMoveSprite = loadImage("../Resources/Brown_Button.png");
        playerInterface = new InterfaceComponent(this);

        testSound = loadSoundClip("../Resources/testljud.wav");

    }

    /**
     * Constructs a string and sets it to the JLabel displayedMoves.
     */
    public void renderPlayerInterface(){

        playerInterface.paintImmediately(0,0,playerInterface.getWidth(), playerInterface.getWidth());

    }

    /**
     * Adds a move to the list of pre programmed moves.
     * @param move a Runnable that is supposed to be executed in the sequential move phase of the game.
     */
    protected void addProgrammedMove(Move move){

        if(programmedMoves.size() < MAX_QUEUED_MOVES){
            programmedMoves.add(move);
            if(programmedMoves.size() == MAX_QUEUED_MOVES) {
                endable = true;
            }
            renderPlayerInterface();
        }else {
            programmedMoves.remove();
            programmedMoves.add(move);
            renderPlayerInterface();
        }
    }

    /**
     * Removes a the latest added programmed move.
     */
    public void removeProgrammedMove(){
        if(!programmedMoves.isEmpty()){
            programmedMoves.remove();
            endable = false;
            renderPlayerInterface();
        }
    }

    public void takeDamage(int damage) {
        hitpoints -= damage;
        if(hitpoints < 1) {
            dead = true;
        }
        testSound.loop(1);
    }

    public void heal(int hp){
	hitpoints += hp;
    }

    public int getDamage() {
        return damage;
    }

}

