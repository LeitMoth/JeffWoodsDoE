package jeffgame.states;

import jeffgame.gameobject.*;
import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.phys.Rectangle;
import jeffgame.sound.Music;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.opengl.GL11.*;

public class StateLevel extends StatePlay {

    Music song = new Music("/sound/level_theme.wav");

    //Helper placement functions for level geometry
    public void middle(float x, float y, float w, float h)
    {
        corner_o(x - w/2,  y - h/2, w, h);
    }
    public void rona(float x, float y) {
        gameObjects.add(new Enemy(new Rectangle(x,y,30/2.f,30/2.f)));
    }
    public void corner_o(float x, float y, float w, float h)
    {
        corner(x,y,x+w,y+h);
    }
    public void corner(float x, float y, float x2, float y2) {
        float halfWidth = (x2 - x) / 2;
        float halfHeight = (y2 - y) / 2;
        gameObjects.add(new Brick(new Rectangle( x + halfWidth, y + halfHeight, halfWidth,halfHeight)));
    }
    public void bowl(float x, float y) {
        gameObjects.add(new Collectable(new Vector2f(x, y), new Vector2f(15,50),
                ResourceStore.getTexture("/texture/health.png"),
                ResourceStore.getShader("/shader/tex.vs.glsl","/shader/tex.fs.glsl")));
    }

    @Override
    public void init(JeffWoods engine) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        camera.setDefaultZoom(0.3f);
        gameObjects.add(new Background(ResourceStore.getTexture("/texture/city_background_night.png")));

        player = new Player(engine);
        player.getBounds().center.y = 40;
        gameObjects.add(player);

        corner(-200,-1000,1_000,0);
        corner(-1000,-1000,-200,1000);

        middle(-60,60,40,10);
        middle(60,100,40,10);
        middle(180,150,40,40);

        corner(200,0,400,200);
        corner(200,280,400,1000);
        bowl(300,240);

        rona(700, 50);

        middle(1150, 50, 60, 20);
        middle(1300, -50, 60, 20);
        middle(1460, 0, 60, 20);
        middle(1550, 80, 60, 20);

        bowl(1300,260);

        middle(1400, 160, 60, 20);
        middle(1550, 240, 60, 20);
        middle(1750, 240, 60, 20);

        middle(1900, 150, 30, 15);
        middle(1960, 230, 30, 15);
        middle(1920, 310, 30, 15);

        corner(2000, -1000, 2200, 400);
        corner(1800, 300, 1875, 1000);
        corner(1875, 500, 3000, 1000);

        middle(2300, 250, 70, 15);
        middle(2450, 300, 70, 15);
        rona(2450, 315);
        middle(2700, 300, 70, 15);
        middle(2800, 360, 40, 15);


        middle(3000, 200, 200, 30);
        rona(2930, 315);
        rona(3070, 315);

        middle(3200, 250, 80, 20);
        middle(3400, 310, 40, 15);
        middle(3550, 400, 20, 7.5f);
        bowl(3620, 500);

        song.play();
    }

    @Override
    public void update(JeffWoods engine) {
        super.update(engine);

        //victory check
        int toDealWith = 0;
        for(IGameObject gameObject : gameObjects)
        {
            if(gameObject instanceof Enemy || gameObject instanceof Collectable)
            {
                toDealWith++;
            }
        }
        if(toDealWith == 0)
        {
            engine.switchState(new StateBossFight());
            return;
        }

        //Adding Player Death function here
        if(player.getHealth() <= 0 || player.getBounds().center.y < -1000){
            engine.switchState(new StateGameOver());
        }

        if(engine.getWindow().keyDown(GLFW_KEY_R))
        {
            engine.switchState(new StateLevel());
        }

        if(engine.getWindow().keyDown(GLFW_KEY_S)){

            engine.switchState(new StateBossFight());
        }


    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        song.stop();
    }

}