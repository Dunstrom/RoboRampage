package io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/** An abstract class for every Component used in the game. Has the implementation of loading images, some standard key actions and overall standard info on displaying things in the game. */
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
        URL url = OutputObject.class.getResource("../Resources/" + fileName);
        try {
            image = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
            image = null;
        }
        return image;
    }

}
