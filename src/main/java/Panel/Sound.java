package Panel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public void sound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File ("src/main/resources/data/music.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
}
