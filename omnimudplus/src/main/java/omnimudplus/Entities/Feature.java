package omnimudplus.Entities;

import omnimudplus.Material;
import omnimudplus.Room;

public class Feature extends Entity {
	
	static final private long serialVersionUID = 1L;
	
	protected Room parentRoom;

	public Room getParentRoom() {
		return parentRoom;
	}

	public void setParentRoom(Room parentRoom) {
		
		// Making sure we aren't putting a feature in more than one room.
		
		this.parentRoom = parentRoom;
		
		this.setArea(parentRoom.getArea());

	}

	public Feature() {

		name = "feature";
		
		aliases = new String[] {"object", "thing"};

		shortDesc = "a feature";

		roomDesc = "An uninitialized feature is here.";

		longDesc = "This feature is uninitialized, and this is probably an issue.";

		weight = 1;
		
		material = Material.UNDEFINED;

	}

	public Feature(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc, int weight, Material material) {

		this.name = name;

		this.aliases = aliases;

		this.shortDesc = shortDesc;

		this.roomDesc = roomDesc;

		this.longDesc = longDesc;

		this.weight = weight;
		
		this.material = material;

	}
	
}
