package entity;

import board.SettingsFailiureException;
import game.Player;
import io.Settings;

/**
 * Created by Hampus on 2015-05-05.
 */
public final class ZigZagRobotFactory extends AbstractRobotCreator {

    public ZigZagRobotFactory(Settings settings) {
        super(settings);
    }

    @Override
    public  AbstractRobot createRobot(Player player) throws SettingsFailiureException {
        return new ZigZagRobot(player.getStartX(), player.getStartY(), player.getOrientation(), player.getName(), player.getSpriteFileName(), settings);
    }
}
