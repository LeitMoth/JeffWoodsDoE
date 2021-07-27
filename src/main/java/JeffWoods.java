import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL33.*;


public class JeffWoods {
	//A State detecting if the game is in the menu or in the game.
	private enum GAMESTATE{
		Menu,
		Game
	};
	private GAMESTATE state = GAMESTATE.Menu;
	private GameMenu menu;
	
    public static void main(String[] args) {
        new JeffWoods().run();
    }

    private Window window;

    /*temp*/ Sprite sprite;
    /*Also temp*/ Sprite backgroundSprite;

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
 //Changed window class so that we can edit height & width from inside the main function instead of opening the window function every time.
        window = new Window("Jeff Woods: Destroyer of evil", 400,400); 
        menu = new GameMenu();
        
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
    

        window.manualSetAspect();
        

        sprite = new Sprite(
                ResourceStore.getTexture("/vem.png"),
                ResourceStore.getShader("/tex.vs.glsl", "/tex.fs.glsl")
                
        );

    }
    
    
    

    private void cleanup()
    {
        sprite.cleanup();
        ResourceStore.cleanup();
        window.close();
    }

    private void update()
    {

    }

    private void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        if (state == GAMESTATE.Game) {
        sprite.draw();

        } else if(state == GAMESTATE.Menu) {
        	
        	
        }
        window.swapBuffers();
        
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        window.pollEvents();
    }
}
