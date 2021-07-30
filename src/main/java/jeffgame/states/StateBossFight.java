package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.*;
import jeffgame.gfx.Camera;
import jeffgame.phys.IPhysDyn;
import jeffgame.phys.IPhysStatic;
import jeffgame.sound.Music;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.opengl.GL11.*;

public class StateBossFight implements IGameState {

    public int localBossID;

    public ArrayList<IGameObject> gameObjects = new ArrayList<>();
    public ArrayList<IGameObject> toRemove = new ArrayList<>();

    public void queueRemoveGameObject(IGameObject gameObject) {
        toRemove.add(gameObject);
    }

    public Camera camera = new Camera();

    public Player player = null;

    Music song = new Music("/sound/final boss- corona.wav");

    private int playTimer = 0;

    @Override
    public void init(JeffWoods engine) {

        localBossID = StatePlay.bossID;

//        BossHandler handler = new BossHandler();
//        handler.BossGenerate(localBossID);

        camera.setDefaultZoom(0.3f);
//        musicHandler.PlayMusic("/sound/level_theme.wav");
        song.play();
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


//        gameObjects = new ArrayList<>();


        gameObjects.add(new Background(ResourceStore.getTexture("/texture/BossArena.png")));

        player = new Player(engine);
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

        gameObjects.add(new Brick(30,30,150,-50));

        gameObjects.add(new Brick(30,30,-150,-50));

        gameObjects.add(new Brick(40,70,-250,-150));

        gameObjects.add(new Brick(40,70,250,-150));

        gameObjects.add(new Brick(40,470,300,-170));

        gameObjects.add(new Brick(40,470,-300,-170));

        gameObjects.add(new Brick(600,15,0,-170));


        gameObjects.add(new Brick(30,30,120,200));

        gameObjects.add(new Boss(new Vector2f(0,100), new Vector2f(100,100), Boss.ID.Boss1));

        gameObjects.add(new Collectable(new Vector2f(160, 100), new Vector2f(20,50),
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl")));
    }

    @Override
    public void update(JeffWoods engine) {
        int toDealWith = 0;
        for(IGameObject gameObject : gameObjects)
        {
            gameObject.update(engine);
            if(gameObject instanceof Boss) toDealWith++;
        }

        if(toDealWith == 0)
        {
            engine.switchState(new StateCredits());
            return;
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

        //Adding Player Death function here
        if(player.getHealth() <= 0){
            engine.switchState(new StateGameOver());
        }

        // Imma just reuse the code for the Credits to make a boss timer
//       final int PLAY_TIME = 120; //seconds
//        if (++playTimer > PLAY_TIME*60) {
//            engine.switchState(new StateGameOver());
//        }

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
        song.stop();
    }

}