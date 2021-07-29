package jeffgame.sound;

import jeffgame.ResourceStore;

import javax.sound.sampled.*;

public class Music {

    private Clip clip;

    public Music(String filepath)
    {
        clip = ResourceStore.getClip(filepath);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f); // Reduce volume by X decibels.
    }

    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
}
