package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.phys.Rectangle;
import jeffgame.sound.Music;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class StateTut implements IGameState {
    Camera cam;

    Sprite background;

    Music m = new Music("/sound/inro.wav");

    @Override
    public void init(JeffWoods engine) {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        cam = new Camera();

        background = new Sprite(
                new Rectangle(0,0,cam.WIDTH/2,cam.HEIGHT/2),
                ResourceStore.getTexture("/texture/howtoplay.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
        );

        m.play(-4.7f);
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
