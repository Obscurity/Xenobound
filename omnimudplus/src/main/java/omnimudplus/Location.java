package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public abstract class Location implements Serializable {
	
	static final private long serialVersionUID = 29L;
	
	protected Coordinates coors;
	
	protected final LockObject terrainLock = new LockObject();
	
	protected Terrain terrain;
	
	protected final LockObject contentLock = new LockObject();
	
	protected LinkedList<Entity> contents = new LinkedList<Entity>();
	
	public Coordinates getCoordinates() {
		
		return coors;
		
	}
	
	public void setContents(LinkedList<Entity> contents) {

		synchronized (contentLock) {

			this.contents = contents;

		}

	}

	public LinkedList<Entity> getContents() {

		synchronized (contentLock) {

			return contents;

		}

	}
	
	public abstract LinkedHashSet<Mobile> getMobiles();

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
	
	public void setTerrain(Terrain terrain) {
		
		this.terrain = terrain;
		
	}
	
	public Terrain getTerrain() {
		
		return terrain;
		
	}
	
	public boolean containsEntity(Entity entity) {
		
		if (contents.contains(entity)) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public abstract boolean containsMobile();
	
	public abstract Zone getZone();
	
	public abstract String printRoom(Mobile mobile);

}
