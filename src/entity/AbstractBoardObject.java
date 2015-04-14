package entity;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.net.URL;

/**
 * A abstract class for every object pressent on the board.
 */
public abstract class AbstractBoardObject implements BoardObject{


    // Position
    protected int x;
    protected int y;
    protected Orientation orientation;

    protected AbstractBoardObject(final int x, final Orientation orientation, final int y) {
	this.x = x;
	this.orientation = orientation;
	this.y = y;
    }

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    protected BufferedImage loadImage(String fileName) {

        BufferedImage image;
        URL url = AbstractRobot.class.getResource(fileName);
        try {
            image = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
            image = null;
        }
        return image;
    }

    /**
     * changes the robots position
     * @param newX amount of tiles to place on the x-axis. pos. x -> right, neg. x -> left.
     * @param newY amount of tiles to place on the y-axis. pos. y -> down, neg. y -> up.
     */
    public void place(final int newX, final int newY) {

        x = newX;
        y = newY;

    }

    /**
     * Takes an image and draws it rotated to the screen.
     * @param image The image we want to draw.
     * @param degreesToRotate The amount of degrees to rotate the image.
     * @param x the x position where we draw the image.
     * @param y the y position where we draw the image.
     * @param g2d a Graphics2D object that draws the image to the screen.
     */
    protected void drawImageRotated(BufferedImage image, double degreesToRotate, int x, int y, Graphics2D g2d){

        double rotationRequired = Math.toRadians(degreesToRotate);
        int locationX = image.getWidth() / 2;
        int locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter(image, null), x, y, null);

    }

    @Override
    public boolean collide(BoardObject obj) {
        if(x == obj.getX() && y == obj.getY()) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
