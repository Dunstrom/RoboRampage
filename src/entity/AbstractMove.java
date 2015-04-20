package entity;

/**
 * A abstract class to force every move to not only be executable but also displayable to the player.
 */
public interface AbstractMove extends Runnable{

    /**
     * @return a String that symbolizes the move.
     */
      String display();

}
