package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.sound.PlayMusic;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.opengl.GL33.*;

public class StateMenu implements IGameState {

    Camera cam;

    Sprite background, title;
    Shader menuShader;
    Texture backTex, textTex;
    PlayMusic musicHandler = new PlayMusic();

    @Override
    public void init() {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        cam = new Camera();

        backTex = ResourceStore.getTexture("/texture/menu_back.png");
        textTex = ResourceStore.getTexture("/texture/menu_text.png");

        menuShader = ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl");

        background = new Sprite(cam.WIDTH,cam.HEIGHT, backTex, menuShader);
        title = new Sprite(cam.WIDTH*.95f,cam.HEIGHT*.2f, textTex, menuShader);
        title.getPosition().y += cam.HEIGHT/4;

        musicHandler.PlayMusic("/sound/menu_theme.wav");
    }

    @Override
    public void update(JeffWoods engine) {
        if(engine.getWindow().keyDown(GLFW_KEY_ENTER))
        {
            //This is how state switching should be done
            engine.switchState(new StatePlay());
            return;
        }
    }

    @Override
    public void render() {
        background.draw(cam);
        title.draw(cam);
    }

    @Override
    public void cleanup() {
        background.cleanup();
        title.cleanup();
    }


}
