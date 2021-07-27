import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {

    private int textureID;

    public Texture(String path)
    {
        ByteBuffer imageBuffer;
        try {
            //I struggled with this for so long
            //It's a bit strange, but this is the best way I've found to load the image into a ByteBuffer
            //(ByteBuffer.wrap() does not work here for some reason)
            byte[] bytes = (getClass().getResourceAsStream(path)).readAllBytes();
            imageBuffer = BufferUtils.createByteBuffer(bytes.length);
            imageBuffer.put(bytes);
            imageBuffer.flip();

            System.out.println(bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(imageBuffer.remaining());

        ByteBuffer image;

        int width, height, components;

        try (MemoryStack stack = stackPush()) {
            IntBuffer w;
            IntBuffer h;
            IntBuffer comp;
            w = stack.mallocInt(1);
            h = stack.mallocInt(1);
            comp = stack.mallocInt(1);

            if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            } else {
                System.out.println("OK with reason: " + stbi_failure_reason());
            }

            // Decode the image
            image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            width = w.get(0);
            height = h.get(0);
            components = comp.get(0);

        }

        int format = components == 4 ? GL_RGBA : GL_RGB;
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, image);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(image);
    }

    public void bind()
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void cleanup() {
        glDeleteTextures(textureID);
    }

}
