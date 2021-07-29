package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import org.joml.Vector2f;

public class Enemy extends SpriteEntity implements IPhysDyn, IInteractable {

    private DynPhysHandler physHandler;
    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }

    public Enemy(Vector2f pos, Vector2f size, Texture t, Shader s) {
        super(pos, size, t, s);
        physHandler = new DynPhysHandler(bounds);
    }

    public Enemy(Vector2f pos, Vector2f size)
    {
        this(
                pos, size,
                ResourceStore.getTexture("/texture/FinalBoss.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
        );
    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);
        physHandler.beginMove();
        physHandler.endMove();
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {
        if(affected instanceof Player)
        {
            ((Player) affected).damage(1);
        }
    }
}
