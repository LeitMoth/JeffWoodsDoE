package jeffgame.gameobject;

import jeffgame.gfx.Camera;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.Rectangle;

public class SpriteEntity extends Entity {

    protected Sprite sprite;

    public SpriteEntity(Rectangle bounds, Texture t, Shader s) {
        this.bounds = bounds;
        sprite = new Sprite(bounds, t, s);
    }

    @Override
    public void draw(Camera c) {
        if(hitCooldown < 0 || hitCooldown % 2 == 0) {
            sprite.draw(c);
        }
    }

    @Override
    public void cleanup() {
        sprite.cleanup();
    }
}
