package board;

import entity.AbstractRobot;
import io.Loader;
import io.OutputEntity;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A class that implements the most basic methods and fields every tile needs (floor tile)
 */
public  class Tile extends OutputEntity
{
    protected int tileSize;
    protected int x;
    protected int y;
    protected Color borderColor = Color.BLACK;
    protected BufferedImage sprite;

    /**
     * @return boolean true if the tile is blocking if not, false.
     */
    boolean isBlocking() {
        return false;
    }

    public Tile(int x, int y, int tileSize) {
        this.tileSize = tileSize;
        this.x = x;
        this.y = y;
        sprite = Loader.loadImage("../Resources/tile.png");
    }

    public Tile(int x, int y, int tileSize, String spritePath){
	this.tileSize = tileSize;
        this.x = x;
	this.y = y;
        this.sprite = Loader.loadImage(spritePath);
    }

    public void update(AbstractRobot robot){

    }

    public boolean willMoveRobot(){
	return false;
    }

    /** Draws the outline and background of every tile and if a entity.robot ocupies it tells the entity.robot to draw itself */
   @Override
    public void draw(Graphics g) {

        //Draws the background of the tile
        g.drawImage(sprite, x, y, null);
        g.setColor(borderColor);
        g.drawRect(x, y, tileSize, tileSize);

    }



}

