package jeffgame.sound;

import jeffgame.ResourceStore;

import javax.sound.sampled.*;

public class Music {

    private Clip clip;

    public Music(String filepath)
    {
        clip = ResourceStore.getClip(filepath);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play(){
        play(-10.0f);
    }

    public void play(float volume)
    {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume); // Reduce volume by X decibels.
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop(){
        clip.stop();
    }
}
