package game;

import robot.Orientation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFactory {


    private Orientation startOrientation = Orientation.WEST;

    public static ArrayList<Player> createPlayers(ArrayList<JTextField> playerNames, int numberOfPlayers) {
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
