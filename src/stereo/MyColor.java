package stereo;

public class MyColor {
	public float h, s, b;

	public MyColor(float h, float s, float b) {
		this.h = h;
		this.s = s;
		this.b = b;
	}
	
	public MyColor(float[] hsb) {
		if (hsb.length == 3) {
			h = hsb[0];
			s = hsb[1];
			b = hsb[2];
		}
	}
}
