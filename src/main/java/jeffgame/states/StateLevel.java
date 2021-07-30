package jeffgame.states;

import jeffgame.gameobject.*;
import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.sound.Music;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.opengl.GL11.*;

public class StateLevel extends StatePlay {

    Music song = new Music("/sound/level_theme.wav");

    @Override
    public void init(JeffWoods engine) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        camera.setDefaultZoom(0.3f);
        song.play();

        gameObjects.add(new Background(ResourceStore.getTexture("/texture/city_background_night.png")));

        player = new Player(engine);
        gameObjects.add(player);

        gameObjects.add(new Brick(30,30,-80,60));
        gameObjects.add(new Brick(30,30,-150,-30));

        gameObjects.add(new Brick(40,70,30,0));

        gameObjects.add(new Brick(40,70,71,0));
        gameObjects.add(new Brick(40,70,30,-71));
        gameObjects.add(new Brick(40,70,71,-71));

        gameObjects.add(new Brick(600,2,0,-100));

        gameObjects.add(new Brick(30,30,120,100));
        gameObjects.add(new Brick(30,30,160,160));
        gameObjects.add(new Brick(30,30,60,200));

        gameObjects.add(new Enemy(new Vector2f(-80,110), new Vector2f(30,30)));
        gameObjects.add(new Enemy(new Vector2f(-50,40), new Vector2f(20,20)));

        gameObjects.add(new Collectable(new Vector2f(160, 100), new Vector2f(20,50),
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl")));
    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);

        //victory check
        int toDealWith = 0;
        for(IGameObject gameObject : gameObjects)
        {
            if(gameObject instanceof Enemy || gameObject instanceof Collectable)
            {
                toDealWith++;
            }
        }
        if(toDealWith == 0)
        {
            engine.switchState(new StateBossFight());
            return;
        }

        //Adding Player Death function here
        if(player.getHealth() <= 0){
            engine.switchState(new StateGameOver());
        }

        if(engine.getWindow().keyDown(GLFW_KEY_R))
        {
            engine.switchState(new StateLevel());
        }

        if(engine.getWindow().keyDown(GLFW_KEY_S)){

            engine.switchState(new StateBossFight());
        }


    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        song.stop();
    }

}