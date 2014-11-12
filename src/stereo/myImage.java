package stereo;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class myImage {
	
	BufferedImage image;
	
	Color [][] rgbPixels;
	MyColor [][] hsbPixels;
	
	int [] reperes;
	
	public myImage(BufferedImage img) {
		
		image = img;
		
		rgbPixels = new Color[image.getWidth()][image.getHeight()];
		hsbPixels = new MyColor[image.getWidth()][image.getHeight()];
		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color c = new Color(image.getRGB(i, j));
				rgbPixels[i][j] = c;
				float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
				hsbPixels[i][j] = new MyColor(hsb);
			}
		}
		
	}
	
	//Retourne un mask autour d'un point
	public Mask getMask(int center) {
		return null;
	}
	
	public void getReperes() {} // trouver les points de reperes de l'image
	 // à partir du mask correspondant au point de base, retrouver le point correspondant dans cette image
	public int getMatchingPoint(Mask m) {
		return 0;
	}

}
