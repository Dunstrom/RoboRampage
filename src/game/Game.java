package game;

import board.Tile;
import board.Board;
import board.BoardListener;
import io.GameFrame;
import robot.AbstractRobot;
import robot.TestRobot;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/**
 * Keeps track of game stuff, initialize the game and such.
 */
public class Game implements BoardListener {

    private Board board;
    private List<AbstractRobot> robots;
    private List<Player> players;
    private AbstractRobot currentRobot;
    private GameFrame gameFrame;
    final static int BOARD_WIDTH = 20;
    final static int BOARD_HEIGHT = 10;


    public Game(List<Player> players) {
        this.players = players;
        board =  new Board(BOARD_WIDTH, BOARD_HEIGHT);
        board.addBoardListener(this);
        robots = new ArrayList<>();
        makeRobots();
        currentRobot = robots.get(0);
        gameFrame = new GameFrame(board, currentRobot.getMainPanel());
    }


    private void makeRobots(){
        for (Player player : players) {
            int tileSize = Tile.TILE_SIZE;
            AbstractRobot robot = new TestRobot(player.getStartCol()*tileSize, player.getStartRow() * tileSize, player.getOrientation(), player.getName());
            robots.add(robot);
        }
        board.setRobots(robots);
    }

    /**
     * Finishes the turn by telling the board to update and resets the turn.
     */
    private void executeTurn() {

        board.update();

	for (AbstractRobot player : robots) {
	    player.setDone(false);
	}

    }

    /**
     * Updates the gameFrame every tick of the timer
     */
    public void update() {
        if(currentRobot.getDone()){
	        if(robots.indexOf(currentRobot) < robots.size()-1){//Are there more robots left this turn?
		        currentRobot = robots.get(robots.indexOf(currentRobot) + 1);
		        gameFrame.setActivePlayer(currentRobot.getMainPanel());
	        }
	    else{
		    executeTurn();
		    currentRobot = robots.get(0);
		    gameFrame.setActivePlayer(currentRobot.getMainPanel());
	        }
        }

        gameFrame.repaintPlayerInterface();

    }

    public void run() {

        final int updateTime = 500;

        final Action doOneFrame = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        update();
                    }
        };

        final Timer timer = new Timer(updateTime, doOneFrame);
        timer.setCoalesce(true);
        timer.start();
    }

    @Override public void boardChanged() {
        if(robots.size() == 1){
            String winner = robots.get(0).getName();
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
        else if (robots.isEmpty()){
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
