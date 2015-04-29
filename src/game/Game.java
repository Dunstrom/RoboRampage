package game;

import board.*;
import entity.ZigZagRobot;
import io.GameFrame;
import entity.AbstractRobot;
import entity.StandardRobot;
import io.Settings;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/** Keeps track of game stuff, initialize the game, runs it, ends it and keep tracks of who has won. */
public class Game implements BoardListener {

    private Board board;
    private List<AbstractRobot> robots;
    private List<Player> players;
    private List<DoneListener> listeners;
    private AbstractRobot currentRobot;
    private GameFrame gameFrame;
    private String winner = "No one...";
    private Settings settings;
    private boolean gameOver = false;

    public String getWinner() {
        return winner;
    }

    public void addDoneListener(DoneListener listener) {
        listeners.add(listener);
    }

    public Game(List<Player> players, GameFrame frame, Settings settings) throws SettingsFailiureException {
        this.players = players;
        this.settings = settings;
        board =  new Board(settings);
        board.addBoardListener(this);
        robots = new ArrayList<>();
        currentRobot = null;
        gameFrame = frame;
        listeners = new ArrayList<>();
    }

    public void startGame() throws SettingsFailiureException{
        gameOver = false;
        makeRobots();
        currentRobot = robots.get(0);
        gameFrame.runGameScreen(board, currentRobot.getPlayerInterface());
        run();
    }

    private void makeRobots() throws SettingsFailiureException {
        for (Player player : players) {
            int tileSize = settings.getTileSize();
            AbstractRobot robot;
            if(player.getRobotType().equals("Standard")){
                robot = new StandardRobot(player.getStartCol()*tileSize, player.getStartRow() * tileSize, player.getOrientation(), player.getName(), player.getSpriteFileName(), settings);
            }else {
                robot = new ZigZagRobot(player.getStartCol()*tileSize, player.getStartRow() * tileSize, player.getOrientation(), player.getName(), player.getSpriteFileName(), settings);
            }
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
	        }
	    else{
		    executeTurn();
		    currentRobot = robots.get(0);
	        }
        }
        if(!gameOver){
            gameFrame.setActivePlayer(currentRobot.getPlayerInterface());
            gameFrame.repaintPlayerInterface();
        }
    }

    public void run() {

        final int updateTime = 500;

        final Action doOneFrame = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameOver){
                    update();
                    gameFrame.repaint();
                }
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
        for(AbstractRobot robot : robots) {//Prefer it this way because of increased readability
            if(robot.getFlagCount() == 3){
                gameOver(robot.getName());
            }
        }
    }

    private void gameOver(String winner) {
        if(!gameOver){// Makes sure this only happens once
            gameOver = true;
            this.winner = winner;
            gameDone();
        }
    }

    private void gameDone() {
        if(!listeners.isEmpty()) {
            listeners.forEach(DoneListener::whenDone);
        }
        listeners.clear();
    }

}
