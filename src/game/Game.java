package game;

import board.AbstractTile;
import board.Board;
import io.GameFrame;
import robot.TestRobot;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Keeps track of game stuff, initialize the game and such.
 */
public class Game {

    private Board board;
    private Player testPlayer;

    public Game() {
        startGame();
    }

    /**
     * Starts the game by creating both bord and a frame.
     */
    private void startGame() {
        int boardWidth = 20; //amount of tiles the board are wide
        int boardHeight = 10;
        board =  new Board(boardWidth, boardHeight);
        TestRobot testRobot = new TestRobot(3 * AbstractTile.getTileSize(), 3 * AbstractTile.getTileSize(), 'S');
        board.addRobot(testRobot);
        testPlayer = new Player("testPlayer", testRobot);
        new GameFrame(board, testPlayer);

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
            public void actionPerformed(ActionEvent e) {
                game.update();
            }
        };

        final Timer timer = new Timer(updateTime, doOneFrame);
        timer.setCoalesce(true);
        timer.start();

    }

}
