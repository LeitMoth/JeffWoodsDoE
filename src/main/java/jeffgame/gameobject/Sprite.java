package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Mesh;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.Rectangle;
import org.joml.Vector2f;

public class Sprite extends Mesh implements IGameObject {

    private Texture texture;

    public Texture getTexture() {
        return texture;
    }

    private Shader shader;

    public Shader getShader() {
        return shader;
    }

    protected Rectangle bounds = new Rectangle();

    float[] positions = {
            -0.5f,  0.5f,
             0.5f,  0.5f,
             0.5f, -0.5f,
            -0.5f, -0.5f,
    };
    float[] texCoords = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
    };
    int[] indices = {
            0,1,2,
            2,3,0,
    };

    public Sprite(float width, float height, jeffgame.gfx.Texture t, Shader s) {
        for(int i = 0; i < positions.length; )
        {
            positions[i++] *= width;
            positions[i++] *= height;
        }
        super.create(positions, texCoords, indices);
        texture = t;
        shader = s;

        bounds.halfSize.x = width/2;
        bounds.halfSize.y = height/2;
        bounds.center.x = 0;
        bounds.center.y = 0;
    }

    public Vector2f getPosition()
    {
        return bounds.center;
    }

    @Override
    public void cleanup()
    {
        super.cleanup();
    }

    @Override
    public void draw(Camera c) {
        shader.bind();
        texture.bind();

        shader.setUniform("texture_sampler",0);
        shader.setUniform("MVP", c.getMVP(bounds.center));

        drawMesh();
    }

    @Override
    public void update(JeffWoods engine) {
    }
}
