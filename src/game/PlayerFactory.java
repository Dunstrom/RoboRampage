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

    public static List<Player> createPlayers(List<JTextField> playerNames, List<JComboBox<String>> playerColors, List<JComboBox<String>> playerRobots, int numberOfPlayers, int boardHeight) {
	List<Player> players = new ArrayList<>();
	for(int i = 0; i < numberOfPlayers; i++) {
		//Name
	    String name = playerNames.get(i).getText();

		//Starting position
	    int startCol = i % 2;
	    int startRow = i * 2;
	    assert startRow < boardHeight;

		//Color "Gray", "Blue", "Yellow", "Green", "Red"
		String spriteFileName = "Robot.png";
		assert playerColors.get(i).getSelectedItem().getClass().equals(String.class);
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
		assert playerRobots.get(i).getSelectedItem().getClass().equals(String.class);

	    players.add(new Player(name, startCol, startRow, spriteFileName, (String)playerRobots.get(i).getSelectedItem()));
	}
	return players;
    }

}
