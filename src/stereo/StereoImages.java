package stereo;

public class StereoImages {

	public myImage imgLeft;
	public myImage imgRight;
	
	public StereoImages(myImage left, myImage right) {
		imgLeft = left;
		imgRight = right;
	}
	
	//Retourne pour chaque pt de repere dans l'image gauche, son correspondant dans l'image droite
	public int[] correspondance() {
		return null;
	}
}
