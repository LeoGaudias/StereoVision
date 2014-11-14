package img;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		Image Myimg = new Image("img\\pv.jpg");
		//Image Myimg = new Image("img\\empty_left.jpg");
		
		int width = Myimg.img.getWidth();
		int height = Myimg.img.getHeight();
		
		float hue;
		boolean in;
		
		ArrayList<Point> li = new ArrayList<Point>();
		boolean point_pres = false;
		int r  = 4;
		for(int i = r; i < width - r; i++){
			for(int j = r; j < height - r; j++){
				
				in = true;
				for(int m = -r; m < r + 1 && in; m++){
					for(int n = -r; n < r + 1 && in; n++){
						hue = Myimg.hue[i+m][j+n];
						if(hue < 0.23 || hue > 0.43)
							in = false;
					}
				}
				if(in){
					Point p1 = new Point(i,j);
					li.add(p1);		
				}	
			}
		}
		
		System.out.println(li);
		
		ArrayList<Point> centres = new ArrayList<Point>();
		
		for(Point pi : li){
			boolean isCentre = true;
			for(Point pj : centres){
				if(Math.abs(pj.x - pi.x) < 15 && Math.abs(pj.y - pi.y) < 15)
					isCentre = false;
			}
			if(isCentre)
				centres.add(pi);
		}
		System.out.println(centres);
		
		
		
	}

}
