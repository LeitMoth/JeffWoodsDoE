package jeffgame.gfx;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class PlayMusic {
    public void PlayMusic(String filepath){
        InputStream music;
        try{

            music = getClass().getResourceAsStream(filepath);
            AudioInputStream aIS = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(aIS);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);


        } catch (IOException e){
            e.printStackTrace();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


    }


}
