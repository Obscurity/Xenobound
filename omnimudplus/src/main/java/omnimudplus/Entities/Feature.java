package omnimudplus.Entities;

import omnimudplus.Geography.Room;

public abstract class Feature extends Entity {
	
	static final private long serialVersionUID = 1L;
	
	protected Room<?> parentRoom;

	public Room<?> getParentRoom() {
		return parentRoom;
	}

	public void setParentRoom(Room<?> parentRoom) {
		
		// Making sure we aren't putting a feature in more than one room.
		
		this.parentRoom = parentRoom;

	}
	
	public Feature() {
		
		super("feature", new String[] {"object", "thing"}, "a feature",
				"An uninitialized feature is here.",
				"This feature is uninitialized, and this is probably an issue.",
				1, Material.UNDEFINED, Pronouns.IT, Size.LARGE);

	}
	
	public Feature(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc,
			int weight, Material material) {

		super(name, aliases, shortDesc, roomDesc, longDesc, weight, material, Pronouns.IT, Size.HUGE);

	}

	public Feature(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc,
			int weight, Material material, Size size) {

		super(name, aliases, shortDesc, roomDesc, longDesc, weight, material, Pronouns.IT, size);

	}
	
}
