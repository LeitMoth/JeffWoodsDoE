package jeffgame.states;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gameobject.*;
import jeffgame.phys.Rectangle;
import jeffgame.sound.Music;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class StateBossFight extends StatePlay {

    Music song = new Music("/sound/final boss- corona.wav");
    private int timer = 0;

    @Override
    public void init(JeffWoods engine) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        camera.setDefaultZoom(0.3f);
        song.play();

        gameObjects.add(new Background(ResourceStore.getTexture("/texture/BossArena.png")));

        player = new Player(engine);
        //Give player extra health for the boss
        player.heal(5);
        gameObjects.add(player);

        gameObjects.add(new Brick(new Rectangle(150,-50, 30/2.f,30/2.f)));
        gameObjects.add(new Brick(new Rectangle(-150,-50, 30/2.f,30/2.f)));
        gameObjects.add(new Brick(new Rectangle(-250,-150, 40/2.f,70/2.f)));
        gameObjects.add(new Brick(new Rectangle(250,-150, 40/2.f,70/2.f)));
        gameObjects.add(new Brick(new Rectangle(300,-170, 40/2.f,470/2.f)));
        gameObjects.add(new Brick(new Rectangle(-300,-170, 40/2.f,470/2.f)));
        gameObjects.add(new Brick(new Rectangle(0,-170, 600/2.f,15/2.f)));

    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);

        int bossSpawnTime = (int) (4.8 * 60);
        if(timer == bossSpawnTime)
        {
            gameObjects.add(new Boss(
                    new Rectangle(0,1000,100/2.f,100/2.f),
                    Boss.ID.values()[new Random(System.currentTimeMillis()).nextInt(Boss.ID.values().length)]));
        }

        int toDealWith = 0;
        for(IGameObject gameObject : gameObjects)
        {
            if(gameObject instanceof Boss) toDealWith++;
        }
        if(toDealWith == 0 && timer >= bossSpawnTime)
        {
            engine.switchState(new StateCredits());
            return;
        }

        //Adding Player Death function here
        if(player.getHealth() <= 0){
            engine.switchState(new StateGameOver());
        }

        timer++;
    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        song.stop();
    }

}