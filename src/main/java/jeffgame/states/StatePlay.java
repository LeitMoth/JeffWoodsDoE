package jeffgame.states;

import jeffgame.gameobject.*;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.IPhysStatic;
import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Camera;
import jeffgame.sound.MusicHandler;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.*;

public class StatePlay implements IGameState{

    public ArrayList<IGameObject> gameObjects;
    public Camera camera = new Camera();

    MusicHandler musicHandler = new MusicHandler();

    public Player player = null;

    @Override
    public void init() {


        musicHandler.PlayMusic("/sound/level_theme.wav");
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        gameObjects = new ArrayList<>();


        gameObjects.add(new Background(ResourceStore.getTexture("/texture/city_background_night.png")));

        player = new Player();
        gameObjects.add(player);

//        Sprite boss =
//                new Sprite(
//                        30,30,
//                        ResourceStore.getTexture("/texture/FinalBoss.png"),
//                        ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
//                );
//
//        boss.getPosition().add(-40,60);
//        gameObjects.add(boss);

        gameObjects.add(new Brick(30,30,-80,60));
        gameObjects.add(new Brick(30,30,-150,-30));

        gameObjects.add(new Brick(40,70,30,0));

        gameObjects.add(new Brick(40,70,71,0));
        gameObjects.add(new Brick(40,70,30,-71));
        gameObjects.add(new Brick(40,70,71,-71));

        gameObjects.add(new Brick(600,2,0,-100));

        gameObjects.add(new Brick(30,30,120,100));
        gameObjects.add(new Brick(30,30,160,160));
        gameObjects.add(new Brick(30,30,60,200));

        gameObjects.add(new Enemy(new Vector2f(-80,110), new Vector2f(30,30)));
        gameObjects.add(new Enemy(new Vector2f(-50,40), new Vector2f(20,20)));

        gameObjects.add(new Collectable(new Vector2f(160, 100), new Vector2f(20,50),
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl")));
    }

    @Override
    public void update(JeffWoods engine) {
        for(IGameObject gameObject : gameObjects)
        {
            gameObject.update(engine);
        }

        //Adding Player Death function here

        int ch = player.getHealth();

        if(ch <= 0){
            engine.switchState(new StateGameOver());

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

        if(engine.getWindow().keyDown(GLFW_KEY_R))
        {
            engine.switchState(new StatePlay());
        }
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
        musicHandler.StopMusic();
    }
}
