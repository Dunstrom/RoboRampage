package io;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Hampus on 2015-05-05.
 */
public final class Loader {

    private Loader() {

    }

    /**
     * Loads an image
     * @param fileName The file name as a string
     * @return a BufferedImage
     */
    public static BufferedImage loadImage(String fileName) {

        BufferedImage image;
        URL url = OutputEntity.class.getResource("../Resources/" + fileName);
        try {
            image = ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
            image = null;
        }
        return image;
    }

    public static Runnable loadSound(String filename) {
        URL url = OutputEntity.class.getResource(filename);
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

    public static Runnable loadBgMusic(String filePath) {
        URL url = OutputEntity.class.getResource(filePath);
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
