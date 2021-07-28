package jeffgame.gameobject;

import jeffgame.JeffWoods;

public interface IInteractable {
    void interact(IInteractable affected, JeffWoods engine);
}
