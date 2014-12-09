package stereo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class StereoImages {

	public myImage imgLeft;
	public myImage imgRight;
	ArrayList<Vector<Point>> matchs;
	
	double cm1;
	double mmperpixel;
	int ecart;
	double ecartPix;
	
	double D; // distance entre les deux centres optiques (10cm)
	double f; //distance focale
	
	
	public StereoImages(myImage left, myImage right, Object e) {
		int ec = (Integer)e;
		imgLeft = left;
		imgRight = right;
		initValues(left, ec);
		matchs = new ArrayList<Vector<Point>>();
	}
	
	public void correlation() {
		for (Point p : imgLeft.reperes) {
			Mask m1 = imgLeft.getMask(p, 20);
			Point p2 = imgRight.findMask(m1);
			
			Vector<Point> v = new Vector<Point>();
			v.add(p);
			v.add(p2);
			matchs.add(v);
		}
	}
	
	public void initValues(myImage left, int e) {
//		double cm50 = (left.fond[0].distance(left.fond[1])
//				+left.fond[1].distance(left.fond[3])
//				+left.fond[3].distance(left.fond[2])
//				+left.fond[2].distance(left.fond[0]))/4;
		//System.out.println(cm50/50);
//		cm1 = 21.6/50;
		cm1 = 21.6;
		mmperpixel = 0.16947;
		ecart = e*10;
		ecartPix = e/mmperpixel;
		
		
		D = (e*10)/mmperpixel;
		f = 3.4/mmperpixel;
	}
	
//	public void calcul() {
//		Point p1 = matchs.get(0).get(0);
//		Point p2 = matchs.get(0).get(1);
//		double d = p1.distance(p2);
//		double o1o2 = ecartPix;
//		double focale = cm1*0.34;
////		Point milieu = new Point(imgLeft.getWidth()/2, imgLeft.getHeight()/2);
////		double distanceMilieu = p1.distance(milieu);
//		double o1p1 = Math.sqrt(Math.pow(focale, 2)+Math.pow(focale, 2));
//		
//		double profondeur = (d/o1o2)*o1p1;
//		System.out.println(profondeur/cm1);
//	}
	
	public void calcul() {
		correlation();
		for (Vector<Point> v : matchs) {
			System.out.println(v.get(0).toString()+" : "+calculProfondeur(v));
		}
	}
	
	public double calculProfondeur(Vector<Point> pts) {
		Point p2 = pts.get(0);
		Point p1 = pts.get(1);
		
		double delta = p1.distance(p2);
		double z = (2*D*f)/delta;
		return z;
		
		
		
//		double d = p1.distance(p2);
////		System.out.println("d:"+d);
//		double o1o2 = ecartPix;
////		System.out.println("o1o2:"+o1o2);
//		double focale = mmperpixel*3.4;
//		Point milieu = new Point(imgLeft.getWidth()/2, imgLeft.getHeight()/2);
//		double distanceMilieu = p1.distance(milieu);
////		System.out.println("distanceMilieu:"+distanceMilieu);
//		double o1p1 = Math.sqrt(Math.pow(focale, 2)+Math.pow(distanceMilieu, 2));
////		System.out.println("o1p1:"+o1p1);
//		
//		double profondeur = (d/o1o2)*o1p1;
//		return profondeur*mmperpixel/10;
	}
	
}
