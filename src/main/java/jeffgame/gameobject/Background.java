package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import org.joml.Vector2f;

public class Background implements IGameObject{

    private Sprite sprite;

    public Background(Texture t)
    {
        t.makeBackground();
        sprite = new Sprite(
                2,2,
                t,
                ResourceStore.getShader("/shader/scroll_fill.vs.glsl", "/shader/tex.fs.glsl")
        );
    }

    @Override
    public void draw(Camera c) {
        Shader s = sprite.getShader();
        Texture t = sprite.getTexture();
        s.bind();
        t.bind();

        s.setUniform("texture_sampler",0);
        s.setUniform("texture_multiplier", new Vector2f((t.height/(float)t.width)*(16.f/9), 1.f));
        s.setUniform("texture_scroll", new Vector2f(c.getPos().x/10_000,-c.getPos().y/10_000 ));

        //We handled all the textures/shaders, so just draw the vertices
        sprite.drawMesh();
    }

    @Override
    public void update(JeffWoods engine) {

    }

    @Override
    public void cleanup() {
        sprite.cleanup();
    }
}
