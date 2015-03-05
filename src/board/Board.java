package board;

import robot.AbstractRobot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Keeps tracks of all the tiles on the board.
 */
public class Board {

    private AbstractTile[][] tiles;
    private int width;
    private int height;
    private List<BoardListener> listeners;
    private List<AbstractRobot> robots;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addRobot(AbstractRobot robot){
        robots.add(robot);
    }

    public void removeRobot(AbstractRobot robot){
        robots.remove(robot);
    }

    public void addBoardListener(BoardListener bl) {
        listeners.add(bl);
    }

    public void removeBoardListener(BoardListener bl) {
        listeners.remove(bl);
    }

    public Board(int width, int height){

        this.width = width;
        this.height = height;
        initBoard(width, height);
        listeners = new ArrayList<BoardListener>();
        robots = new ArrayList<AbstractRobot>();
        notifyListeners();

    }

    // Board stuff

    /**
     * Initialize the board with all empty tiles
     * @param width an int telling the board how many tiles it should have per row.
     * @param height an int telling the board how many rows it should have.
     */
    private void initBoard(int width, int height) {

        tiles = new AbstractTile[height][width];

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++){

                tiles[row][col] = new EmptyTile(col * AbstractTile.getTileSize(), row * AbstractTile.getTileSize());

            }

        }

    }

    /**
     * Updates the board by updating robots and updating the tiles.
     */
    public void update(){

        for(int i = 0; i < AbstractRobot.getMaxQueuedMoves(); i++){

            updateRobots();

            updateTiles();

            notifyListeners();

        }

    }

    /**
     * Goes through all the tiles on the board and updates them.
     * So the tiles for example, shoot, moves or kills the robots
     */
    public void updateTiles() {

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++) {

                tiles[row][col].update();

            }

        }

    }

    /**
     * Draws the board by going through all the tiles and draws them.
     * @param g a Graphics object needed to draw the individual tile.
     */
    public void draw(Graphics g) {

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++) {

                tiles[row][col].draw(g);

            }

        }

        for(AbstractRobot robot : robots) {
            robot.draw(g);
        }

    }

    /**
     * Notifies all the boardListeners by calling their boardChanged method.
     */
    private void notifyListeners() {
        for(BoardListener listener : listeners) {
            listener.boardChanged();
        }
    }

    // Robot stuff

    /**
     * Checks if the robot can be moved to it's temporary position.
     * @param robot a Abstract robot that is going to be moved.
     * @return a boolean, true if the robot is movable false if it isn't.
     */
    private boolean canMoveRobot(AbstractRobot robot){


        if(!robots.contains(robot)){// Is robot on board
            throw new IllegalArgumentException("Robot not on the board");
        }

        for (AbstractRobot otherRobot : robots) {
            if(!robot.equals(otherRobot)){
                if(otherRobot.getX() == robot.getTempX() && otherRobot.getY() == robot.getTempY()){
                    return false;//Todo: Add push robot stuff!
                }
            }
        }
        return true;

    }

    /**
     * Get's the next move programmed in to the robot and tries to move the robot.
     * @param robot the robot that is to be moved.
     */
    private void moveRobot(AbstractRobot robot) {

        robot.getNextMove().run(); // sets tempx and tempy

        if(canMoveRobot(robot)){ // check if move to tempx and tempy is possible
            robot.place(robot.getTempX(), robot.getTempY()); // sets x to tempx and y to tempy
            notifyListeners();
        }

    }

    /**
     * Makes the robot attack
     * @param robot the robot that shall attack.
     */
    private void robotAttack(AbstractRobot robot) {
        notifyListeners();
    }

    /**
     * Moves the robots as they are programmed and makes them attack.
     */
    private void updateRobots() {

        for(AbstractRobot robot : robots){

            moveRobot(robot);

            robotAttack(robot);

            notifyListeners();

        }

    }

}
