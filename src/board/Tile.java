package board;

import entity.AbstractRobot;

import java.awt.*;

/**
 * A class that implements the most basic methods and fields every tile needs (floor tile)
 */
public  class Tile
{
    /** The size of one tile both height and width in pixels */
    public final static int TILE_SIZE = 50;
    protected int x;
    protected int y;
    protected Color backgroundColor = Color.GRAY;
    protected Color borderColor = Color.WHITE;

    /**
     * @return boolean true if the tile is blocking if not, false.
     */
    boolean isBlocking() {
        return false;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, Color backgrouncolor){
	this.x = x;
	this.y = y;
	this.backgroundColor = backgrouncolor;
    }

    public void update(AbstractRobot robot){

    }

    public boolean willMoveRobot(){
	return false;
    }

    /**
     * Draws the outline and background of every tile and if a entity.robot ocupies it tells the entity.robot to draw itself
     */
    public void draw(Graphics g) {

        //Draws the background of the tile
        g.setColor(backgroundColor);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        //Draws the border of the tiles
        g.setColor(borderColor);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);

    }



}

