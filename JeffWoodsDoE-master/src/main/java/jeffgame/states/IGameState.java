package jeffgame.states;

import jeffgame.JeffWoods;

public interface IGameState {
    void init();
    void update(JeffWoods engine);
    void render();
    void cleanup();
}
