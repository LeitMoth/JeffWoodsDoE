package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.sound.Music;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.*;

public class StateCredits implements IGameState {
    Camera cam;

    Sprite background;
    Shader menuShader;
    Texture backTex;
    Music song = new Music("/sound/japes.wav");

    @Override
    public void init(JeffWoods engine) {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            cam = new Camera();

        backTex = ResourceStore.getTexture("/texture/credit1.png");

        menuShader = ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl");

        background = new Sprite(cam.WIDTH,cam.HEIGHT, backTex, menuShader);

//        musicHandler.PlayMusic("/sound/level_theme.wav");
        song.play();
    }

    @Override
    public void update(JeffWoods engine) {

        if(engine.getWindow().keyDown(GLFW_KEY_R))
        {
            //This is how state switching should be done
            engine.switchState(new StateMenu());
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
        song.stop();
    }


}
