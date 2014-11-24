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
	float[][] satu;
	float[][] bri;
	
	public Image(String chemin){
		try {
		    img = ImageIO.read(new File(chemin));
		} 
		catch (IOException e) { }
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		hue = new float[width][height];
		satu = new float[width][height];
		bri = new float[width][height];
		
		int pixel;
		Color c;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				pixel = img.getRGB(i, j);
				c = new Color(pixel);
				float[] col = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
				hue[i][j] = col[0];
				satu[i][j] = col[1];
				bri[i][j] = col[2];
			}
		}	
	}
	
	
	
	public ArrayList<Point> SearchPointGreen(){

		int width = this.img.getWidth();
		int height = this.img.getHeight();
		
		float hue = 0;
		float sat = 0;
		float bri = 0;
		boolean in;
		
		ArrayList<Point> li = new ArrayList<Point>();
		boolean point_pres = false;
		int r  = 2;
		for(int i = r; i < width - r; i++){
			for(int j = r; j < height - r; j++){
				sat = 0;
				bri = 0;
				in = true;
				for(int m = -r; m < r + 1 && in; m++){
					for(int n = -r; n < r + 1 && in; n++){
						hue = this.hue[i+m][j+n];
						sat += this.satu[i+m][j+n];
						bri += this.bri[i+n][j+n];
						if(!(hue > 0.31
								&& hue < 0.50
								))
							in = false;
					}
				}
				sat = sat/((2*r + 1)*(2*r + 1));
				bri = bri/((2*r + 1)*(2*r + 1));
				
				if(in
						&& sat > 0.12
						&& bri > 0.282 
						)
				{
					Point p1 = new Point(i,j);
					li.add(p1);
				}
			}
		}
				
		ArrayList<ArrayList<Point>> patates = new ArrayList<ArrayList<Point>>();
		
		int nbCentre = 0;
		for(Point pi : li){
			
			boolean added = false;
			for(ArrayList<Point> l : patates){
				if(Math.abs(l.get(0).x - pi.x) < 20 && Math.abs(l.get(0).y - pi.y) < 20){
					l.add(pi);
					added = true;
					break;
				}
			}
			if(!added){
				ArrayList<Point> l = new ArrayList<Point>();
				l.add(pi);
				patates.add(l);
			}	
		}
		
		ArrayList<Point> centres = new ArrayList<Point>();
		
		for(ArrayList<Point> l : patates){
			
			int maxX = 0, minX = width, maxY = 0, minY = height;			
			for(Point p : l){
				if(p.x > maxX)
					maxX = p.x;
				if(p.x < minX)
					minX = p.x;
				if(p.y > maxY)
					maxY = p.y;
				if(p.y < minY)
					minY = p.y;
			}
			
			int x = (maxX - minX)/2 + minX;
			int y = (maxY - minY)/2 + minY;
			Point Centre = new Point(x,y);
			centres.add(Centre);
			nbCentre++;
		}
		
		
		System.out.println(centres
				+"\nnombre points verts : "+nbCentre);
		
		return centres;
		
	}
	
	public static void main(String[] args){
		Image Myimg = new Image("img/empty_left.jpg");
		Myimg.SearchPointGreen();
	}

}
