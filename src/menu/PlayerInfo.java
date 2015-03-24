package menu;

import robot.AbstractRobot;

import java.awt.Color;

public class PlayerInfo
{
    private String Name;
    private Color color;
    private AbstractRobot robot;

    public PlayerInfo(final String name, final Color color, final AbstractRobot robot) {
	Name = name;
	this.color = color;
	this.robot = robot;
    }
}
