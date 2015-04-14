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

    public static List<Player> createPlayers(List<JTextField> playerNames, List<JComboBox<String>> playerColors, int numberOfPlayers) {
	List<Player> players = new ArrayList<>();
	for(int i = 0; i < numberOfPlayers; i++) {
		//Name
	    String name = playerNames.get(i).getText();

		//Starting position
	    int startCol = i % 2;
	    int startRow = i * 2;
	    if(startRow > Game.BOARD_HEIGHT) {
		throw new IllegalArgumentException("To Many players for the board");
	    }

		//Color "Gray", "Blue", "Yellow", "Green", "Red"
		String spriteFileName = "Robot.png";
		if(!playerColors.get(i).getSelectedItem().getClass().equals(String.class)){
			throw new IllegalArgumentException("Color key is not a string.");
		}
		switch ((String)playerColors.get(i).getSelectedItem()) {
			case("Blue"):
				spriteFileName = "BlueRobot.png";
				break;
			case("Yellow"):
				spriteFileName = "YellowRobot.png";
				break;
			case("Green"):
				spriteFileName = "GreenRobot.png";
				break;
			case("Red"):
				spriteFileName = "RedRobot.png";
				break;
		}

	    players.add(new Player(name, startCol, startRow, spriteFileName));
	}
	return players;
    }

}
