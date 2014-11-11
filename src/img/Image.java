package img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	public static void main(String[] arg){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("C:\\Users\\maxime\\Desktop\\empty_left.jpg"));
		} catch (IOException e) {
		}
		
		System.out.println(img);
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		int pixel;
		Color c;
		
		int r, g, b;
		float max = -1, min = 1000, dif;
		
		
		
		int tmp = 0, t, l, s;
		
		float[] myC = new float[3];
		
		MyColor[][] hsb = new MyColor[width][height];
		boolean f = false;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				pixel = img.getRGB(i, j);
				c = new Color(pixel);
				
				myC = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
				
				MyColor myColor = new MyColor(myC[0], myC[1], myC[2]);
				hsb[i][j] = myColor;
				
				if(myC[0] > 0.34 && myC[0] < 0.43 && !f){
					System.out.println("x :"+ i + ",y = "+j);
					f = true;
				}
				else
					f = false;
					
				
				if(max < myC[0]){max = myC[0];} 
				if(min > myC[0]){min = myC[0];} 
				
			}
		}	
		System.out.println("MAX "+max);
		System.out.println("MIN "+min);
		
	}

}
