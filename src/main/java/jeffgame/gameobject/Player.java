package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import jeffgame.sound.MusicHandler;
import jeffgame.states.StatePlay;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends SpriteEntity implements IPhysDyn, IInteractable {

    private DynPhysHandler physHandler;
    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }
    MusicHandler soundEffectMan = new MusicHandler();

    public Player() {
        super(
                new Vector2f(0,0),new Vector2f(50,20),
                ResourceStore.getTexture("/texture/Player.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
        );
        physHandler = new DynPhysHandler(bounds);
        health = 10;
        hitCooldownStart = 30;
    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);
        physHandler.beginMove();

        float speed = 5f;
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
            soundEffectMan.PlaySoundEffect("/sound/jump.wav");
            physHandler.getVelocity().y = 10.f;
        }

        physHandler.endMove();
    }

    @Override
    public void draw(Camera c) {
        if(hitCooldown < 0 || hitCooldown % 2 == 0) {
            super.draw(c);
        }
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {
        if(affected instanceof Collectable && engine.state instanceof StatePlay)
        {
            System.out.println("Picked up collectable: " + ((Collectable) affected).name);
            ((Collectable) affected).cleanup();
            ((StatePlay)engine.state).gameObjects.remove(affected);
        }
    }
}
