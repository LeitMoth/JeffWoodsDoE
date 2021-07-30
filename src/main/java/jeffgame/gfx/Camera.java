package jeffgame.gfx;

import jeffgame.JeffWoods;
import jeffgame.states.StateBossFight;
import jeffgame.states.StatePlay;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public final float HEIGHT = 100.0f;
    public final float WIDTH = HEIGHT * (16.f/9.f);

    private Matrix4f proj = new Matrix4f();
    private Matrix4f view = new Matrix4f();

    public Vector2f pos = new Vector2f();

    public Vector2f getPos() {
        return pos;
    }


    public float zoomMax = 3f;
    public float zoomMin = 0.2f;
    public float zoomStart = 1;

    public float zoom;

    public Camera() {
        pos.x = 0;
        pos.y = 0;
        zoom = zoomStart;
    }

    public void setDefaultZoom(float zoom)
    {
        this.zoom = zoom;
        this.zoomStart = zoom;
    }

    public Matrix4f getMVP(Vector2f modelPos)
    {
        proj.setOrtho(-WIDTH/2,WIDTH/2,-HEIGHT/2,HEIGHT/2, -1, 1);
        view.identity().scale(zoom).translate(-pos.x, -pos.y, 0);
        Matrix4f model = new Matrix4f();
        model.identity().translate(modelPos.x, modelPos.y, 0);
        return proj.mul(view.mul(model));
    }

    public void update(JeffWoods engine)
    {
        Window window = engine.getWindow();
        float zoom_speed = 0.2f;
        if(window.keyDown(GLFW_KEY_KP_7))
        {
            zoom += zoom_speed;
        }
        if(window.keyDown(GLFW_KEY_KP_9))
        {
            zoom -= zoom_speed;
        }
        if(zoom > zoomMax) zoom = zoomMax;
        if(zoom < zoomMin) zoom = zoomMin;

        if(window.keyDown(GLFW_KEY_KP_5))
        {
            zoom = zoomStart;
        }

        if(!window.keyDown(GLFW_KEY_TAB))
        {
            if(engine.state instanceof StatePlay)
            {
                pos = new Vector2f(( (StatePlay) engine.state).player.getBounds().center);
            }
        }
        else {
            float mov_speed = 2;
            if (window.keyDown(GLFW_KEY_KP_4)) {
                pos.x -= mov_speed;
            }
            if (window.keyDown(GLFW_KEY_KP_6)) {
                pos.x += mov_speed;
            }
            if (window.keyDown(GLFW_KEY_KP_2)) {
                pos.y -= mov_speed;
            }
            if (window.keyDown(GLFW_KEY_KP_8)) {
                pos.y += mov_speed;
            }
        }
    }

}
