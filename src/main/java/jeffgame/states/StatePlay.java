package jeffgame.states;

import jeffgame.gameobject.Brick;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.IPhysStatic;
import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.IGameObject;
import jeffgame.gameobject.Player;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Camera;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class StatePlay implements IGameState{

    public ArrayList<IGameObject> gameObjects;
    public Camera camera = new Camera();

    @Override
    public void init() {

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        gameObjects = new ArrayList<>();

        Sprite boss =
                new Sprite(
                        30,30,
                        ResourceStore.getTexture("/FinalBoss.png"),
                        ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
                );

        boss.getPosition().add(-40,60);
        gameObjects.add(boss);

        gameObjects.add(new Brick(30,30,-80,60));
        gameObjects.add(new Brick(30,30,-150,-30));

        gameObjects.add(new Player());
        gameObjects.add(new Brick(40,70,30,0));

        gameObjects.add(new Brick(40,70,71,0));
        gameObjects.add(new Brick(40,70,30,-71));
        gameObjects.add(new Brick(40,70,71,-71));

        gameObjects.add(new Brick(600,2,0,-100));

        gameObjects.add(new Brick(30,30,120,100));
        gameObjects.add(new Brick(30,30,160,160));
        gameObjects.add(new Brick(30,30,60,200));
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
        gameObjects.stream().filter(gameObjDyn -> gameObjDyn instanceof IPhysDyn).forEach(gameObjectDyn -> {
            IPhysDyn physDyn = (IPhysDyn) gameObjectDyn;
            gameObjects.stream().filter(gameObjStatic -> gameObjStatic instanceof IPhysStatic).sorted( (objStatic1, objStatic2) -> {
                return (int) (physDyn.getHandler().getCenterDistanceSqrd((IPhysStatic)objStatic1) - physDyn.getHandler().getCenterDistanceSqrd((IPhysStatic) objStatic2));
            }).forEach(objStatic -> {
                physDyn.getHandler().handle((IPhysStatic)objStatic);
            });
        });

        camera.update(engine);
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
