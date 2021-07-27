package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;

public interface IGameObject {
    void draw(Camera c);
    void update(JeffWoods engine);
    void cleanup();
}
