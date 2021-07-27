import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureShadowTake {

	/*Hey! Shadow here, going to try to make my own take of the Texture class just to see if i can better
	 * understand what it is doing. i'll comment it all up to the best of my ability
	 * - Shadow
	 */
	
	private int id;
	private int width;
	private int height;
	
	
	public TextureShadowTake(String fileName) {
		
		//Starting by making the BufferedImage and asigning it to a file.
		BufferedImage biText;
		try {
			
			biText = ImageIO.read(new File(fileName));
			width = biText.getWidth();
			height = biText.getHeight();
			
			//Turn our BufferedImage into an array of pixels! FOR THE PIXEL REPUBLIC!
			int[] pixels_raw = new int[width * height * 4];
			pixels_raw = biText.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int i = 0; i < width; i++) {
				
				for(int x = 0; x < height; x++) {
					
					int pixel = pixels_raw[i*width+x];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); // Red channel
					pixels.put((byte) ((pixel >> 8) & 0xFF)); // Green Channel
					pixels.put((byte) ((pixel) & 0xFF)); // Blue Channel
					pixels.put((byte) ((pixel >> 24) & 0xFF)); // Alpha Channel
				}
				
			}
			
			pixels.flip(); //OpenGL apparently hates ImageBuffers, so you have to flip it around.
			
			//Lets get the openGL ball rolling!
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			//Paramaters?
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_BYTE,pixels);
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	//We are binding things now bois! i really dont understand open GL, but im sure nothing can go wrong, right?
	public void bind() {
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		
	}
	
	
	
}
