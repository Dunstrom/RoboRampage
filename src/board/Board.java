package board;

import entity.AbstractRobot;
import entity.Orientation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Keeps tracks of all the tiles on the board.
 */
public class Board {

    private Tile[][] tiles;
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

    public void setRobots(List<AbstractRobot> robots){
        this.robots = robots;
    }

    public void removeRobot(AbstractRobot robot){
        robots.remove(robot);
    }

    public void addBoardListener(BoardListener bl) {
        listeners.add(bl);
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

        tiles = new Tile[height][width];

        for(int row = 0; row < height; row++){

            for(int col = 0; col < width; col++){

                tiles[row][col] = new Tile(col * Tile.TILE_SIZE, row * Tile.TILE_SIZE);

            }

        }

    }

    /**
     * Updates the board by updating robots and updating the tiles.
     */
    public void update(){

        for(int i = 0; i < AbstractRobot.MAX_QUEUED_MOVES; i++){

            updateRobots();

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
            toBeRemoved.forEach(this::removeRobot);
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
        listeners.forEach(BoardListener::boardChanged);
    }

    // Robot stuff

    /**
     * Checks if the entity.robot can be moved to it's temporary position. If there is another entity.robot in the way then push it.
     * @param robot a Abstract entity.robot that is going to be moved.
     * @return a boolean, true if the entity.robot is movable false if it isn't.
     */
    private boolean canMoveRobot(AbstractRobot robot){

        int oneTile = Tile.TILE_SIZE;

        if(!robots.contains(robot)){// Is entity.robot on board
            throw new IllegalArgumentException("Robot not on the board");
        }
        else if(robot.getTempX() < 0 || robot.getTempY() < 0 || robot.getTempX() >= width*oneTile || robot.getTempY() >= height*oneTile){//Checks if entity.robot is about to move out of the board.
            return false;
        }

        for (AbstractRobot otherRobot : robots) {
            if(!robot.equals(otherRobot)){
                if(otherRobot.getX() == robot.getTempX() && otherRobot.getY() == robot.getTempY()){

                    int x = otherRobot.getX();
                    int y = otherRobot.getY();

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
                    }else{
                        return false;
                    }

                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Get's the next move programmed in to the entity.robot and tries to move the entity.robot.
     * @param robot the entity.robot that is to be moved.
     */
    private void moveRobot(AbstractRobot robot) {

        robot.getNextMove().run(); // sets tempx and tempy

        if(canMoveRobot(robot)){ // check if move to tempx and tempy is possible
            robot.place(robot.getTempX(), robot.getTempY()); // sets x to tempx and y to tempy
            notifyListeners();
        }

    }

    /**
     * Finds out if theres a entity.robot at a certain tile.
     * @param row an int that tells which row to look.
     * @param col an int that tells which col to look.
     * @return a AbstractRobot if there is a entity.robot else returns null
     */
    private AbstractRobot getRobotAt(int row, int col) {
        for(AbstractRobot robot : robots){
            int robotRow = robot.getY() / Tile.TILE_SIZE;
            int robotCol = robot.getX() / Tile.TILE_SIZE;
            if(robotCol == col && robotRow == row) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Makes the entity.robot attack
     * @param robot the entity.robot that shall attack.
     */
    private void robotAttack(AbstractRobot robot) {

        Orientation orientation = robot.getOrientation();
        int y = robot.getY();
        int x = robot.getX();
        int tileSize = Tile.TILE_SIZE;

        switch(orientation){

            case NORTH:
                attackNorth(y,x, tileSize, robot);
                break;
            case SOUTH:
                attackSouth(y, x, tileSize, robot);
                break;
            case EAST:
                attackEast(y, x, tileSize, robot);
                break;
            case WEST:
                attackWest(y, x, tileSize, robot);
                break;

        }

    }

    private void attackNorth(int y, int x, int tileSize, AbstractRobot robot) {
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
    }

    private void attackSouth(int y, int x, int tileSize, AbstractRobot robot) {
        for(int yToCheck = y+tileSize; yToCheck < (height-1)*tileSize; yToCheck+=tileSize){
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
    }

    private void attackWest(int y, int x, int tileSize, AbstractRobot robot) {
        for(int xToCheck = x-tileSize; xToCheck > 0; xToCheck-=tileSize){
            if(tiles[y / tileSize][xToCheck / tileSize].isBlocking()){
                break;
            }
            int row = y / tileSize;
            int col = xToCheck / tileSize;
            AbstractRobot target = getRobotAt(row, col);
            if(target != null){
                target.takeDamage(robot.getDamage());
            }
        }
    }

    private void attackEast(int y, int x, int tileSize, AbstractRobot robot) {
        for(int xToCheck = x+tileSize; xToCheck < (width-1)*tileSize; xToCheck+=tileSize){
            if(tiles[y / tileSize][xToCheck / tileSize].isBlocking()){
                break;
            }
            int row = y / tileSize;
            int col = xToCheck / tileSize;
            AbstractRobot target = getRobotAt(row, col);
            if(target != null){
                target.takeDamage(robot.getDamage());
            }
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
