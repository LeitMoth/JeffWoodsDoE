package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.Rectangle;
import jeffgame.sound.Music;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class StateGameOver implements IGameState {

    Camera cam;

    Sprite background, title, restart;
    Shader menuShader;
    Texture backTex, textTex, restartTex;
    Music song = new Music("/sound/Game_Over.wav");

    @Override
    public void init(JeffWoods engine) {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        cam = new Camera();

        backTex = ResourceStore.getTexture("/texture/gameOver.png");
        textTex = ResourceStore.getTexture("/texture/gameOverText.png");
        restartTex = ResourceStore.getTexture("/texture/restartText1.png");

        menuShader = ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl");

        background = new Sprite(new Rectangle(0,0,cam.WIDTH/2,cam.HEIGHT/2), backTex, menuShader);
        title = new Sprite(new Rectangle(0,0,cam.WIDTH*.95f/2,cam.HEIGHT*.2f/2), textTex, menuShader);
        title.getPosition().y -= cam.HEIGHT/24;
        restart = new Sprite(new Rectangle(0,0,cam.WIDTH*.50f/2,cam.HEIGHT*.2f/2), restartTex, menuShader);
        restart.getPosition().y += cam.HEIGHT/3;

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
        title.draw(cam);
        restart.draw(cam);
    }

    @Override
    public void cleanup() {
        background.cleanup();
        title.cleanup();
        restart.cleanup();
        song.stop();
    }


}
