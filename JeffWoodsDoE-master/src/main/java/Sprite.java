import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Sprite {

    private Texture texture;
    private Shader shader;

    private int VAO, VBO, IBO;
    private int indexCount;

    public Sprite(Texture t, Shader s)
    {
        texture = t;
        shader = s;

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        float[] data = {
                -0.5f,  0.5f,   0.0f, 0.0f,
                 0.5f,  0.5f,   1.0f, 0.0f,
                 0.5f, -0.5f,   1.0f, 1.0f,
                -0.5f, -0.5f,   0.0f, 1.0f,
        };

        VBO = glGenBuffers();
        FloatBuffer posBuf = MemoryUtil.memAllocFloat(data.length);
        posBuf.put(data).flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, posBuf, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4*4, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4*4, 2*4);

        int[] indices = {
                0,1,2,
                2,3,0,
        };

        IBO = glGenBuffers();
        IntBuffer indicesBuf = MemoryUtil.memAllocInt(indices.length);
        indicesBuf.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuf, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        indexCount = indices.length;
    }

    public void draw()
    {
        shader.bind();
        texture.bind();

        shader.setUniform("texture_sampler",0);

        glBindVertexArray(VAO);

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
    }

    public void cleanup()
    {
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glDeleteBuffers(VBO);
        glDeleteBuffers(IBO);

        glBindVertexArray(0);
        glDeleteVertexArrays(VAO);
    }

}
