package io;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class for loading images
 */
public class ImageLoader {

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    public  BufferedImage loadImage(String fileName) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(fileName));
        }catch(IOException e) {
            e.printStackTrace();
        }

        return image;

    }

}
