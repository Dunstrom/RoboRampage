package entity;

import io.Settings;

/**
 * Created by Hampus on 2015-05-05.
 */
public abstract class AbstractRobotCreator implements RobotCreator {

    protected Settings settings;

    protected AbstractRobotCreator(Settings settings){
        this.settings = settings;
    }

}
