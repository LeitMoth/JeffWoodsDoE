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
        s = new Sprite(
                bounds,
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl"));
    }

    @Override
    public void interact(IInteractable affected, JeffWoods engine) {
        if(active && affected instanceof Enemy)
        {
            ((Enemy) affected).damage(damage);
        }
    }

    @Override
    public void draw(Camera c) {
        if(active)
            s.draw(c);
    }
}
