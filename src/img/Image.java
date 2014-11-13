package img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	BufferedImage img;
	float[][] hue;
	
	public Image(String chemin){
		try {
		    img = ImageIO.read(new File(chemin));
		} 
		catch (IOException e) { }
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		hue = new float[width][height];
		
		int pixel;
		Color c;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				pixel = img.getRGB(i, j);
				c = new Color(pixel);
				hue[i][j] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[0];
			}
		}	
	}
	
	
	
	public static void main(String[] arg){
		Image img = new Image("img\\book_left.jpg");
		
		
	}

}
