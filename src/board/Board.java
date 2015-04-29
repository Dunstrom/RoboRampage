package board;

import entity.AbstractRobot;
import entity.BoardObject;
import entity.Flag;
import entity.FlagFactory;
import entity.Orientation;
import io.Settings;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Keeps tracks of everything that is kept on the board where the game takes place. Handels the actual moving of the robots, collision handeling and the shoot functionality aswell.
 */
public class Board {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<BoardListener> listeners;
    private List<AbstractRobot> robots;
    private List<BoardObject> boardObjects;
    private int tileSize;

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setRobots(List<AbstractRobot> robots){
        this.robots = robots;
        for(AbstractRobot robot: robots) {//Prefer it this way because of increased readability
            if(!boardObjects.contains(robot)){
                boardObjects.add(robot);
            }
        }
    }

    public void removeRobot(AbstractRobot robot){
        robots.remove(robot);
        boardObjects.remove(robot);
    }

    public void addBoardListener(BoardListener bl) {
        listeners.add(bl);
    }

    public Board(Settings settings) throws SettingsFailiureException{

        tiles = settings.getTiles();
        tileSize = settings.getTileSize();
        width = tiles[0].length;
        height = tiles.length;
        listeners = new ArrayList<>();
        robots = new ArrayList<>();
        boardObjects = new ArrayList<>();
        placeFlags(settings);
        notifyListeners();

    }

    // Board stuff

    private void placeFlags(Settings settings) {

        FlagFactory flagFactory = FlagFactory.getInstance();

        boardObjects.addAll(flagFactory.createFlags(settings));

    }

    /**
     * Updates the board by updating robots and updating the tiles.
     */
    public void update(){

        for(int i = 0; i < AbstractRobot.MAX_QUEUED_MOVES; i++){

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

        for(AbstractRobot robot : robots){//Prefer it this way because of increased readability
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

        for(BoardObject obj : boardObjects) {
            obj.draw(g);
        }

    }

    /**
     * Notifies all the boardListeners by calling their boardChanged method.
     */
    private void notifyListeners() {
        listeners.forEach(BoardListener::boardChanged);
    }

    // Robot stuff

    private boolean stillOnBoard(AbstractRobot robot, int oneTile) {
        assert robots.contains(robot); // Makes sure the robot is on the board
        if(robot.getTempX() < 0 || robot.getTempY() < 0 || robot.getTempX() >= width*oneTile || robot.getTempY() >= height*oneTile){//Checks if entity.robot is about to move out of the board.
            return false;
        } else {
            return true;
        }
    }

    private boolean canPush(AbstractRobot robot, int oneTile) {
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
     * Checks if the entity.robot can be moved to it's temporary position. If there is another entity.robot in the way then push it.
     * @param robot a Abstract entity.robot that is going to be moved.
     * @return a boolean, true if the entity.robot is movable false if it isn't.
     */
    private boolean canMoveRobot(AbstractRobot robot){

        if(!stillOnBoard(robot, tileSize) || !canPush(robot, tileSize) || tiles[robot.getTempY()/tileSize][robot.getTempX()/tileSize].isBlocking()){
            return false;
        }

        return true;
    }

    private void pickFlag(AbstractRobot robot) {
        for(BoardObject obj : boardObjects) {//Prefer it this way because of increased readability
            if(robot.collide(obj) && obj.getClass().equals(Flag.class)){
                robot.pickFlag((Flag)obj); // Can cast obj to Flag because I have chacked that it is a flag.
            }
        }
    }

    /**
     * Get's the next move programmed in to the entity.robot and tries to move the entity.robot.
     * @param robot the entity.robot that is to be moved.
     */
    private void moveRobot(AbstractRobot robot) {

        robot.getNextMove().execute(); // sets tempx and tempy

        if(canMoveRobot(robot)){ // check if move to tempx and tempy is possible
            robot.place(robot.getTempX(), robot.getTempY()); // sets x to tempx and y to tempy
            pickFlag(robot);
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
            int robotRow = robot.getY() / tileSize;
            int robotCol = robot.getX() / tileSize;
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
        for(int yToCheck = y-tileSize; yToCheck >= 0; yToCheck-=tileSize){
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
        for(int yToCheck = y+tileSize; yToCheck <= (height-1)*tileSize; yToCheck+=tileSize){
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
        for(int xToCheck = x-tileSize; xToCheck >= 0; xToCheck-=tileSize){
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
        for(int xToCheck = x+tileSize; xToCheck <= (width-1)*tileSize; xToCheck+=tileSize){
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

            robot.renderPlayerInterface();

            notifyListeners();

            wait(1000);

        }

    }

    private void updateTiles(){

	for (AbstractRobot robot : robots) {
	    Tile tile = tiles[robot.getY() / tileSize][robot.getX() / tileSize];
	    tile.update(robot);

	    if(tile.willMoveRobot() && canMoveRobot(robot)){ // check if move to tempx and tempy is possible
         	robot.place(robot.getTempX(), robot.getTempY()); // sets x to tempx and y to tempy
        	pickFlag(robot);
         	notifyListeners();
     }
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
