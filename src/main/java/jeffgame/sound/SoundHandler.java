package jeffgame.sound;

import jeffgame.ResourceStore;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundHandler {

    public static void playSoundEffect(String filepath) {
        playSoundEffect(filepath, -10.0f);
    }

    public static void playSoundEffect(String filepath, float volume){
        Clip clip = ResourceStore.getClip(filepath);
        clip.setFramePosition(0);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume); // Reduce volume by X decibels.
        clip.start();
    }

}
