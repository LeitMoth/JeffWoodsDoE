package jeffgame;

import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ResourceStore {


    private static HashMap<String, Texture> texStore;
    private static HashMap<String, Shader> shaderStore;
    private static HashMap<String, Clip> clipStore;

    public static void init()
    {
        texStore = new HashMap<>();
        shaderStore = new HashMap<>();
        clipStore = new HashMap<>();
    }

    public static void cleanup()
    {
        for(Texture t : texStore.values())
            t.cleanup();
        texStore.clear();

        for(Shader s : shaderStore.values())
            s.cleanup();
        shaderStore.clear();

        clipStore.clear();
    }

    public static Texture getTexture(String name)
    {
        Texture t = texStore.get(name);
        if(t != null) return t;

        System.out.println("Creating new texture: " +name);
        t = new Texture(name);
        texStore.put(name, t);
        return t;
    }

    public static Shader getShader(String vert, String frag)
    {
        String name = vert + ":" + frag;
        Shader s = shaderStore.get(name);
        if(s != null) return s;

        System.out.println("Creating new shader: " +name);
        s = new Shader(vert, frag);
        shaderStore.put(name, s);
        return s;
    }

    public static Clip getClip(String name)
    {
        Clip c = clipStore.get(name);
        if(c != null) return c;

        System.out.println("Creating new clip: " +name);

        try {
            c = AudioSystem.getClip();
            c.open(
                    AudioSystem.getAudioInputStream(ResourceStore.class.getResource(name))
            );
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        clipStore.put(name, c);
        return c;
    }
}
