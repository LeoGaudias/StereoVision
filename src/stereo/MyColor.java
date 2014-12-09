package stereo;

import java.awt.Color;

public class MyColor {
	public float h, s, b;
	public int red, green, blue;

	public MyColor(float h, float s, float b) {
		this.h = h;
		this.s = s;
		this.b = b;
	}
	
	public MyColor(Color c) {
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		h = hsb[0];
		s = hsb[1];
		b = hsb[2];
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
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
			if (c.h == h && c.s == s && c.b == b && c.red == red && c.green == green && c.blue == blue) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public float ressemblance(MyColor c) {
//		return (5*c.h/h + 3*c.s/s + 2*c.b/b)/10;
		float somme = 0.0f;
		float divide = 0.0f;
		if (green>0) {
			somme += 5*c.green/green;
			divide += 5;
		}
		if (blue>0) {
			somme += 3*c.blue/blue;
			divide += 3;
		}
		if (red>0) {
			somme += 2*c.red/red;
			divide += 2;
		}
		if (divide>0) {
			return somme/divide;
		} else {
			return 0;
		}

//		return (5*c.green/green + 3*c.blue/blue + 2*c.red/red)/10;
//		return c.h*h;
	}
	
}
