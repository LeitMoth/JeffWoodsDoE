package jeffgame;

import jeffgame.gameobject.IGameObject;
import jeffgame.gameobject.Player;
import jeffgame.gfx.Camera;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class JeffWoods {
    public static void main(String[] args) {
        new JeffWoods().run();
    }

    private Window window;

    public Window getWindow() {
        return window;
    }

    /*temp*/ Sprite sprite;

    public ArrayList<IGameObject> gameObjects;
    public Camera camera = new Camera();

    private void run()
    {
        init();
        loop();
        cleanup();
    }

    private void loop()
    {
        final long nanos_per_update = 1000000000/60;
        final int  max_steps = 10;

        long current_time = System.nanoTime();

        while(window.isOpen())
        {
            long new_time = System.nanoTime();
            for(int i = 0; current_time < new_time && i < max_steps; ++i)
            {
                update();
                current_time += nanos_per_update;
            }
            current_time = Math.max(current_time, new_time);

            render();
        }
    }

    private void init()
    {
        ResourceStore.init();

        window = new Window();

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearColor(0.00f,0.20f,0.40f, 0.0f);
        glEnable(GL_DEPTH_TEST);

        window.manualSetAspect();

        gameObjects = new ArrayList<>();

        gameObjects.add(new Sprite(
                20,40,
                ResourceStore.getTexture("/vem.png"),
                ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
        ));


        gameObjects.add(new Player());

    }

    private void cleanup()
    {
        for(IGameObject object : gameObjects)
        {
            object.cleanup();
        }
        ResourceStore.cleanup();
        window.close();
    }

    private void update()
    {
        for(IGameObject gameObject : gameObjects)
        {
            gameObject.update(this);
        }

        float zoom_speed = 0.1f;
        if(window.keyDown(GLFW_KEY_KP_7))
        {
            camera.zoom += zoom_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_9))
        {
            camera.zoom -= zoom_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_5))
        {
            camera.zoom = 1;
        }

        float mov_speed = 1;
        if(window.keyDown(GLFW_KEY_KP_4))
        {
            camera.pos.x -= mov_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_6))
        {
            camera.pos.x += mov_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_2))
        {
            camera.pos.y -= mov_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_8))
        {
            camera.pos.y += mov_speed;
        }

    }

    private void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        for(IGameObject g : gameObjects)
        {
            g.draw(camera);
        }

        window.swapBuffers();

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        window.pollEvents();
    }
}
