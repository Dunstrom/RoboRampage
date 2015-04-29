package entity;

/**
 * A move is what the robots use to store their movements. It extends Button because it needs to be able to have one action on buttonpress and one on execution.
 */
public interface Move extends Button {

    void execute();

}
