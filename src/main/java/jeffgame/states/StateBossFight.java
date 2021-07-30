package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.*;
import jeffgame.sound.Music;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class StateBossFight extends StatePlay {

    Music song = new Music("/sound/final boss- corona.wav");

    @Override
    public void init(JeffWoods engine) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        camera.setDefaultZoom(0.3f);
        song.play();

        gameObjects.add(new Background(ResourceStore.getTexture("/texture/BossArena.png")));

        player = new Player(engine);
        gameObjects.add(player);

        gameObjects.add(new Brick(30,30,150,-50));
        gameObjects.add(new Brick(30,30,-150,-50));
        gameObjects.add(new Brick(40,70,-250,-150));
        gameObjects.add(new Brick(40,70,250,-150));
        gameObjects.add(new Brick(40,470,300,-170));
        gameObjects.add(new Brick(40,470,-300,-170));
        gameObjects.add(new Brick(600,15,0,-170));

        gameObjects.add(new Brick(30,30,120,200));

        gameObjects.add(new Boss(new Vector2f(0,25550), new Vector2f(100,100), Boss.ID.Boss1));

        gameObjects.add(new Collectable(new Vector2f(160, 100), new Vector2f(20,50),
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl")));
    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);

        int toDealWith = 0;
        for(IGameObject gameObject : gameObjects)
        {
            if(gameObject instanceof Boss) toDealWith++;
        }
        if(toDealWith == 0)
        {
            engine.switchState(new StateCredits());
            return;
        }

        //Adding Player Death function here
        if(player.getHealth() <= 0){
            engine.switchState(new StateGameOver());
        }

    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        song.stop();
    }

}