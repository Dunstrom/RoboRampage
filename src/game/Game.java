package game;

import board.AbstractTile;
import board.Board;
import io.GameFrame;
import robot.AbstractRobot;
import robot.Orientation;
import robot.TestRobot;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Keeps track of game stuff, initialize the game and such.
 */
public class Game {

    private Board board;
    private List<AbstractRobot> players;
    private int numberOfPlayers = 2;
    private AbstractRobot currentPlayer;
    private GameFrame game;

    public Game() {
        startGame();
    }

    /**
     * Starts the game by creating both bord and a frame.
     */
    private void startGame() {
        int boardWidth = 20; //amount of tiles the board is wide
        int boardHeight = 10;
        board =  new Board(boardWidth, boardHeight);
	players = new ArrayList<AbstractRobot>();

	addPlayers(numberOfPlayers);

	currentPlayer = players.get(0);
        game = new GameFrame(board, currentPlayer);

    }

    /**
     * Adds al the players to the game.
     * @param numberOfPlayers number of players in this game
     */
    private void addPlayers(int numberOfPlayers){
	for (int i = 0; i < numberOfPlayers; i++) {
	    AbstractRobot testRobot = new TestRobot((3 + i*2) * AbstractTile.getTileSize(), 3 * AbstractTile.getTileSize(), Orientation.NORTH, "Player 1");
	    players.add(testRobot);
	    board.addRobot(testRobot);
	}
    }

    /**
     * Finishes the turn by telling the board to update and resets the turn.
     */
    private void executeTurn() {

        board.update();

	for (AbstractRobot player : players) {
	    player.setDone(false);
	}
    }

    /**
     * Updates the game every tick of the timer
     */
    public void update() {
        if(currentPlayer.getDone()){
	    if(players.indexOf(currentPlayer) < players.size()-1){//Are there more players left this turn?
		currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
		game.setActivePlayer(currentPlayer);
	    }
	    else{
		executeTurn();
		currentPlayer = players.get(0);
		game.setActivePlayer(currentPlayer);
	    }
        }
    }

    /**
     * Main method that runs the game by using a timer.
     * @param args String[] with arguments
     */
    public static void main(String[] args) {

        final int updateTime = 500;

        final Game game = new Game();

        final Action doOneFrame = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
            }
        };

        final Timer timer = new Timer(updateTime, doOneFrame);
        timer.setCoalesce(true);
        timer.start();

    }

}
