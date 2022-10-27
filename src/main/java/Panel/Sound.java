package Panel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private boolean isOpenMusic = true;
    private boolean isOpenEffect = true;

    public void setOpenMusic(boolean music) {
        isOpenMusic = music;
    }

    public boolean isMusic() {
        return isOpenMusic;
    }

    public void setOpenEffect(boolean effect) {
        isOpenEffect = effect;
    }

    public boolean isEffect() {
        return isOpenEffect;
    }
    public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File file = new File("src/main/resources/data/music.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();

        if (isOpenMusic) {
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();
        }

    }
    public void bomb() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/bomb.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
    public void die() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/die.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
    public void item() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/item.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
    public void endgame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/endgame.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
    public void setbomb() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/setbomb.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
    public void brickbroken() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/data/brickbroken.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        if (isOpenEffect) {
            clip.open(audioStream);
        }
        clip.start();
    }
}
