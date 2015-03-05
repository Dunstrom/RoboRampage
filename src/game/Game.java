package game;

import board.AbstractTile;
import board.Board;
import io.GameFrame;
import robot.TestRobot;

import javax.swing.*;

/**
 * Keeps track of gamestuff, initialize the game and such.
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
     * Starts the game by creating both bord and a frame.
     */
    private void startGame() {
        board =  new Board(boardWidth, boardHeight);
        TestRobot testRobot = new TestRobot(3 * AbstractTile.getTileSize(), 3 * AbstractTile.getTileSize(), 'S');
        board.addRobot(testRobot);
        testPlayer = new Player("testPlayer", testRobot);
        frame = new GameFrame(board, testPlayer);

    }

    /**
     * Finishes the turn by telling the board to update and resets the turn.
     */
    private void executeTurn() {

        board.update();

        testPlayer.setDone(false);
    }

    /**
     * Updates the game every tick of the timer
     */
    public void update() {
        if(testPlayer.getDone()){
            executeTurn();
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
            public void actionPerformed(java.awt.event.ActionEvent e) {
                game.update();
            }
        };

        final Timer timer = new Timer(updateTime, doOneFrame);
        timer.setCoalesce(true);
        timer.start();

    }

}
