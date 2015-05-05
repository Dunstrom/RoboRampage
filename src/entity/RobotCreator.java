package entity;

import board.SettingsFailiureException;
import game.Player;

/**
 * A interface for the games factories.
 */
public interface RobotCreator {

    AbstractRobot createRobot(Player player) throws SettingsFailiureException;

}
