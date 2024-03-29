package entity;

/**
 * A abstract class to force every move to not only be executable but also displayable to the player.
 */
public interface Button extends Runnable{

    /**
     * @return a String that symbolizes/explains the move.
     */
      String display();

}
