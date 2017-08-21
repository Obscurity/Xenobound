package omnimudplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import omnimudplus.Geography.Vector;

public class Physics {
	
	public static final double GRAVITY = 9.81;
	public static final int SQRT1      = 1;
	public static final double SQRT2   = Math.sqrt(2);
	public static final double SQRT3   = Math.sqrt(3);
	
    public static final LockObject radialmaplock = new LockObject();
    
    public static final HashMap<Integer, ArrayList<Vector>> radialmap = new LinkedHashMap<Integer, ArrayList<Vector>>();
    
    public static final LockObject squarerootmaplock = new LockObject();
    
    public static final HashMap<Integer, Integer> squarerootmap = new LinkedHashMap<Integer, Integer>();
    
    public static final LockObject arctanmaplock = new LockObject();
    
    public static final HashMap<Integer, CompassAngle> arctanmap = new LinkedHashMap<Integer, CompassAngle>();
    
    public static void populateRadialMap() {
    	
    	for (int r = 1; r <= 30; r++) {
    		
    		int rsq = r*r + 2;
    		
    		ArrayList<Vector> offsets = new ArrayList<Vector>();
    		
    		for (int i = -r; i <= r; i++) {
    			
    			for (int j = r; j >= -r; j--) {
    			
    				for (int k = 0; k <= r; k++) {
    					
    					if (i*i + j*j + k*k > rsq) {
    						continue;
    					}
    					
    					offsets.add(new Vector(i, j, k));
    					
    					if (k > 0) {
    						offsets.add(new Vector(i, j, -k));
    					}
    					
    				}
    				
    			}
    				
    		}
    		
    		radialmap.put(r, offsets);
    		
    	}
    	
    }
    
    public static void populateSquareRootMap() {
    	
    	for (int i = 0; i <= 1010000; i++) {
    		
    		squarerootmap.put(i, (int)(Math.sqrt(i) + 0.5));
    		
    	}
    	
    }

}
