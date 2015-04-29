package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/** An abstract class for every Component used in the game. Has the implementation of loading images, some standard key actions and overall standard info on displaying things in the game. */
public abstract class GameComponent extends JComponent {

    protected final static int BUTTON_FONT_SIZE = 14;
    protected final static Font BUTTON_FONT = new Font("Arial",Font.BOLD, BUTTON_FONT_SIZE);
    protected final static int HEADLINE_FONT_SIZE = 25;
    protected final static Font HEADLINE_FONT = new Font("Arial",Font.BOLD, HEADLINE_FONT_SIZE);

    protected GameComponent() {

        //Setup standard keybindings
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_DOWN_MASK), "actionMapKey");
        Action exit = new AbstractAction() {
            @Override public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };
        getActionMap().put("actionMapKey", exit);
        runBgMusic("../Resources/Hitman.wav");
    }

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

    protected Runnable loadBgMusic(String filePath) {
        URL url = AbstractOutputObject.class.getResource(filePath);
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

    private void runBgMusic(String filePath){
        Runnable bgMusic = loadBgMusic(filePath);
        Thread bgMusicPlayer = new Thread(bgMusic);
        bgMusicPlayer.start();
    }

}
