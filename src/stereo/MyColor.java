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
	
	public String toString() {
		return "("+h+","+s+","+b+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof MyColor) {
			MyColor c = (MyColor)obj;
			if (c.h == h && c.s == s && c.b == b) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public float ressemblance(MyColor c) {
		return (5*c.h/h + 3*c.s/s + 2*c.b/b)/10;
	}
	
}
