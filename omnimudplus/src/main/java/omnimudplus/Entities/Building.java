package omnimudplus.Entities;

import java.util.Iterator;

import omnimudplus.ConnectNode;
import omnimudplus.Colors.Color;
import omnimudplus.Geography.BuildingArea;
import omnimudplus.Geography.BuildingRoom;
import omnimudplus.Geography.Coordinates;
import omnimudplus.Geography.Direction;
import omnimudplus.Geography.Exit;
import omnimudplus.Geography.Room;
import omnimudplus.Geography.Vector;

public abstract class Building extends OutdoorFeature {
	
	// Buildings are features that can be entered.
	
	/* Different buildings have different rules with respect to whether
	 * they can be built up or down. These variables prescribe these limits.
	 */
	
	
	
	static final private long serialVersionUID = 1L;
	
	private BuildingArea buildingArea;

	public void setOrigin(BuildingRoom origin) {
		buildingArea.setOrigin(origin);
	}
	
	public Building() {
		
		super("building", new String[] {"object", "thing"}, "a building",
				"An uninitialized building stands here.",
				"This building is not very well initialized, is it?",
				0, Material.UNDEFINED);
		
		buildingArea = new BuildingArea(this);
		
		newRoom();
		
	}
	
	public Building(Room room) {
		
		super("building", new String[] {"object", "thing"}, "a building",
				"An uninitialized building stands here.",
				"This building is not very well initialized, is it?",
				0, Material.UNDEFINED);
		
		buildingArea = new BuildingArea(this);
		
		this.setParentRoom(room);
		
		newRoom();
		
	}
	
	public void newRoom() {
		
		buildingArea.newRoom();
		
	}
	
	public void newRoom(BuildingRoom start, Direction dir) {
		
		buildingArea.newRoom(start, dir);
		
	}
	
	public int getRoomNumber() {
		
		return buildingArea.getRoomNumber();
		
	}
	
	public void addRoom(BuildingRoom room) {
		
		buildingArea.addRoom(room);
		
	}
	
	public void removeRoom(BuildingRoom room) {
		
		buildingArea.removeRoom(room);
		
	}
	
	public Iterator<BuildingRoom> getRooms() {
		
		return buildingArea.getRooms();
		
	}
	
	public BuildingRoom getOrigin() {
		
		return (BuildingRoom)buildingArea.getOrigin();
		
	}
	
	public BuildingRoom getRoom(Coordinates coors) {
		
		return (BuildingRoom)buildingArea.getRoom(coors);
		
	}
	
	public String getMapString(Shell shell, int radius) {
		
		return buildingArea.getMapString(shell, radius);
		
	}
	
	public Room<?> getDestination(Mobile mobile, Direction moveDir) {
		
		Room<?> start = mobile.getRoom();
		
		Exit test = start.getExit(moveDir);
		
		return test.getDestination();
		
	}

}
