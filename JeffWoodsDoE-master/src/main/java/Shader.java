import java.io.IOException;

import static org.lwjgl.opengl.GL33.*;

/*
Very helpful links:
https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter04/chapter4.html
https://learnopengl.com/Getting-started/Shaders
 */

public class Shader {

    private int programID;
    private int vertexID;
    private int fragmentID;

    public Shader(String vert, String frag) {
        programID = glCreateProgram();
        if (programID == 0) {
            System.err.println("Shader creation failed");
        }

        try {
            vertexID = createShader(getFileString(vert), GL_VERTEX_SHADER);
            fragmentID = createShader(getFileString(frag), GL_FRAGMENT_SHADER);

            link();
        }
        catch (Exception e)
        {
            System.err.println("Shader compilation/linking failed");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String getFileString(String path) throws IOException
    {
        return new String(getClass().getResourceAsStream(path).readAllBytes());
    }

    private int createShader(String code, int type) throws Exception
    {
        int shaderID = glCreateShader(type);
        if(shaderID == 0)
        {
            throw new Exception("Error creating shader type: " + type);
        }

        glShaderSource(shaderID, code);
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling shader: " + glGetShaderInfoLog(shaderID, 1024));
        }

        glAttachShader(programID, shaderID);

        return shaderID;
    }

    private void link() throws Exception
    {
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking shader: " + glGetProgramInfoLog(programID, 1024));
        }

        //Free shaders, they aren't needed after linking
        if(vertexID != 0)
        {
            glDetachShader(programID, vertexID);
        }
        if(fragmentID != 0)
        {
            glDetachShader(programID, fragmentID);
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
        {
            System.err.println("Warning validating shader: " + glGetProgramInfoLog(programID, 1024));
        }
    }

    public void bind()
    {
        glUseProgram(programID);
    }

    public void unbind()
    {
        glUseProgram(0);
    }

    public void cleanup()
    {
        unbind();
        if (programID != 0)
        {
            glDeleteProgram(programID);
        }
    }

    public void setUniform(String uniformName, int value)
    {
        glUniform1i(glGetUniformLocation(programID,uniformName), value);
    }

}
