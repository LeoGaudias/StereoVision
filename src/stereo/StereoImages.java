package stereo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class StereoImages {

	public myImage imgLeft;
	public myImage imgRight;
	ArrayList<Vector<Point>> matchs;
	
	double cm1;
	int ecart;
	double ecartPix;
	
	public StereoImages(myImage left, myImage right, Object e) {
		int ec = (Integer)e;
		imgLeft = left;
		imgRight = right;
		initValues(left, ec);
		matchs = new ArrayList<Vector<Point>>();
		Vector<Point> v = new Vector<Point>();
		v.add(left.reperes.get(3));
		v.add(right.reperes.get(3));
		System.out.println(calculProfondeur(v));
		Vector<Point> v2 = new Vector<Point>();
		v2.add(left.reperes.get(0));
		v2.add(right.reperes.get(0));
		System.out.println(calculProfondeur(v2));
		Vector<Point> v3 = new Vector<Point>();
		v3.add(left.reperes.get(1));
		v3.add(right.reperes.get(1));
		System.out.println(calculProfondeur(v3));
//		matchs.add(v);
		System.out.println(left.reperes.toString());
		System.out.println(right.reperes.toString());
		
	}
	
	public void initValues(myImage left, int e) {
		double cm50 = (left.fond[0].distance(left.fond[1])
				+left.fond[1].distance(left.fond[3])
				+left.fond[3].distance(left.fond[2])
				+left.fond[2].distance(left.fond[0]))/4;
		//System.out.println(cm50/50);
		cm1 = cm50/50;
		ecart = e;
		ecartPix = e * cm1;
	}
	
	public void calcul() {
		Point p1 = matchs.get(0).get(0);
		Point p2 = matchs.get(0).get(1);
		double d = p1.distance(p2);
		double o1o2 = ecartPix;
		double focale = cm1*0.34;
//		Point milieu = new Point(imgLeft.getWidth()/2, imgLeft.getHeight()/2);
//		double distanceMilieu = p1.distance(milieu);
		double o1p1 = Math.sqrt(Math.pow(focale, 2)+Math.pow(focale, 2));
		
		double profondeur = (d/o1o2)*o1p1;
		System.out.println(profondeur/cm1);
	}
	
	public double calculProfondeur(Vector<Point> pts) {
		Point p1 = pts.get(0);
		Point p2 = pts.get(1);
		double d = p1.distance(p2);
		double o1o2 = ecartPix;
		double focale = cm1*0.34;
		Point milieu = new Point(imgLeft.getWidth()/2, imgLeft.getHeight()/2);
		double distanceMilieu = p1.distance(milieu);
		double o1p1 = Math.sqrt(Math.pow(focale, 2)+Math.pow(distanceMilieu, 2));
		
		double profondeur = -(d/o1o2)*o1p1;
		return profondeur/cm1;
	}
	
	//Retourne pour chaque pt de repere dans l'image gauche, son correspondant dans l'image droite
	public int[] correspondance() {
		return null;
	}
	
}
