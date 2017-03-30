package omnimudplus;

import java.io.Serializable;

public class Vector implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int Xoffset;
	private int Yoffset;
	private int Zoffset;
	
	public Vector(int Xoffset, int Yoffset) {
		
		this.Xoffset = Xoffset;
		this.Yoffset = Yoffset;
		this.Zoffset = 0;
		
	}
	
	public Vector(int Xoffset, int Yoffset, int Zoffset) {
		
		this.Xoffset = Xoffset;
		this.Yoffset = Yoffset;
		this.Zoffset = Zoffset;
		
	}
	
	public Vector(Coordinates coors1, Coordinates coors2) {
		
		this.Xoffset = coors2.getX() - coors1.getX();
		this.Yoffset = coors2.getY() - coors1.getY();
		this.Zoffset = coors2.getZ() - coors1.getZ();
		
	}
	
	public int getXoffset() {
		
		return Xoffset;
		
	}
	
	public int getYoffset() {
		
		return Yoffset;
		
	}
	
	public int getZoffset() {
		
		return Zoffset;
		
	}
	
	public int getSquaredDistance() {
		
		return Xoffset*Xoffset + Yoffset*Yoffset + Zoffset*Zoffset;
		
	}
	
	public double getDistance() {
		
		int test = Xoffset*Xoffset + Yoffset*Yoffset + Zoffset*Zoffset;
		
		/* synchronized (Omnimud.squarerootmaplock) {
			
			Integer test2 = Omnimud.squarerootmap.get(test);
			
			if (test2 != null) {
				
				return test2;
				
			} else {
				
				Integer sqrt = (int)(Math.sqrt(test));
				
				Omnimud.squarerootmap.put(test, sqrt);
				
				return sqrt;
				
			}
			
		} */
		
		return Math.sqrt(test);
		
	}
	
	public double getAbsoluteOffset() {
		
		int absOffset = 0;
		
		if (Xoffset != 0) {
			
			absOffset += 1;
			
		}
		
		if (Yoffset != 0) {
			
			absOffset += 1;
			
		}
		
		if (Zoffset != 0) {
			
			absOffset += 1;
			
		}
		
		if (absOffset == 1) {
			
			return Physics.SQRT1;
			
		} else if (absOffset == 2) {
			
			return Physics.SQRT2;
			
		} else if (absOffset == 3) {
		
			return Physics.SQRT3;
			
		}
		
		return Physics.SQRT1;
		
	}
	
	public double getXSlope() {
		
		// How much the X changes over a given distance.
		
		double x = Xoffset;
		double y = Yoffset;
		
		if (y == 0) {
			
			if (x == 0) {
				
				return 0;
				
			} else {
			
				if (x > 0) {
					
					return 1;
					
				} else {
					
					return -1;
					
				}
				
			}
			
		}
		
		return x/y;
		
	}
	
	public double getYSlope() {
		
		// How much the Y changes over a given distance.
		
		double x = Xoffset;
		double y = Yoffset;
		
		if (x == 0) {
			
			if (y == 0) {
				
				return 0;
				
			} else {
			
				if (y > 0) {
					
					return 1;
					
				} else {
					
					return -1;
					
				}
				
			}
			
		}
		
		return y/x;
		
	}
	
	public double getZSlope() {
		
		// How much the Z changes over a given distance.
		
		double xyDistance = Math.sqrt(Xoffset*Xoffset + Yoffset*Yoffset);
		
		double z = Zoffset;
		
		if (xyDistance == 0) {
			
			if (z == 0) {
				
				return 0;
				
			} else {
				
				if (z > 0) {
				
					return 1;
					
				} else {
					
					return -1;
					
				}
				
			}
			
		}
		
		return z/xyDistance;
		
	}
	
	public boolean equals(Vector offset) {
		
		return this.Xoffset == offset.getXoffset() && this.Yoffset == offset.Yoffset && this.Zoffset == offset.Zoffset;
		
	}
	
	public String toString() {
		
		return "(" + Xoffset + ", " + Yoffset + ", " + Zoffset + ")";
		
	}

}
