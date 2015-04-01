package game;

import board.AbstractTile;
import board.Board;
import board.BoardListener;
import io.GameFrame;
import robot.AbstractRobot;
import robot.Orientation;
import robot.TestRobot;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Keeps track of game stuff, initialize the game and such.
 */
public class Game implements BoardListener {

    private Board board;
    private List<AbstractRobot> players;
    private final static int NUMBER_OF_PLAYERS = 2;
    private AbstractRobot currentPlayer;
    private GameFrame gameFrame;
    private static final Color[] PLAYER_COLORS = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
    private static final String[] PLAYER_NAMES = {"Blue", "Red", "Yellow", "Green"};


    public Game() {
        startGame();
    }

    /**
     * Starts the game by creating both bord and a frame.
     */
    private void startGame() {
        final int boardWidth = 20;
        final int boardHeight = 10;
        board =  new Board(boardWidth, boardHeight);
        board.addBoardListener(this);

        players = new ArrayList<>();
	    addPlayers(NUMBER_OF_PLAYERS);
	    currentPlayer = players.get(0);

        gameFrame = new GameFrame(board, currentPlayer.getMainPanel());

    }

    /**
     * Adds al the players to the game.
     * @param numberOfPlayers number of players in this game
     */
    private void addPlayers(int numberOfPlayers){
        for (int i = 0; i < numberOfPlayers; i++) {
            Color color = PLAYER_COLORS[i];
            String name = PLAYER_NAMES[i];
            Orientation dir = Orientation.NORTH;
            int x = (3 + i*2) * AbstractTile.getTileSize();
            int y = 3 * AbstractTile.getTileSize();
	        AbstractRobot testRobot = new TestRobot(x, y, dir, name, color);
	        players.add(testRobot);
        }
        board.setRobots(players);
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
     * Updates the gameFrame every tick of the timer
     */
    public void update() {
        if(currentPlayer.getDone()){
	        if(players.indexOf(currentPlayer) < players.size()-1){//Are there more players left this turn?
		        currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
		        gameFrame.setActivePlayer(currentPlayer.getMainPanel());
	        }
	    else{
		    executeTurn();
		    currentPlayer = players.get(0);
		    gameFrame.setActivePlayer(currentPlayer.getMainPanel());
	        }
        }

        gameFrame.repaintPlayerInterface();

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

    @Override public void boardChanged() {
        if(players.size() == 1){
            String winner = players.get(0).getName();
            Object[] options = {"New Game", "Quit"};
            int optionChosen = JOptionPane.showOptionDialog(gameFrame,
                "GAME OVER\n" +
                "The player " + winner + " has won the game!",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if (optionChosen == 0){
                //TODO: start new game
            }
            else if (optionChosen == 1){
                System.exit(0);
            }
        }
        else if (players.isEmpty()){
            Object[] options = {"New Game", "Quit"};
            int optionChosen = JOptionPane.showOptionDialog(gameFrame,
                "GAME OVER\n" +
                "All robots have been destroyed, there is no winner",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if (optionChosen == 0){
                //TODO: start new game
            }
            else if (optionChosen == 1){
                System.exit(0);
            }
        }

    }
}
