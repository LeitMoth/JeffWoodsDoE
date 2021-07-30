package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import org.joml.Vector2f;

public class BossEntity extends SpriteEntity implements IPhysDyn, IInteractable {

    private DynPhysHandler physHandler;

    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }

    public BossEntity(Vector2f pos, Vector2f size, Texture t, Shader s) {
        super(pos, size, t, s);
        physHandler = new DynPhysHandler(bounds);
        health = 25;
    }

    public BossEntity(Vector2f pos, Vector2f size, String texture)
    {
        this(
                pos, size,
                ResourceStore.getTexture(texture),
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
            ((Player) affected).damage(2);
        }
    }
}