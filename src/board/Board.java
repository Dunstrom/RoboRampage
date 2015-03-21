package board;

import robot.AbstractRobot;
import robot.Orientation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
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
        listeners = new ArrayList<>();
        robots = new ArrayList<>();
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

            removeDeadRobots();

        }

    }

    /**
     * Removes the dead robots from the robotlist.
     */
    private void removeDeadRobots() {

        Collection<AbstractRobot> toBeRemoved = new ArrayList<>();

        for(AbstractRobot robot : robots){
            if(robot.isDead()){
                toBeRemoved.add(robot);
            }
        }

        if(!toBeRemoved.isEmpty()) {
            for(AbstractRobot robot : toBeRemoved) {
                removeRobot(robot);
            }
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
        notifyListeners();
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
     * Checks if the robot can be moved to it's temporary position. If there is another robot in the way then push it.
     * @param robot a Abstract robot that is going to be moved.
     * @return a boolean, true if the robot is movable false if it isn't.
     */
    private boolean canMoveRobot(AbstractRobot robot){


        if(!robots.contains(robot)){// Is robot on board
            throw new IllegalArgumentException("Robot not on the board");
        }
        else if(robot.getTempX() < 0 || robot.getTempY() < 0 || robot.getTempX() >= width*AbstractTile.getTileSize() || robot.getTempY() >= height*AbstractTile.getTileSize()){
            return false;
        }

        for (AbstractRobot otherRobot : robots) {
            if(!robot.equals(otherRobot)){
                if(otherRobot.getX() == robot.getTempX() && otherRobot.getY() == robot.getTempY()){

                    int x = otherRobot.getX();
                    int y = otherRobot.getY();
                    int oneTile = AbstractTile.getTileSize();

                    switch (robot.getOrientation()){

                        case NORTH:
                            otherRobot.setTempX(x);
                            otherRobot.setTempY(y - oneTile);
                            break;
                        case SOUTH:
                            otherRobot.setTempX(x);
                            otherRobot.setTempY(y + oneTile);
                            break;
                        case WEST:
                            otherRobot.setTempX(x - oneTile);
                            otherRobot.setTempY(y);
                            break;
                        case EAST:
                            otherRobot.setTempX(x + oneTile);
                            otherRobot.setTempY(y);
                            break;

                    }

                    if(canMoveRobot(otherRobot)){ // check if move to tempx and tempy is possible
                        otherRobot.place(otherRobot.getTempX(), otherRobot.getTempY()); // sets x to tempx and y to tempy
                        notifyListeners();
                    }

                    return true;
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
     * Finds out if theres a robot at a certain tile.
     * @param row an int that tells which row to look.
     * @param col an int that tells which col to look.
     * @return a AbstractRobot if there is a robot else returns null
     */
    private AbstractRobot getRobotAt(int row, int col) {
        for(AbstractRobot robot : robots){
            int robotRow = robot.getY() / AbstractTile.getTileSize();
            int robotCol = robot.getX() / AbstractTile.getTileSize();
            if(robotCol == col && robotRow == row) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Makes the robot attack
     * @param robot the robot that shall attack.
     */
    private void robotAttack(AbstractRobot robot) {

        Orientation orientation = robot.getOrientation();
        int y = robot.getY();
        int x = robot.getX();
        int tileSize = AbstractTile.getTileSize();

        switch(orientation){

            case NORTH:
                for(int yToCheck = y-tileSize; yToCheck > 0; yToCheck-=tileSize){
                    if(tiles[yToCheck / tileSize][x / tileSize].isBlocking()){
                        break;
                    }
                    int row = yToCheck / tileSize;
                    int col = x / tileSize;
                    AbstractRobot target = getRobotAt(row, col);
                    if(target != null){
                        target.takeDamage(robot.getDamage());
                    }
                }
                break;
            case SOUTH:
                for(int yToCheck = y+tileSize; yToCheck < height; yToCheck+=tileSize){
                    if(tiles[yToCheck / tileSize][x / tileSize].isBlocking()){
                        break;
                    }
                    int row = yToCheck / tileSize;
                    int col = x / tileSize;
                    AbstractRobot target = getRobotAt(row, col);
                    if(target != null){
                        target.takeDamage(robot.getDamage());
                    }
                }
                break;
            case EAST:
                for(int xToCheck = x+tileSize; xToCheck < width; xToCheck+=tileSize){
                    if(tiles[xToCheck / tileSize][x / tileSize].isBlocking()){
                        break;
                    }
                    int row = y / tileSize;
                    int col = xToCheck / tileSize;
                    AbstractRobot target = getRobotAt(row, col);
                    if(target != null){
                        target.takeDamage(robot.getDamage());
                    }
                }
                break;
            case WEST:
                for(int xToCheck = x-tileSize; xToCheck > 0; xToCheck-=tileSize){
                    if(tiles[xToCheck / tileSize][x / tileSize].isBlocking()){
                        break;
                    }
                    int row = y / tileSize;
                    int col = xToCheck / tileSize;
                    AbstractRobot target = getRobotAt(row, col);
                    if(target != null){
                        target.takeDamage(robot.getDamage());
                    }
                }
                break;

        }

    }

    /**
     * Moves the robots as they are programmed and makes them attack.
     */
    private void updateRobots() {

        for(AbstractRobot robot : robots){

            moveRobot(robot);

            robotAttack(robot);

            robot.updateDisplayedMoves();

            notifyListeners();

            wait(1000);

        }

    }

    /**
     * Makes the program wait.
     * @param time the amount of time you want the program to wait, in miliseconds.
     */
    private void wait(int time) {

        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

}
