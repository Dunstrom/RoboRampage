package entity;

import board.SettingsFailiureException;
import io.InterfaceComponent;
import io.Settings;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 * Abstract class for all robots in the game.
 */
public abstract class AbstractRobot extends AbstractBoardEntity {

    //For the collisionhandling
    protected int tempX;
    protected int tempY;
    protected int tileSize;
    protected boolean endable;
    protected boolean done;

    //io
    protected BufferedImage buttonSprite;
    protected BufferedImage hpBarSprite;
    protected InterfaceComponent playerInterface;
    protected BufferedImage robotSprite;
    protected BufferedImage choosenMoveSprite;
    protected Runnable shootSound;

    // Stats
    protected String name;
    protected LinkedList<Move> programmedMoves;
    /**
     * The maximum amount of moves allowed for one entity.robot
     */
    public final static int MAX_QUEUED_MOVES = 3;
    protected int hitpoints;
    protected int maxHitpoints;
    protected int damage;
    protected boolean dead;
    protected List<Flag> flags;


    // Getters
    public Iterable<Move> getProgrammedMoves() {
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

    public String getName() {
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

    public boolean isDead() {
        return dead;
    }

    public int getFlagCount() {
        return flags.size();
    }

    public BufferedImage getHpBarSprite() {
        return hpBarSprite;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public BufferedImage getRobotSprite() {
        return robotSprite;
    }

    public Iterable<Flag> getFlags() {
        return flags;
    }

    // Setters
    public void setEndable(boolean bl) {
        endable = bl;
    }

    public void setTempX(int newTempX) {
        tempX = newTempX;
    }

    public void setTempY(int newTempY) {
        tempY = newTempY;
    }

    public void kill() {
        dead = true;
    }

    public void pickFlag(Flag flag) {
        if (!flags.contains(flag)) {
            flags.add(flag);
        }
    }

    protected AbstractRobot(final int x, final int y, final Orientation orientation, final String name, final int hitpoints, Settings settings, String spritePath) throws SettingsFailiureException {
        super(x, orientation, y);

        //Setup robot
        maxHitpoints = hitpoints;
        this.hitpoints = maxHitpoints;
        damage = 1;
        dead = false;
        this.name = name;
        programmedMoves = new LinkedList<>();
        flags = new ArrayList<>();
        tileSize = settings.getTileSize();
        done = false;

        //Setup player interface
        buttonSprite = loadImage("../Resources/Button.png");
        hpBarSprite = loadImage("../Resources/hp_bar.png");
        robotSprite = loadImage(spritePath);
        choosenMoveSprite = loadImage("../Resources/Brown_Button.png");
        playerInterface = new InterfaceComponent(this, settings);

        shootSound = loadSound("../Resources/testljud.wav");

    }



    /**
     * Constructs a string and sets it to the JLabel displayedMoves.
     */
    public void renderPlayerInterface() {

        playerInterface.paintImmediately(0, 0, playerInterface.getWidth(), playerInterface.getWidth());

    }

    /**
     * Adds a move to the list of pre programmed moves.
     *
     * @param move a Runnable that is supposed to be executed in the sequential move phase of the game.
     */
    protected void addProgrammedMove(Move move) {

        if (programmedMoves.size() < MAX_QUEUED_MOVES) {
            programmedMoves.add(move);
            if (programmedMoves.size() == MAX_QUEUED_MOVES) {
                endable = true;
            }
            renderPlayerInterface();
        }
    }

    /**
     * Removes a the latest added programmed move.
     */
    public void removeProgrammedMove() {
        if (!programmedMoves.isEmpty()) {
            int i = programmedMoves.size()-1;
            programmedMoves.remove(i);
            endable = false;
            renderPlayerInterface();
        }
    }

    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints < 1) {
            dead = true;
        }
    }

    public void heal(int hp) {
        hitpoints += hp;
        if(hitpoints > maxHitpoints) {
            hitpoints = maxHitpoints;
        }
    }

    public int getDamage() {
        new Thread(shootSound).start();
        return damage;
    }

}

