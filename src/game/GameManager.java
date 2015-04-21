package game;

import board.BoardNotFoundException;
import board.SettingsFailiureException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameManager implements DoneListener {

    private GameState state;
    private List<Player> players;
    private Menu menu;
    private Game game;
    private String winner;
    private Settings settings;

    public GameManager() {
        try {
            settings = new Settings("settings");
        } catch(SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }
        players = new ArrayList<>();
        try {
            menu = new Menu(settings);
        } catch(BoardNotFoundException e) {
            handleSettingsExceptions(e);
        }

        game = null;
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
            game = new Game(players, settings);
        } catch(BoardNotFoundException | SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }

        game.addDoneListener(this);
        try {
            game.startGame();
        } catch(BoardNotFoundException | SettingsFailiureException e) {
            handleSettingsExceptions(e);
        }

    }

    private void runWinScreen() {

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
	final JFrame frame = new JFrame("Oops!");
	frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "hej", e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

}
