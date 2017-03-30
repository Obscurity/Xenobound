package omnimudplus;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Room extends Location {
	
	private String briefDesc;
	
	private String verboseDesc;
	
	private Area area;

	private static final LockObject exitLock = new LockObject();
	
	private LinkedHashMap<Direction, Exit> exits = new LinkedHashMap<Direction, Exit>();
	
	private static final LockObject mobileLock = new LockObject();
	
	private LinkedHashSet<Mobile> mobiles = new LinkedHashSet<Mobile>();
	
	static final private long serialVersionUID = 23L;
	
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
	
	public void setExit(Direction dir, Location location) {
		
		synchronized (exitLock) {
		
			if (exits.get(dir) != null) {
				exits.remove(dir);
			}
			
			if (location != null) {
				exits.put(dir, new Exit(location));
			}
			
		}
		
	}
	
	public Exit getExit(Direction dir) {
		
		synchronized (exitLock) {
		
			return exits.get(dir);
		
		}
		
	}
	
	public Location getDestination(Direction dir) {
		
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
	
	public Area getZone() {
		
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
	
}
