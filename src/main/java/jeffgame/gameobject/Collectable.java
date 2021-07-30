package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import org.joml.Vector2f;

public class Collectable extends Sprite implements IPhysDyn, IInteractable {

    private DynPhysHandler physHandler;
    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }

    public String name = "Peggy1";

    public Collectable(Vector2f pos, Vector2f size, Texture t, Shader s) {
        super(size.x, size.y, t, s);
        physHandler = new DynPhysHandler(bounds,false);
        bounds.center = pos;
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {

    }

}
