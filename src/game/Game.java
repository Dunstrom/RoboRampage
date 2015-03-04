package game;

import board.AbstractTile;
import board.Board;
import io.GameFrame;
import robot.TestRobot;

import javax.swing.*;

/**
 * <h1>Game</h1><br>
 *     <p>Keeps track of gamestuff, initialize the game and such.</p>
 */
public class Game {

    private JFrame frame;
    private Board board;
    private int boardWidth = 20;
    private int boardHeight = 10;
    private Player testPlayer;

    public Game() {
        startGame();
    }

    /**
     * <h1>startGame</h1><br>
     *     <p>Starts the game by creating both bord and a frame.</p>
     */
    private void startGame() {
        board =  new Board(boardWidth, boardHeight);
        TestRobot testRobot = new TestRobot(3 * AbstractTile.getTileSize(), 3 * AbstractTile.getTileSize(), 'S');
        board.addRobot(testRobot);
        testPlayer = new Player("testPlayer", testRobot);
        frame = new GameFrame(board, testPlayer);

    }

    /**
     * <h1>executeTurn</h1><br>
     *     <p>Finishes the turn by telling the board to update and resets the turn.</p>
     */
    private void executeTurn() {

        board.update();

        testPlayer.setDone(false);
    }

    /**
     * <h1>update</h1><br>
     *     <p>Updates the game every tick of the timer<p/>
     */
    public void update() {
        if(testPlayer.getDone()){
            executeTurn();
        }
    }

}
