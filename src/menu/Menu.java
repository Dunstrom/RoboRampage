package menu;

import game.Game;
import game.PlayerFactory;

import javax.swing.*;
import java.util.List;

/**
 * Handles the games menu.
 */
public class Menu {

    public void startGame(List<JTextField> playerNames, List<JComboBox<String>> playerColors, int numberOfPlayers) {
        Game game = new Game(PlayerFactory.createPlayers(playerNames, playerColors, numberOfPlayers));
        game.run();

    }

    public static void main(String[] args) {

        Menu menu = new Menu();

	MenuFrame menuFrame = new MenuFrame(menu);
    }

}
