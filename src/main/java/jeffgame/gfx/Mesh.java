package jeffgame.gfx;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL33.*;

/*
I did not straight copy, but this was a good refresher
https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter08/src/main/java/org/lwjglb/engine/graph/Mesh.java
 */

public class Mesh {

    protected int VAO;
    protected ArrayList<Integer> VBOs;
    protected int indexCount;

    public Mesh() { }

    public Mesh(float[] positions, float[] texCoords, int[] indices)
    {
        create(positions, texCoords, indices);
    }

    public void create(float[] positions, float[] texCoords, int[] indices)
    {
        //VAO
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        //VBOs
        VBOs = new ArrayList<>();

        //Position VBO
        int posVBO = glGenBuffers();
        FloatBuffer posBuf = MemoryUtil.memAllocFloat(positions.length);
        posBuf.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, posVBO);
        glBufferData(GL_ARRAY_BUFFER, posBuf, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        VBOs.add(posVBO);

        //Texture VBO
        int texVBO = glGenBuffers();
        FloatBuffer texCoordBuf = MemoryUtil.memAllocFloat(texCoords.length);
        texCoordBuf.put(texCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, texVBO);
        glBufferData(GL_ARRAY_BUFFER, texCoordBuf, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        VBOs.add(texVBO);

        //Indices VBO
        int indexVBO = glGenBuffers();
        IntBuffer indexBuf = MemoryUtil.memAllocInt(indices.length);
        indexBuf.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuf, GL_STATIC_DRAW);
        VBOs.add(indexVBO);

        //Cleanup
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        indexCount = indices.length;
    }

    public void drawMesh()
    {
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void cleanup()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int VBO : VBOs)
        {
            glDeleteBuffers(VBO);
        }

        glBindVertexArray(0);
        glDeleteVertexArrays(VAO);
    }

}
