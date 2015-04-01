package game;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates players with information from the menu and generates the players startpositions.
 */
public final class PlayerFactory {

    private PlayerFactory() {

    }

    public static ArrayList<Player> createPlayers(List<JTextField> playerNames, int numberOfPlayers) {
	ArrayList<Player> players = new ArrayList<>();
	for(int i = 0; i < numberOfPlayers; i++) {
	    String name = playerNames.get(i).getText();
	    int startCol = i % 2;
	    int startRow = i * 2;
	    if(startRow > Game.BOARD_HEIGHT) {
		throw new IllegalArgumentException("To Many players for the board");
	    }
	    players.add(new Player(name, startCol, startRow));
	}
	return players;
    }

}
