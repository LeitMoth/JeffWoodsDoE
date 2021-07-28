package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite implements IPhysDyn {

    private final float speed = 5f;

    DynPhysHandler physHandler = new DynPhysHandler(bounds);

    public Player() {
        super(
                50, 20,
                ResourceStore.getTexture("/deej_weeg.png"),
                ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
        );
    }

    @Override
    public void update(JeffWoods engine) {
        physHandler.beginMove();

        if(engine.getWindow().keyDown(GLFW_KEY_RIGHT))
        {
            bounds.center.x += speed;
        }
        if(engine.getWindow().keyDown(GLFW_KEY_LEFT))
        {
            bounds.center.x -= speed;
        }

        physHandler.enableGravity = true;
        if(engine.getWindow().keyDown(GLFW_KEY_LEFT_SHIFT)) {
            physHandler.enableGravity = false;
            if(engine.getWindow().keyDown(GLFW_KEY_UP))
            {
                bounds.center.y += speed;
            }
            if(engine.getWindow().keyDown(GLFW_KEY_DOWN))
            {
                bounds.center.y -= speed;
            }
        }

        if(engine.getWindow().keyDown(GLFW_KEY_SPACE) && !physHandler.inAir)
        {
            physHandler.getVelocity().y = 10.f;
        }


        physHandler.endMove();
    }

    public DynPhysHandler getHandler()
    {
        return physHandler;
    }
}
