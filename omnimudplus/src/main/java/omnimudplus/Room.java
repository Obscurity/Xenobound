package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Room implements Serializable {

	private String briefDesc;
	
	private String verboseDesc;
	
	private Area area;

	private static final LockObject exitLock = new LockObject();
	
	private LinkedHashMap<Direction, Exit> exits = new LinkedHashMap<Direction, Exit>();
	
	private static final LockObject mobileLock = new LockObject();
	
	private LinkedHashSet<Mobile> mobiles = new LinkedHashSet<Mobile>();
	
	private Feature feature;
	
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
	
	public Room() {
		
		briefDesc = "An uninitialized room";
		
		verboseDesc = "This room has not been initialized.";
		
		coors = new Coordinates(0, 0);
		
		setTerrain(Terrain.UNINITIALIZED);
		
	}
	
	public Room(Area area, Coordinates coors) {
		
		briefDesc = "An uninitialized room";
		
		verboseDesc = "This room has not been initialized.";
		
		this.coors = coors;
		
		setTerrain(Terrain.UNINITIALIZED);
		
		this.area = area;
		
	}
	
	public Room(Building building, Coordinates coors) {
		
		briefDesc = "An uninitialized room";
		
		verboseDesc = "This room has not been initialized.";
		
		this.coors = coors;
		
		setTerrain(Terrain.UNINITIALIZED);
		
		if (building.getParentRoom() != null) {
		
			this.area = building.getParentRoom().getArea();
		
		}
		
	}
	
	public Room(String briefDesc, String verboseDesc, Terrain terrain, Area area) {
		
		this.briefDesc = briefDesc;
		
		this.verboseDesc = verboseDesc;
		
		this.terrain = terrain;
		
		this.area = area;
		
	}
	
	public Room(Area area, Room origin, Direction dir) {
		
		briefDesc = "An uninitialized room";
		
		verboseDesc = "This room has not been initialized.";
		
		Coordinates newCoors = new Coordinates(origin.getCoordinates(), dir.getOffset());
		
		coors = newCoors;
		
		setTerrain(Terrain.UNINITIALIZED);
		
		this.area = area;
		
	}
	
	public void setExit(Direction dir, Room room) {
		
		synchronized (exitLock) {
		
			if (exits.get(dir) != null) {
				exits.remove(dir);
			}
			
			if (room != null) {
				exits.put(dir, new Exit(room));
			}
			
		}
		
	}
	
	public Exit getExit(Direction dir) {
		
		synchronized (exitLock) {
		
			return exits.get(dir);
		
		}
		
	}
	
	public Room getDestination(Direction dir) {
		
		synchronized (exitLock) {
			
			return exits.get(dir).getDestination();
			
		}
		
	}
	
	public String getBriefDesc() {
		return briefDesc;
	}
	
	public String getVerboseDesc() {
		return verboseDesc;
	}
	
	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}
	
	public void setVerboseDesc(String verboseDesc) {
		this.verboseDesc = verboseDesc;
	}
	
	public boolean containsMobile() {
		
		return mobiles.size() > 0;
		
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
	
	public boolean containsMobile(Mobile mobile) {
		
		synchronized (mobileLock) {
		
			return mobiles.contains(mobile);
		
		}
		
	}
	
	public LinkedHashSet<Mobile> getMobiles() {
		
		synchronized (mobileLock) {
		
			return mobiles;
		
		}
		
	}
	
	public void setArea(Area area) {
		
		if (this.area != null) {
			
			this.area.removeRoom(this);
			
		}
		
		this.area = area;
		
		if (area != null) {
		
			area.addRoom(this);
		
		}
		
	}
	
	public Area getArea() {
		
		return area;
		
	}
	
	public String printRoom(Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		StringBuilder sumdesc = new StringBuilder();
		
		// Area area = room.getZone();
		
		sumdesc.append(terrain.getColor().getHighCode());
		sumdesc.append(briefDesc);
		sumdesc.append(" ");
		sumdesc.append(cn.getColorScheme().getForeground());
		sumdesc.append(coors);
		sumdesc.append("\n\n");
		sumdesc.append(verboseDesc);
		sumdesc.append(" ");
		sumdesc.append(cn.getColorScheme().getMobiles());
		
		for (Mobile other : mobiles) {
			
			ConnectNode oc = other.getConnectNode();
			
			if (other == mobile) {
				continue;
			}
			
			if (oc == null) {
				sumdesc.append(cn.getColorScheme().getMobiles());
				sumdesc.append(other.getShortDesc());
			} else {
				sumdesc.append(cn.getColorScheme().getPlayers());
				sumdesc.append(other.getName());
			}
			
			sumdesc.append(" is here.");
			
		}
		
		sumdesc.append("\n\n");
		
		if (contents != null) {

			if (contents.size() > 0) {
				
				for(Entity object : contents) {
					
					sumdesc.append(object.getRoomDesc());
					sumdesc.append(" \n");
					
				}

				sumdesc.append("\n\n");

			}

		}
		
		if (feature != null) {
			
			sumdesc.append(feature.getRoomDesc());
			sumdesc.append("\n\n");
			
		} else {
			
			if (area != null) {
			
				if (area instanceof BuildingArea) {
					
					if (area.getOrigin() == this) {
						
						BuildingArea ba = (BuildingArea)area;
						
						sumdesc.append("You can leave " + ba.getBuilding().getShortDesc() + " from here.");
						sumdesc.append("\n\n");
						
					}
					
				}
			
			}
			
		}
		
		sumdesc.append(cn.getColorScheme().getExits());
		sumdesc.append(getExitString(cn));
		sumdesc.append("\n");
		
		return sumdesc.toString();
		
	}
	
	public String getExitString(ConnectNode cn) {
		
		StringBuilder builder = new StringBuilder();
		
		int count = 0;
		
		if (exits.size() <= 0) {
			builder.append("You can see no obvious exits here.");
			return builder.toString();
			
		} else if (exits.size() >= 1) {
			
			if (exits.size() == 1) {
				builder.append("You can see an exit to the ");
			} else {
				builder.append("You can see exits to the ");
			}
			
			for (Direction dir : Direction.values()) {
				
				Exit test = exits.get(dir);
				
				if (test != null) {
					
					count++;
					
					builder.append(test.getDestination().getTerrain().getColor().getHighCode());
					builder.append(dir.getName());
					
					builder.append(cn.getColorScheme().getExits());
					
					if (count < exits.size() - 1) {
						
						builder.append(", ");
						
					} else if (count == exits.size() - 1) {
						
						builder.append(", and ");
						
					} else if (count == exits.size()) {
						
						builder.append(".");
						
					}
					
				}
				
			}
			
		}
		
		return builder.toString();
		
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		// System.out.println(building);
		this.feature = feature;
		feature.setParentRoom(this);
		feature.setArea(this.area);
	}
	
}
