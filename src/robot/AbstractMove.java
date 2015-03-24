package robot;

/**
 * A abstract class to force every move to not only be executable but also displayable to the player.
 */
public abstract class AbstractMove implements Runnable{

    /**
     * @return a String that symbolizes the move.
     */
    public abstract String display();

}
