package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.phys.Rectangle;

public class PlayerAttack extends HitBox {

    public int damage = 1;
    public boolean active = true;
    public Sprite s;

    public PlayerAttack(Rectangle bounds) {
        super(bounds);
        s = new Sprite(bounds.halfSize.x*2, bounds.halfSize.y*2,
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl"));
        s.bounds = bounds;
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {
        if(active && affected instanceof Enemy)
        {
            ((Enemy) affected).damage(1);
        }
    }

    @Override
    public void draw(Camera c) {
        if(active)
            s.draw(c);
    }
}
