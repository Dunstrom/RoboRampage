package game;

import io.PlayerInterfaceComponent;
import robot.AbstractRobot;

/**
 * Created by Hampus on 2015-03-02.
 */
public class Player {

    private String name;
    private AbstractRobot robot;
    private PlayerInterfaceComponent component;

    public Player(String name, AbstractRobot robot) {

        this.name = name;
        this.robot = robot;

        component = new PlayerInterfaceComponent(this);

    }



}
