package jeffgame;

import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;

import java.util.HashMap;

public class ResourceStore {


    private static HashMap<String, Texture> texStore;
    private static HashMap<String, Shader> shaderStore;

    public static void init()
    {
        texStore = new HashMap<>();
        shaderStore = new HashMap<>();
    }

    public static void cleanup()
    {
        for(Texture t : texStore.values())
            t.cleanup();
        texStore.clear();

        for(Shader s : shaderStore.values())
            s.cleanup();
        shaderStore.clear();
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
}
