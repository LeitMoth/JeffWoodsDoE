package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.sound.Music;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class StateTut implements IGameState {
    Camera cam;

    Sprite background;
    Shader menuShader;
    Texture backTex;

    Music m = new Music("/sound/inro.wav");

    @Override
    public void init(JeffWoods engine) {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        cam = new Camera();

        backTex = ResourceStore.getTexture("/texture/howtoplay.png");

        menuShader = ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl");

        background = new Sprite(cam.WIDTH,cam.HEIGHT, backTex, menuShader);
        m.play();

    }

    @Override
    public void update(JeffWoods engine) {

        if(engine.getWindow().keyDown(GLFW_KEY_ENTER))
        {
            //This is how state switching should be done
            engine.switchState(new StateLevel());
            return;
        }
    }

    @Override
    public void render() {
        background.draw(cam);
    }

    @Override
    public void cleanup() {
        background.cleanup();
        m.stop();
    }


}
