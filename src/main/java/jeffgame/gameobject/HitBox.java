package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.Rectangle;

public class HitBox implements IGameObject, IPhysDyn, IInteractable {

    public DynPhysHandler physHandler;

    public HitBox(Rectangle bounds)
    {
        physHandler = new DynPhysHandler(bounds, false);
    }

    @Override
    public void draw(Camera c) {}

    @Override
    public void update(JeffWoods engine) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {

    }

    @Override
    public DynPhysHandler getHandler() {
        return physHandler;
    }
}
