package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.phys.IPhysStatic;
import jeffgame.phys.Rectangle;
import org.joml.Vector2f;

public class Brick implements IGameObject, IPhysStatic {

    protected Sprite sprite;
    protected Rectangle bounds = new Rectangle();

    public Brick(float width, float height, float x, float y) {
        sprite = new Sprite(width, height,
                ResourceStore.getTexture("/texture/vem.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl"));

        bounds.center = new Vector2f(x,y);
        bounds.halfSize = new Vector2f(width,height).div(2);

        //link, not copy
        sprite.bounds = bounds;
    }

    @Override
    public Rectangle getRectangle() {
        return this.bounds;
    }

    @Override
    public void draw(Camera c) {
        sprite.draw(c);
    }

    @Override
    public void update(JeffWoods engine) {

    }

    @Override
    public void cleanup() {
        sprite.cleanup();
    }
}
