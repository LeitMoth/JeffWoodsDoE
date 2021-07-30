package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.phys.IPhysStatic;
import jeffgame.phys.Rectangle;

public class Brick implements IGameObject, IPhysStatic {

    protected Sprite sprite;

    public Brick(Rectangle bounds) {
        sprite = new Sprite(
                bounds,
                bounds.halfSize.x*2/50, bounds.halfSize.y*2/50,
                ResourceStore.getTexture("/texture/TempBrick.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl"));
    }

    @Override
    public Rectangle getRectangle() {
        return this.sprite.bounds;
    }

    @Override
    public void draw(Camera c) {
        sprite.draw(c);
    }

    @Override
    public void update(JeffWoods engine) { }

    @Override
    public void cleanup() {
        sprite.cleanup();
    }
}
