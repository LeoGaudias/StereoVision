package stereo;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class myImage extends BufferedImage {
	
	public ArrayList<Point> reperes;
	
	public Point[] fond;
	
	public myImage(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), img.getType());
		this.setData(img.getData());
		reperes = new ArrayList<Point>();
		fond = new Point[4];
	}
	
	public MyColor getHSB(int i, int j) {
		return new MyColor(new Color(this.getRGB(i, j)));
	}
	
	//Retourne un mask autour d'un point
	public Mask getMask(Point center, int size) {
		MyColor[][] colors = new MyColor[size*2+1][size*2+1];
		for (int i = -size, k = 0; i<=size; i++, k++) {
			for (int j = -size, l = 0; j<=size; j++, l++) {
				colors[k][l] = getHSB(i+center.x, j+center.y);
//				System.out.println("["+k+","+l+"]="+colors[k][l].toString());
			}
		}
		return new Mask(colors, center);
	}
	
	public boolean sameLine(Point p1, Point p2, int taille) {
		return (p2.getY() <= p1.getY()+taille && p2.getY() >= p1.getY()-taille && p2.getX() <= p1.getX());
	}
	
	public Point findMask(Mask m) {
		float max = 0.0f;
		Point pres = new Point();
		for (Point p : reperes) {
			Mask m2 = getMask(p, m.taille);
			if (sameLine(p, m.centre, m.taille*2+1)) {
				float ressemble = m.ressemblance(m2);
				if (ressemble > max) {
					max = ressemble;
					pres = p;
				}
			}
		}
		return pres;
	}

	public ArrayList<Point> findGreenPoints(){

		int width = this.getWidth();
		int height = this.getHeight();
		
		float sat = 0;
		float bri = 0;
		boolean in;
		
		ArrayList<Point> li = new ArrayList<Point>();
		int r  = 1;
		
		for(int i = r; i < width - r; i++){
			for(int j = r; j < height - r; j++){
				float min = 1, max = 0;
				sat = 0;
				bri = 0;
				in = true;
				for(int m = -r; m < r + 1 && in; m++){
					for(int n = -r; n < r + 1 && in; n++){
						
						MyColor mc = this.getHSB(i+m, j+n);
						if(mc.h > max)
							max = mc.h;
						if(mc.h < min)
							min = mc.h;
						sat += mc.s;
						bri += mc.b;
						if(!(mc.h > 0.27
								&& mc.h < 0.50
								))
							in = false;
					}
				}
				sat = sat/((2*r + 1)*(2*r + 1));
				bri = bri/((2*r + 1)*(2*r + 1));
				
				if(in
						&& sat > 0.32
						&& bri > 0.21 
						)
				{
					//System.out.println("i : "+i+" j : "+ j + " | sat : "+ sat+ " bri : "+bri+ " min/max :"+min+"/"+max);
					Point p1 = new Point(i,j);
					li.add(p1);
				}
			}
		}
				
		ArrayList<ArrayList<Point>> patates = new ArrayList<ArrayList<Point>>();
		
//		int nbCentre = 0;
		for(Point pi : li){
			
			boolean added = false;
			for(ArrayList<Point> l : patates){
				if(Math.abs(l.get(0).x - pi.x) < 25 && Math.abs(l.get(0).y - pi.y) < 25){
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
			
//			nbCentre++;
		}
		

		
		Point[] bords = new Point[4];
		bords[0] = new Point(0,0);
		bords[1] = new Point(getWidth(),0);
		bords[2] = new Point(0,getHeight());
		bords[3] = new Point(getWidth(),getHeight());
		
				
		
		Point tmp = reperes.get(0);
		for(int i = 0; i < 4; i++){
			
			for(Point pt : reperes){
				if(pt.distance(bords[i]) < tmp.distance(bords[i]))
					tmp = pt;	
			}
			fond[i] = tmp;
		}
		
//		System.out.println(fond[0] + " " + fond[1] + " " + fond[2] + " " + fond[3] + " ");

		
		/*System.out.println(reperes
				+"\nnombre points verts : "+nbCentre);*/
		
		return reperes;
		
	}
	
//	public Point[] IsCarree(){
//		
//		int maxX = 0, minX = getWidth(), maxY = 0, minY = getHeight();			
//		for(Point p : fond){
//			if(p.x > maxX)
//				maxX = p.x;
//			if(p.x < minX)
//				minX = p.x;
//			if(p.y > maxY)
//				maxY = p.y;
//			if(p.y < minY)
//				minY = p.y;
//		}
//		
////		System.out.println(maxX + " " + minX + " " + maxY + " " + minY + " ");
//		
//		int r = 200;
//		if(Math.abs(fond[0].x - minX) > r || Math.abs(fond[0].y - minY) > r){
//			fond[0] = new Point(fond[2].x, fond[1].y);
//			reperes.add(fond[0]);
//		}
//			
//		if(Math.abs(fond[1].x - maxX) > r || Math.abs(fond[1].y - minY) > r){
//			fond[1] = new Point(fond[3].x, fond[0].y);
//			reperes.add(fond[1]);
//		}
//			
//		if(Math.abs(fond[2].x - minX) > r || Math.abs(fond[2].y - maxY) > r){
//			fond[2] = new Point(fond[0].x, fond[3].y);
//			reperes.add(fond[2]);
//		}
//			
//		if(Math.abs(fond[3].x - maxX) > r || Math.abs(fond[3].y - maxY) > r){
//			fond[3] = new Point(fond[1].x, fond[2].y);
//			reperes.add(fond[3]);
//		}
//			
//		
//		
////		System.out.println(fond[0] + " " + fond[1] + " " + fond[2] + " " + fond[3] + " ");
//		
//		return fond;
//	}
	
//	public static void main(String[] args){
//		myImage img = null;
//		try {
//			img = new myImage(ImageIO.read(new File("img/book_right.jpg")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		img.findGreenPoints();
//		img.IsCarree();
//		
//	}

}