package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite{

    private final float speed = 0.75f;

    public Player() {
        super(
                50, 50,
                ResourceStore.getTexture("/deej_weeg.png"),
                ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
        );
    }

    @Override
    public void update(JeffWoods engine) {
        if(engine.getWindow().keyDown(GLFW_KEY_RIGHT))
        {
            position.x += speed;
        }
        if(engine.getWindow().keyDown(GLFW_KEY_LEFT))
        {
            position.x -= speed;
        }
        if(engine.getWindow().keyDown(GLFW_KEY_UP))
        {
            position.y += speed;
        }
        if(engine.getWindow().keyDown(GLFW_KEY_DOWN))
        {
            position.y -= speed;
        }
    }
}
