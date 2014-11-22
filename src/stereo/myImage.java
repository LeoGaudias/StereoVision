package stereo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class myImage {
	
	BufferedImage image;
	
	public ArrayList<int[]> reperes;
	
	public myImage(BufferedImage img) {
		image = img;
		reperes = new ArrayList<int[]>();
		reperes.add(new int[]{708, 318});
		reperes.add(new int[]{1178, 312});
	}
	
	public MyColor getHSB(int i, int j) {
		Color c = new Color(image.getRGB(i, j));
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
			for (int i = m.taille; i<image.getWidth()-m.taille; i++) {
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
	
	public void getReperes() {} // trouver les points de reperes de l'image
	 // à partir du mask correspondant au point de base, retrouver le point correspondant dans cette image
	public int getMatchingPoint(Mask m) {
		return 0;
	}

}
