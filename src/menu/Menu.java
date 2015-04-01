package menu;

import game.Game;
import game.PlayerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu
{

    public Menu() {

    }

    public void startGame(ArrayList<JTextField> playerNames, int numberOfPlayers) {
        Game game = new Game(PlayerFactory.createPlayers(playerNames, numberOfPlayers));
        game.run();

    }

    public static void main(String[] args) {

        Menu menu = new Menu();

	JFrame menuFrame = new MenuFrame(menu);
    }

}
