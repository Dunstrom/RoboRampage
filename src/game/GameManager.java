package game;

import board.SettingsFailiureException;
import io.GameFrame;
import io.Settings;

import java.util.ArrayList;
import java.util.List;


/**
 * This class handles the games states, switching between menu, game and the winscreen. Also responsible for starting and restarting the game.
 */
public class GameManager implements DoneListener {

    private GameState state;
    private List<Player> players;
    private Menu menu = null;
    private Game game;
    private String winner;
    private Settings settings = null;
    private GameFrame gameFrame;

    public GameManager() {
        try {
            settings = new Settings("settings");
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
        players = new ArrayList<>();
        try {
            menu = new Menu(settings);
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
        gameFrame = new GameFrame();
        state = GameState.MENU;
        game = null;
        winner = "No one, yet...";
    }

    public void startGame() {
        state = GameState.MENU;
        runState();
    }

    private void runState(){
        switch (state) {
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
        try{
            game = new Game(players, gameFrame, settings);
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
        game.addDoneListener(this);
        try {
            game.startGame();
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }

    }

    private void runWinScreen() {
        DoneListener[] listeners = {this};
        gameFrame.runwinScreen(winner, listeners);
    }

    public void whenDone() {
        switch (state) {
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

    private void handleSettingsExceptions(Exception e) {
        e.printStackTrace();
        System.exit(0);
    }

}
