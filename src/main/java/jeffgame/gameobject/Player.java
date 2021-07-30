package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.Rectangle;
import jeffgame.sound.SoundHandler;
import jeffgame.states.StateBossFight;
import jeffgame.states.StateLevel;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends SpriteEntity implements IPhysDyn, IInteractable {

    private DynPhysHandler physHandler;
    private int facing = 1;
    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }

    private PlayerAttack hit;

    public Player(JeffWoods engine) {
        super(
                new Vector2f(0,0),new Vector2f(50,20),
                ResourceStore.getTexture("/texture/Player.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
        );
        physHandler = new DynPhysHandler(bounds);
        health = 10;
        hitCooldownStart = 30;

        hit = new PlayerAttack(new Rectangle(new Vector2f(0,0),new Vector2f(20,5)));
        if(engine.state instanceof StateLevel)
        {
            ((StateLevel) engine.state).gameObjects.add(hit);
        }
        if(engine.state instanceof StateBossFight)
        {
            ((StateBossFight) engine.state).gameObjects.add(hit);
        }
        hit.active = false;

        //Go ahead and make sure it's in the store, so first jump doesn't slow down the game to look for it
        ResourceStore.getClip("/sound/jump.wav");

    }

    @Override
    public void update(JeffWoods engine) {
        //Track health
        super.update(engine);

        //Handle physics
        physHandler.beginMove();

        float speed = 5f;
        if(engine.getWindow().keyDown(GLFW_KEY_RIGHT))
        {
            bounds.center.x += speed;
            facing = 1;
        }
        if(engine.getWindow().keyDown(GLFW_KEY_LEFT))
        {
            bounds.center.x -= speed;
            facing = -1;
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
//            soundEffectMan.PlaySoundEffect("/sound/jump.wav");
            SoundHandler.playSoundEffect("/sound/jump.wav");
            physHandler.getVelocity().y = 10.f;
        }

        physHandler.endMove();

        //Handle attacks
        if(engine.getWindow().keyDown(GLFW_KEY_X))
        {
            /*
            TODO: fix
            because this code runs before the physics are resolved, the hitbox for the attack will be aligned with the
            players position *before* they are moved out of whatever solid they are in

            This results in a constant negligible offset,
            and the weapon clipping into the ground for a single frame when the player lands on the ground
             */
            Rectangle hitb = hit.getHandler().getBounds();
            hitb.center = new Vector2f(bounds.center);
            hitb.center.x += (bounds.halfSize.x + hitb.halfSize.x) * facing;
            hit.active = true;
        }
        else
        {
            hit.active = false;
        }
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {
        if(affected instanceof Collectable && engine.state instanceof StateLevel)
        {
            System.out.println("Picked up collectable: " + ((Collectable) affected).name);
            ((Collectable) affected).cleanup();
            ((StateLevel)engine.state).gameObjects.remove(affected);
        }

        if(affected instanceof Collectable && engine.state instanceof StateBossFight)
        {
            System.out.println("Picked up collectable: " + ((Collectable) affected).name);
            ((Collectable) affected).cleanup();
            ((StateBossFight)engine.state).gameObjects.remove(affected);
        }
    }
}
