package omnimudplus;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Subroom extends Location {

	private String briefDesc;
	
	private String verboseDesc;
	
	private Room parentRoom;
	
	private static final LockObject exitLock = new LockObject();
	
	private static final LockObject mobileLock = new LockObject();
	
	private LinkedHashSet<Mobile> mobiles = new LinkedHashSet<Mobile>();
	
	private boolean open = false;
	
	static final private long serialVersionUID = 29L;
	
	public boolean isOpen() {
		
		return open;
		
	}
	
	public boolean containsMobile() {
		
		synchronized (mobileLock) {
			
			return mobiles.size() > 0;
			
		}
		
	}
	
	public boolean containsMobile(Mobile mobile) {
		
		synchronized (mobileLock) {
		
			return mobiles.contains(mobile);
		
		}
		
	}
	
	public Room getParent() {
		
		return parentRoom;
		
	}
	
	public Area getZone() {
		
		return parentRoom.getZone();
		
	}
	
	public LinkedHashSet<Mobile> getMobiles() {
		
		synchronized (mobileLock) {
		
			return mobiles;
		
		}
		
	}
	
	public String printRoom(Mobile mobile) {
		
		return "Null";
		
	}
	
}
