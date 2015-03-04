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
     * <h1>initBoard</h1><br>
     *     <p>Initialize the board with all empty tiles</p>
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


    public void update(){

        updateRobots();

        updateTiles();

        notifyListeners();

    }

    /**
     * <h1>updateTiles</h1><br>
     *     <p>Goes through all the tiles on the board and updates them.
     *     So the tiles for example, shoot, moves or kills the robots</p>
     */
    public void updateTiles() {

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++) {

                tiles[row][col].update();

            }

        }

    }

    /**
     * <h1>draw</h1><br>
     *     <p>Draws the board by going through all the tiles and draws them.</p>
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
     * <h1>notifyListeners</h1><br>
     *     <p>Notifies all the boardListeners by calling their boardChanged method.</p>
     */
    private void notifyListeners() {
        for(BoardListener listener : listeners) {
            listener.boardChanged();
        }
    }

    // Robot stuff

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

    private void moveRobot(AbstractRobot robot) {

        robot.getNextMove().run(); // sets tempx and tempy

        if(canMoveRobot(robot)){ // check if move to tempx and tempy is possible
            robot.place(robot.getTempX(), robot.getTempY()); // sets x to tempx and y to tempy
            notifyListeners();
        }

    }

    private void robotAttack(AbstractRobot robot) {
        notifyListeners();
    }

    private void updateRobots() {

        for(AbstractRobot robot : robots){

            moveRobot(robot);

            robotAttack(robot);

            notifyListeners();

        }

    }

}
