package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Hampus on 2015-04-23.
 */
public abstract class GameComponent extends JComponent {

    protected final static int BUTTON_FONT_SIZE = 14;
    protected final static Font BUTTON_FONT = new Font("Arial",Font.BOLD, BUTTON_FONT_SIZE);
    protected final static int HEADLINE_FONT_SIZE = 25;
    protected final static Font HEADLINE_FONT = new Font("Arial",Font.BOLD, HEADLINE_FONT_SIZE);

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    protected BufferedImage loadImage(String fileName) {

        BufferedImage image;
        URL url = AbstractOutputObject.class.getResource("../Resources/" + fileName);
        try {
            image = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
            image = null;
        }
        return image;
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

    protected Clip loadSoundClip(String filename) {
        Clip clip = null;
        URL url = AbstractOutputObject.class.getResource(filename);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    protected void handleReadingError(String message) {

    }

}
