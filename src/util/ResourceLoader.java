package util;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static BufferedImage loadImage(String absolutePath) {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                System.err.println("Resource not found: " + absolutePath);
                return null;
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Clip loadAudio(String absolutePath) {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                System.err.println("Resource not found: " + absolutePath);
                return null;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
