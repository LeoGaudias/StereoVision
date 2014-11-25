package stereo;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class myImage extends BufferedImage {
	
	public ArrayList<Point> reperes;
	
	public myImage(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), img.getType());
		this.setData(img.getData());
		reperes = new ArrayList<Point>();
	}
	
	public MyColor getHSB(int i, int j) {
		Color c = new Color(this.getRGB(i, j));
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		return new MyColor(hsb);
	}
	
	//Retourne un mask autour d'un point
	public Mask getMask(int[] center, int size) {
		MyColor[][] colors = new MyColor[size*2+1][size*2+1];
		for (int i = -size, k = 0; i<=size; i++, k++) {
			for (int j = -size, l = 0; j<=size; j++, l++) {
				colors[k][l] = getHSB(i+center[0], j+center[1]);
//				System.out.println("["+k+","+l+"]="+colors[k][l].toString());
			}
		}
		return new Mask(colors, center);
	}
	
	public boolean findMask(Mask m) {
		float max = 0.0f;
		for (int j = m.centre[1]-m.taille; j<m.centre[1]+m.taille; j++) {
			for (int i = m.taille; i<this.getWidth()-m.taille; i++) {
				Mask m2 = getMask(new int[]{i, j}, m.taille);
				float ressemble = m.ressemblance(m2);
				if (ressemble > max) {
					max = ressemble;
					System.out.println("["+i+","+j+"]="+ressemble);
				}
			}
		}
		return true;
	}

	public ArrayList<Point> findGreenPoints(){

		int width = this.getWidth();
		int height = this.getHeight();
		
		float sat = 0;
		float bri = 0;
		boolean in;
		
		ArrayList<Point> li = new ArrayList<Point>();
		int r  = 2;
		for(int i = r; i < width - r; i++){
			for(int j = r; j < height - r; j++){
				sat = 0;
				bri = 0;
				in = true;
				for(int m = -r; m < r + 1 && in; m++){
					for(int n = -r; n < r + 1 && in; n++){
						MyColor mc = this.getHSB(i+m, j+n);
						sat += mc.s;
						bri += mc.b;
						if(!(mc.h > 0.31
								&& mc.h < 0.50
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
			reperes.add(Centre);
			nbCentre++;
		}
		
		
		System.out.println(reperes
				+"\nnombre points verts : "+nbCentre);
		
		return reperes;
		
	}

}
