package omnimudplus;

import java.io.Serializable;

public class Coordinates implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int z;
	
	public Coordinates(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.z = 0;
		
	}
	
	public Coordinates(int x, int y, int z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	public Coordinates(Coordinates coors, Vector offset) {
		
		this.x = coors.getX() + offset.getXoffset();
		this.y = coors.getY() + offset.getYoffset();
		this.z = coors.getZ() + offset.getZoffset();
		
	}
	
	public int getX() {
		
		return x;
		
	}
	
	public int getY() {
		
		return y;
		
	}
	
	public int getZ() {
		
		return z;
		
	}

	public boolean equals(Coordinates coors) {
		
		return this.x == coors.getX() && this.y == coors.getY() && this.z == coors.getZ();
		
	}
	
	public String toString2D() {
		
		return "(" + x + ", " + y + ")";
		
	}
	
	public String toString() {
		
		//if (z != 0) {
		
			return "(" + x + ", " + y + ", " + z + ")";
			
		//} else {
			
			//return "(" + x + ", " + y + ")";
			
		//}
		
	}

}
