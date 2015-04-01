package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFactory {

    public static ArrayList<Player> createPlayers(ArrayList<JTextField> playerNames, int numberOfPlayers) {
	ArrayList<Player> players = new ArrayList<>();
	for(int i = 1; i <= numberOfPlayers; i++) {
	    String name = playerNames.get(i).getText();
	    players.add(new Player(name));
	}
	return players;
    }

}
