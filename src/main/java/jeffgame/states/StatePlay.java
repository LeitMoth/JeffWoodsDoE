package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.IGameObject;
import jeffgame.gameobject.Player;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class StatePlay implements IGameState{

    public ArrayList<IGameObject> gameObjects;
    public Camera camera = new Camera();

    @Override
    public void init() {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        gameObjects = new ArrayList<>();

        gameObjects.add(new Sprite(
                20,40,
                ResourceStore.getTexture("/vem.png"),
                ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
        ));

        Sprite boss =
                new Sprite(
                        30,30,
                        ResourceStore.getTexture("/FinalBoss.png"),
                        ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
                );

        boss.getPosition().add(-40,60);
        gameObjects.add(boss);

        gameObjects.add(new Player());
    }

    @Override
    public void update(JeffWoods engine) {
        for(IGameObject gameObject : gameObjects)
        {
            gameObject.update(engine);
        }
        camera.update(engine);
    }

    @Override
    public void render() {
        for(IGameObject g : gameObjects)
        {
            g.draw(camera);
        }
    }

    public void cleanup()
    {
        for(IGameObject object : gameObjects)
        {
            object.cleanup();
        }
    }
}
