package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashSet;

public abstract class Zone implements Serializable {
	
	static final private long serialVersionUID = 1L;
	
	protected static final LockObject stringLock = new LockObject();
	
	protected String areaDesc;
	
	protected static final LockObject mobileLock = new LockObject();
	
	private LinkedHashSet<Mobile> mobiles = new LinkedHashSet<Mobile>();

	protected static final LockObject contentLock = new LockObject();

	private LinkedHashSet<Entity> contents = new LinkedHashSet<Entity>();
	
	public abstract int getSize();
	
	public abstract Location getLocation(Coordinates coors);
	
	public void setContents(LinkedHashSet<Entity> contents) {

		synchronized (contentLock) {

			this.contents = contents;

		}

	}

	public LinkedHashSet<Entity> getContents() {

		synchronized (contentLock) {

			return contents;

		}

	}

	public void addEntity(Entity entity) {

		synchronized (contentLock) {

			contents.add(entity);

		}

	}

	public void removeEntity(Entity entity) {

		synchronized (contentLock) {

			contents.remove(entity);

		}

	}

	public LinkedHashSet<Mobile> getMobiles() {

		synchronized (mobileLock) {

			return mobiles;

		}

	}
	
	public void addMobile(Mobile mobile) {

		synchronized (mobileLock) {

			mobiles.add(mobile);

		}

	}

	public void removeMobile(Mobile mobile) {

		synchronized (mobileLock) {

			mobiles.remove(mobile);

		}

	}
	
	public String getAreaDesc() {
		
		return areaDesc;
		
	}
	
	public void setAreaDesc(String areaDesc) {
		
		this.areaDesc = areaDesc;
		
	}
	
	public abstract String getMapString(Mobile mobile, int radius);
	
	public abstract Location getDestination(Mobile mobile, Direction moveDir);

}
