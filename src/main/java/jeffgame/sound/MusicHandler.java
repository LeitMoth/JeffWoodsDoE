package jeffgame.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class MusicHandler {
    InputStream music;
    Clip clip;

    public void PlayMusic(String filepath){

        try {

            music = getClass().getResourceAsStream(filepath);
            AudioInputStream aIS = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
            clip.open(aIS);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by X decibels.
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);


        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public void PlaySoundEffect(String filepath){

        try {

            music = getClass().getResourceAsStream(filepath);
            AudioInputStream aIS = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
            clip.open(aIS);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by X decibels.
            clip.start();


        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public void StopMusic(){



            clip.stop();


    }
}
