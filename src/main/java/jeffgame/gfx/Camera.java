package jeffgame.gfx;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {

    public final float HEIGHT = 100.0f;
    public final float WIDTH = HEIGHT * (16.f/9.f);

    private Matrix4f proj = new Matrix4f();
    private Matrix4f view = new Matrix4f();

    public Vector2f pos = new Vector2f();
    public float zoom;

    public Camera() {
        pos.x = 0;
        pos.y = 0;
        zoom = 1;
    }

    public Matrix4f getMVP(Vector2f modelPos)
    {
        proj.setOrtho(-WIDTH/2,WIDTH/2,-HEIGHT/2,HEIGHT/2, -1, 1);
        view.identity().scale(zoom).translate(-pos.x, -pos.y, 0);
        Matrix4f model = new Matrix4f();
        model.identity().translate(modelPos.x, modelPos.y, 0);
        return proj.mul(view.mul(model));
    }

}
