package jeffgame.gameobject;

import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import org.joml.Vector2f;

public class SpriteEntity extends Entity {

    protected Sprite sprite;

    public SpriteEntity(Vector2f pos, Vector2f size, Texture t, Shader s) {
        bounds.center = pos;
        size.div(2, bounds.halfSize);
        sprite = new Sprite(size.x, size.y, t, s);

        //Assign the REFERENCE here, don't copy the object, this is important
        //We do this so the position will stay linked for easier rendering
        sprite.bounds = this.bounds;
    }

    @Override
    public void draw(Camera c) {
        sprite.draw(c);
    }

    @Override
    public void cleanup() {
        sprite.cleanup();
    }
}
