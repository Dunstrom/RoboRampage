package menu;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    private JPanel playerSelect;

    public static void main(String[] args) {
	JFrame menu = new MenuFrame();
    }

    public MenuFrame() {
	super("RoboRampage");


	final int height = 400;
	final int width = 400;

	setLayout(new BorderLayout());

	setResizable(false);

	Label title = new Label("ROBO RAMPAGE");
	JPanel playerSelect = new JPanel();
	JButton addPlayerBtn = new JButton("Add Player");
	JButton startBtn = new JButton("START");

	add(title, BorderLayout.PAGE_START);

	playerSelect.add(addPlayer(1));

	playerSelect.add(addPlayerBtn);

	add(playerSelect, BorderLayout.CENTER);

	add(startBtn, BorderLayout.PAGE_END);

	setSize(width, height);
 	requestFocus();
 	setVisible(true);

    }

    private JPanel addPlayer(int playerNumber){

	JPanel playerPanel = new JPanel();
	Label playerNr = new Label("Player " + playerNumber + ":");

	JTextField playerName = new JTextField("Name");
	playerName.setColumns(10);

	JButton playerRobot = new JButton("Robot");
	JButton playerColor = new JButton("Color");

	JButton addPlayerBtn = new JButton("Add Player");

	playerPanel.add(playerNr);
	playerPanel.add(playerName);
	playerPanel.add(playerRobot);
	playerPanel.add(playerColor);

	return playerPanel;
    }

}
