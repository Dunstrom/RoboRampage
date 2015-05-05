package game;

import board.SettingsFailiureException;
import io.Settings;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates players with information from the menu and generates the players startpositions.
 */
public final class PlayerFactory  {

    private PlayerFactory() {

    }

    public static List<Player> createPlayerFactory(List<JTextField> playerNames, List<JComboBox<String>> playerColors, List<JComboBox<String>> playerRobots, int numberOfPlayers, Settings settings) throws SettingsFailiureException {
	List<Player> players = new ArrayList<>();
	List<int[]> playerPos = settings.getPlayerPositions();
 	for(int i = 0; i < numberOfPlayers; i++) {
		//Name
	    String name = playerNames.get(i).getText();

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

	    players.add(new Player(name, playerPos.get(i)[0], playerPos.get(i)[1], spriteFileName, (String)playerRobots.get(i).getSelectedItem(), settings));
	}
	return players;
    }
}
