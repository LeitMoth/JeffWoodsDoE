package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;
import jeffgame.gfx.Mesh;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import org.joml.Vector2f;

public class Sprite extends Mesh implements IGameObject {

    private Texture texture;
    private Shader shader;

    protected Vector2f size = new Vector2f(), position = new Vector2f();

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

        size.x = width;
        size.y = height;
        position.x = 0;
        position.y = 0;
    }

    public Vector2f getPosition()
    {
        return position;
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
        shader.setUniform("MVP", c.getMVP(position));

        super.draw();
    }

    @Override
    public void update(JeffWoods engine) {
    }
}