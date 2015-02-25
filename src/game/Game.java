package game;

import board.Board;
import io.Frame;

import javax.swing.*;

/**
 * <h1>Game</h1><br>
 *     <p>Keeps track of gamestuff, initialize the game and such.</p>
 */
public class Game {

    private JFrame frame;
    private Board board;
    private int width = 10;
    private int height = 10;

    public Game() {
        startGame();
    }

    public void startGame() {
        board =  new Board(width, height);
        frame = new Frame(board);
    }

    public void update() {}

}
