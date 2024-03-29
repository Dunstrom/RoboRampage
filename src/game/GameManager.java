package game;

import board.SettingsFailiureException;
import io.GameFrame;
import io.Loader;
import io.PlayerSelectComponent;
import io.Settings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class handles the games states, switching between menu, game and the winscreen. Also responsible for starting and restarting the game.
 */
public class GameManager implements DoneListener {

    private GameState state;
    private List<Player> players;
    private PlayerSelectComponent playerSelectMenu = null;
    private Game game;
    private String winner;
    private Settings settings = null;
    private GameFrame gameFrame;
    private Thread bgMusicThread;

    public GameManager() {
        bgMusicThread = new Thread(Loader.loadBgMusic("../Resources/Hitman.wav"));
        try {
            settings = new Settings("settings");
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
        players = new ArrayList<>();
        gameFrame = new GameFrame();
        state = GameState.MENU;
        game = null;
        winner = "No one, yet...";
    }

    public void startGame() {
        state = GameState.MENU;
        bgMusicThread.start();
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
        try {
            playerSelectMenu = gameFrame.runPlayerSelectScreen(settings);
            playerSelectMenu.addListener(this);
        } catch (SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
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
                players = playerSelectMenu.getPlayers();
                state = GameState.GAME;
                break;
            case GAME:
                winner = game.getWinner();
                state = GameState.WINSCREEN;
                break;
            case WINSCREEN:
                state = GameState.MENU;
                gameFrame.setVisible(false);
                break;
        }
        runState();
    }

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.startGame();
    }

    private void handleSettingsExceptions(Exception e) {
        JFrame frame = new JFrame("Oops!");
        JOptionPane.showMessageDialog(frame,
            e.getMessage(),
            "Settings Error",
            JOptionPane.ERROR_MESSAGE);
        frame.setVisible(true);
        frame.pack();
        e.printStackTrace();
        System.exit(0);

    }

}
