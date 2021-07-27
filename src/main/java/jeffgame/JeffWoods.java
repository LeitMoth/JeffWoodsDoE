package jeffgame;

import jeffgame.gfx.Window;
import jeffgame.states.IGameState;
import jeffgame.states.StateMenu;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL33.*;

public class JeffWoods {
    public static void main(String[] args) {
        new JeffWoods().run();
    }

    private Window window;
    public Window getWindow() {
        return window;
    }

    public IGameState state;
    public void switchState(IGameState state)
    {
        if(this.state != null) {
            this.state.cleanup();
        }

        this.state = state;
        if(this.state != null) {
            this.state.init();
        }
    }

    private void run()
    {
        init();
        loop();
        cleanup();
    }

    private void init()
    {
        ResourceStore.init();

        window = new Window(500, 500, "Jeff Woods: Destroyer of Evil");

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearColor(0.00f,0.20f,0.40f, 0.0f);

        window.manualSetAspect();

        switchState(new StateMenu());
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

    private void cleanup()
    {
        if(state != null)
        {
            state.cleanup();
        }
        ResourceStore.cleanup();
        window.close();
    }

    private void update()
    {
        state.update(this);
    }

    private void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        state.render();

        window.swapBuffers();

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        window.pollEvents();
    }
}
