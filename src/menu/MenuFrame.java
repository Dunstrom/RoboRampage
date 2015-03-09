package menu;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    private int players;

    public static void main(String[] args) {
	JFrame menu = new MenuFrame();
    }

    public MenuFrame() {
	super("RoboRampage");

	setLayout(new BorderLayout());

	Label title = new Label("ROBO RAMPAGE");

	JPanel playerSelect = new JPanel();
	JPanel p1Panel = new JPanel();
	Label player1 = new Label("Player 1:");
	JTextField p1Name = new JTextField();
	JButton p1Robot = new JButton("Robot");
	JButton p1Color = new JButton("Color");

	JButton addPlayerBtn = new JButton("Add Player");

	JButton startBtn = new JButton("START");

	add(title, BorderLayout.PAGE_START);

	p1Panel.add(player1);
	p1Panel.add(p1Name);
	p1Panel.add(p1Robot);
	p1Panel.add(p1Color);

	playerSelect.add(p1Panel);
	playerSelect.add(addPlayerBtn);

	add(playerSelect, BorderLayout.CENTER);

	add(startBtn, BorderLayout.PAGE_END);

	pack();
 	requestFocus();
 	setVisible(true);

    }

}
