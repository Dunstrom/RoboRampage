package game;

import board.Tile;
import board.Board;
import board.BoardListener;
import io.GameFrame;
import entity.AbstractRobot;
import entity.TestRobot;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.AbstractAction;

/**
 * Keeps track of game stuff, initialize the game and such.
 */
public class Game implements BoardListener {

    private Board board;
    private List<AbstractRobot> robots;
    private List<Player> players;
    private List<DoneListener> listeners;
    private AbstractRobot currentRobot;
    private GameFrame gameFrame;
    final static int BOARD_WIDTH = 20;
    final static int BOARD_HEIGHT = 10;
    private String winner = "No one...";

    public String getWinner() {
        return winner;
    }

    public void addDoneListener(DoneListener listener) {
        listeners.add(listener);
    }

    public Game(List<Player> players) {
        this.players = players;
        board =  new Board(BOARD_WIDTH, BOARD_HEIGHT);
        board.addBoardListener(this);
        robots = new ArrayList<>();
        currentRobot = null;
        gameFrame = null;
        listeners = new ArrayList<>();
    }

    public void startGame(){
        makeRobots();
        currentRobot = robots.get(0);
        gameFrame = new GameFrame(board, currentRobot.getMainPanel());
        board.addBoardListener(gameFrame);
        run();
    }

    private void makeRobots(){
        for (Player player : players) {
            int tileSize = Tile.TILE_SIZE;
            AbstractRobot robot = new TestRobot(player.getStartCol()*tileSize, player.getStartRow() * tileSize, player.getOrientation(), player.getName(), player.getSpriteFileName());
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

    /** Checks the games winning conditions */
    @Override public void boardChanged() {
        if(robots.size() == 1){
            String winner = robots.get(0).getName();
            gameOver(winner);
        }
        else if (robots.isEmpty()){
            gameOver("No one");
        }

        checkRobotGotAllFlags();

    }

    private void checkRobotGotAllFlags() {
        for(AbstractRobot robot : robots) {
            if(robot.getFlagCount() == 3){
                gameOver(robot.getName());
            }
        }
    }

    private void gameOver(String winner) {
        this.winner = winner;
        gameDone();
        /*Object[] options = {"New Game", "Quit"};
        int optionChosen = JOptionPane.showOptionDialog(gameFrame,
            "GAME OVER\n" + winText,

            "GAME OVER",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        if (optionChosen == 0){

        }
        else if (optionChosen == 1){
            System.exit(0);
        }*/
    }

    private void gameDone() {
        for(DoneListener listener : listeners) {
            listener.whenDone();
        }
    }

}
