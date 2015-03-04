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
        frame = new GameFrame(board);
        testPlayer = new Player("testPlayer", new TestRobot(3 * AbstractTile.getTileSize(), 3 * AbstractTile.getTileSize(), 'S'));
    }

    /**
     * <h1>update</h1><br>
     *     <p>Updates the game every tick of the timer<p/>
     */
    public void update() {}

}
