package board;

/**
 * A Exception to throw when something has gone wrong with reading the settings of the game. The error message is used so you may know what setting is wrong.
 */
public class SettingsFailiureException extends Exception {

    public SettingsFailiureException(String message) {
        super(message);
    }

}
