package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.Rectangle;

public class Enemy extends SpriteEntity implements IPhysDyn, IInteractable {

    protected DynPhysHandler physHandler;
    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }

    public int attackDamage = 1;

    public Enemy(Rectangle bounds, Texture t, Shader s) {
        super(bounds, t, s);
        physHandler = new DynPhysHandler(this.bounds);
        health = 4;
    }

    public Enemy(Rectangle bounds)
    {
        this(
                bounds,
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
            ((Player) affected).damage(attackDamage);
        }
    }
}
