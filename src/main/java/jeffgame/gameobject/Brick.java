package jeffgame.gameobject;

import jeffgame.ResourceStore;
import jeffgame.phys.IPhysStatic;
import jeffgame.phys.Rectangle;

public class Brick extends Sprite implements IPhysStatic {

    public Brick(float width, float height, float x, float y) {
        super(width, height,
                ResourceStore.getTexture("/vem.png"),
                ResourceStore.getShader("/tex.vs.glsl","/tex.fs.glsl"));
        bounds.center.x = x;
        bounds.center.y = y;
    }

    @Override
    public Rectangle getRectangle() {
        return bounds;
    }
}
