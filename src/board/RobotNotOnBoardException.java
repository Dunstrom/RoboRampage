package board;

/**
 * Created by Hampus on 2015-04-20.
 */
public class RobotNotOnBoardException extends Exception {
    public RobotNotOnBoardException(String message) {
        super(message);
    }
}
