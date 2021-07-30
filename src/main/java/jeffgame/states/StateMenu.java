package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.phys.Rectangle;
import jeffgame.sound.Music;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class StateMenu implements IGameState {

    Camera cam;

    Sprite background, title, title2;
    Shader menuShader;
    Texture backTex, textTex, text2Tex;
    Music song = new Music("/sound/menu_theme.wav");

    @Override
    public void init(JeffWoods engine) {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        cam = new Camera();

        backTex = ResourceStore.getTexture("/texture/menu_back.png");
        textTex = ResourceStore.getTexture("/texture/menu_text.png");
        text2Tex = ResourceStore.getTexture("/texture/starttip.png");

        menuShader = ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl");

        background = new Sprite(new Rectangle(0,0,cam.WIDTH/2,cam.HEIGHT/2), backTex, menuShader);
        title = new Sprite(new Rectangle(0,0,cam.WIDTH*.95f/2,cam.HEIGHT*.2f/2), textTex, menuShader);
        title.getPosition().y += cam.HEIGHT/4;

        title2 = new Sprite(new Rectangle(0,0,cam.WIDTH*.9f/2, cam.HEIGHT*.2f/2), text2Tex, menuShader);
        title2.getPosition().y -= cam.HEIGHT/5;

        song.play();
    }

    @Override
    public void update(JeffWoods engine) {

        if(engine.getWindow().keyDown(GLFW_KEY_SPACE))
        {
            //This is how state switching should be done
            engine.switchState(new StateTut());
            return;
        }

        if(engine.getWindow().keyDown(GLFW_KEY_C))
        {
            //This is how state switching should be done
            engine.switchState(new StateCredits());
            return;
        }
    }

    @Override
    public void render() {
        background.draw(cam);
        title.draw(cam);
        title2.draw(cam);
    }

    @Override
    public void cleanup() {
        background.cleanup();
        title.cleanup();
        title2.cleanup();
        song.stop();
    }


}
