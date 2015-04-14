package game;

import game.Player;
import game.PlayerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The frame in wich the menu is shown. Has all the buttons and textfields.
 */
public class Menu extends JFrame {

    private JPanel playerSelect;
    private int numberOfPlayers = 1;
    private final static int MAXPLAYERS = 4;
    private List<JTextField> playerNames;
    private List<JComboBox<String>> playerColors;
    private boolean done = false;

    public Menu() {
        super("RoboRampage");

        playerNames = new ArrayList<>();
        playerColors = new ArrayList<>();

    }

    private void addPlayer() {

        JPanel playerPanel = new JPanel();
        Label playerNr = new Label("Player " + numberOfPlayers + ":");

        JTextField playerName = new JTextField("Player " + numberOfPlayers);
        playerNames.add(playerName);
        playerName.setColumns(10);

        JButton playerRobot = new JButton("Robot");
        String[] colors = {"Gray", "Blue", "Yellow", "Green", "Red"};
        JComboBox<String> playerColor = new JComboBox<>(colors);
        playerColor.setSelectedIndex(numberOfPlayers - 1);
        playerColors.add(playerColor);

        playerPanel.add(playerNr);
        playerPanel.add(playerName);
        playerPanel.add(playerRobot);
        playerPanel.add(playerColor);

        playerSelect.add(playerPanel);
    }

    public List<Player> setUpPlayers() {

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

        addPlayerBtn.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numberOfPlayers < MAXPLAYERS) {
                    numberOfPlayers++;
                    addPlayer();

                    requestFocus();
                    setVisible(true);
                }
            }
        });

        startBtn.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNames.size() > 1) {
                    done = true;
                }
            }
        });

        addPlayerBtn.setText("Add Player");
        startBtn.setText("START");

        while (true) {
            if (done) {
                return PlayerFactory.createPlayers(playerNames, playerColors, numberOfPlayers);
            }else {
                System.out.println();
            }
        }

    }

}
