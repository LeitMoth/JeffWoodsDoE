package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.gameobject.*;
import jeffgame.gfx.Camera;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.IPhysStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatePlay implements IGameState {

    public Player player = null;
    public ArrayList<IGameObject> gameObjects = new ArrayList<>();
    public ArrayList<IGameObject> toRemove = new ArrayList<>();

    public Camera camera = new Camera();

    public void queueRemoveGameObject(IGameObject gameObject) {
        toRemove.add(gameObject);
    }

    @Override
    public void init(JeffWoods engine) {
    }

    @Override
    public void update(JeffWoods engine) {
        for(IGameObject gameObject : gameObjects)
        {
            gameObject.update(engine);
        }

        /*
        Could be more optimized
        current process:

        Get all Dynamics in array x[]
        For each element of x[]
            Get all Statics in array y[]
            Sort array y[] by distance
            For each element of y[]
                The current x (Dynamic) handles each y (Static) in order

        possibly better process by changing:
        Get all static in array y[]
        to:
        Get all static in array y[] that are currently colliding

        However, I will not go with this process, because it leaves a gap:
        If a Dynamic resolves away from one Static *into* a different Static, the second static
        won't be handled because it wasn't part of the initial list of colliding Statics

        If this becomes too slow, I can optimize by reasonable distance
        (Something like summing the perimeter of each object then squaring, then comparing that
        to distance squared to filter out faw away rectangles)
         */
        Stream<IPhysDyn> dynamics = gameObjects.stream().filter(gameObjDyn -> gameObjDyn instanceof IPhysDyn).map(gameObject -> (IPhysDyn) gameObject);
        dynamics.forEach(physDyn -> {
            gameObjects.stream().filter(gameObjStatic -> gameObjStatic instanceof IPhysStatic).sorted( (objStatic1, objStatic2) -> {
                return (int) (physDyn.getHandler().getCenterDistanceSqrd((IPhysStatic)objStatic1) - physDyn.getHandler().getCenterDistanceSqrd((IPhysStatic) objStatic2));
            }).forEach(objStatic -> {
                physDyn.getHandler().handle((IPhysStatic)objStatic);
            });
        });

        /*
        Get list of all interactable and dynamic objects
        Match each every item in the list to every other item once, and make them interact with each other if they are colliding
         */

        List<IGameObject> intDynObjs = gameObjects.stream()
                .filter(intDyn -> intDyn instanceof IInteractable && intDyn instanceof IPhysDyn)
                .collect(Collectors.toList());

        for(int i = 0; i < intDynObjs.size() - 1; i++ ) {
            for(int j = i + 1; j < intDynObjs.size(); j++ ) {
                if(
                        ((IPhysDyn) intDynObjs.get(i)).getHandler().isColliding((IPhysDyn) intDynObjs.get(j))
                ) {
                    ((IInteractable) intDynObjs.get(i)).interact((IInteractable) intDynObjs.get(j), engine);
                    ((IInteractable) intDynObjs.get(j)).interact((IInteractable) intDynObjs.get(i), engine);
                }
            }
        }

        camera.update(engine);

        //MUST NOT MODIFY GAMEOBJECTS PAST THIS POINT
        for(IGameObject g : toRemove)
        {
            gameObjects.remove(g);
        }
        toRemove.clear();
    }

    @Override
    public void render() {
        for(IGameObject g : gameObjects)
        {
            g.draw(camera);
        }
    }

    public void cleanup()
    {
        for(IGameObject object : gameObjects)
        {
            object.cleanup();
        }
    }

}
