package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuFrame extends JFrame {

    private JPanel playerSelect;
    private int numberOfPlayers = 1;
    private List<JPanel> playerPanels;
    private final static int MAXPLAYERS = 4;

    public MenuFrame() {
	super("RoboRampage");
	playerPanels = new ArrayList<>();

	final int height = 400;
	final int width = 400;

	setLayout(new BorderLayout());

	setResizable(false);

	Label title = new Label("ROBO RAMPAGE");
	playerSelect = new JPanel();
	JButton addPlayerBtn = new JButton("Add Player");
	JButton startBtn = new JButton("START");

	add(title, BorderLayout.PAGE_START);

	playerSelect.add(addPlayerBtn);

	addPlayer();

	add(playerSelect, BorderLayout.CENTER);

	add(startBtn, BorderLayout.PAGE_END);

	setSize(width, height);
 	requestFocus();
 	setVisible(true);

	addPlayerBtn.setAction(new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent e) {
		if (numberOfPlayers < MAXPLAYERS) {
		    numberOfPlayers++;
		    addPlayer();

		    requestFocus();
		    setVisible(true);
		}
	    }
	});

	startBtn.setAction(new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent e) {
		for (JPanel playerPanel : playerPanels) {
		    playerPanel.getComponent(1).getText();//TODO: Använd http://stackoverflow.com/questions/11389802/after-creating-jtextfield-dynamically-how-do-i-use-gettext
		}
	    }
	});

	addPlayerBtn.setText("Add Player");
	startBtn.setText("START");
    }



    private void addPlayer(){

	JPanel playerPanel = new JPanel();
	Label playerNr = new Label("Player " + numberOfPlayers + ":");

	JTextField playerName = new JTextField("Player " + numberOfPlayers);
	playerName.setColumns(10);

	JButton playerRobot = new JButton("Robot");
	JButton playerColor = new JButton("Color");


	playerPanel.add(playerNr);
	playerPanel.add(playerName);
	playerPanel.add(playerRobot);
	playerPanel.add(playerColor);

	playerPanels.add(playerPanel);
	playerSelect.add(playerPanel);
    }

}
