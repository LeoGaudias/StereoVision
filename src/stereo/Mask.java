package stereo;

public class Mask {
	public int taille; //Longueur ou largeur /2
	public int[] centre;
	public MyColor pixels[][];
	
	public Mask(int taille, int[] centre) {
		pixels = new MyColor[taille*2+1][taille*2+1];
	}
	
	public Mask(MyColor[][] colors, int[] center) {
		pixels = colors;
		taille = (colors.length-1)/2;
		centre = center;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Mask) {
			Mask m = (Mask) obj;
			if (taille == m.taille) {
				for (int i = 0; i<taille*2+1; i++) {
					for (int j = 0; j<taille*2+1; j++) {
						if (!pixels[i][j].equals(m.pixels[i][j])) {
							return false;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public float ressemblance(Mask m) {
		float f = 0.0f;
		for (int i = 0; i<taille*2+1; i++) {
			for (int j = 0; j<taille*2+1; j++) {
				f += pixels[i][j].ressemblance(m.pixels[i][j]);
			}
		}
		return f/((float)taille*2+1*(float)taille*2+1);
	}
	
	public void print() {
		for (int i = 0; i<taille*2+1; i++) {
			for (int j = 0; j<taille*2+1; j++) {
				System.out.print("["+i+","+j+"]="+pixels[i][j].toString());
			}
		}
	}
}
