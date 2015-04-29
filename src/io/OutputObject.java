package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.net.URL;

/** A class to be extended by objects that want to be able to use some standard outputmethods. */
public class OutputObject {

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    protected BufferedImage loadImage(String fileName) {

        BufferedImage image;
        URL url = OutputObject.class.getResource(fileName);
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

    protected Runnable loadSound(String filename) {
        URL url = OutputObject.class.getResource(filename);
        Runnable sound =  () -> {

                try (Clip clip = AudioSystem.getClip()) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    clip.open(audioIn);
                    clip.loop(1);
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                    e.printStackTrace();
                }
        };

        return sound;
    }

    protected Runnable loadBgMusic(String filePath) {
            URL url = OutputObject.class.getResource(filePath);
            Runnable sound =  () -> {

                try (Clip clip = AudioSystem.getClip()) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    clip.open(audioIn);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    Thread.sleep(clip.getMicrosecondLength());
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            return sound;
        }

}
