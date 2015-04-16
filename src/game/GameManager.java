package game;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements DoneListener {

    private GameState state;
    private List<Player> players;
    private Menu menu;
    private Game game;
    private String winner;

    public GameManager() {
	players = new ArrayList<>();
	menu = new Menu();
	game = null;
    }

    public void startGame() {
	state = GameState.MENU;
	runState();

    }

    private void runState() {
	switch(state) {
	    case MENU:
		runMenu();
		break;
	    case GAME:
		runGame();
		break;
	    case WINSCREEN:
		runWinScreen();
		break;
	}
    }

    private void runMenu() {
	menu.addListener(this);
	menu.setUpPlayers();
    }

    private void runGame() {
	game = new Game(players);
	game.startGame();
    }

    private void runWinScreen() {

    }

    public void whenDone() {
	switch(state) {
	    case MENU:
		players = menu.getPlayers();
		state = GameState.GAME;
		menu.dispose();
		break;
	    case GAME:
		winner = game.getWinner();
		state = GameState.WINSCREEN;
		break;
	    case WINSCREEN:
		state = GameState.MENU;
		break;
	}
	runState();
    }

    public static void main(String[] args) {
	GameManager gm = new GameManager();
	gm.startGame();
    }

}
