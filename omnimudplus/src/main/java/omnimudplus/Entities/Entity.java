package omnimudplus.Entities;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import omnimudplus.Area;
import omnimudplus.Coordinates;
import omnimudplus.LockObject;
import omnimudplus.Material;
import omnimudplus.Pronouns;
import omnimudplus.Room;

public class Entity implements Serializable {

	protected final LockObject descLock = new LockObject();

	protected String name; /* The name of the entity. */

	protected String[] aliases; /* The aliases the entity can be referenced by. Cannot be left blank. */

	protected String shortDesc; /* A short description of the entity - 'a short sword', 'a tall man' */

	protected String roomDesc; /* As it appears in a room - 'A short sword lies here.', 'A tall man stands here.' */

	protected String longDesc; /* The description yielded upon closer examination. 'This short sword is sharp.', 'This man really is very tall.' */

	protected final LockObject propertyLock = new LockObject();

	protected int weight; /* Weight in grams. 0 is effectively weightless, or close to. */
	
	protected Pronouns pronouns = Pronouns.IT;
	
	protected Material material;
	
	static final private long serialVersionUID = 1L;
	
	protected Coordinates coors;
	
	protected Room room = null;
	
	protected Area area = null;

	public Entity() {

		name = "entity";
		
		aliases = new String[] {"object", "thing"};

		shortDesc = "an entity";

		roomDesc = "An uninitialized entity is here.";

		longDesc = "This entity is uninitialized, and this is probably an issue.";

		weight = 1;
		
		material = Material.UNDEFINED;

	}

	public Entity(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc, int weight, Material material) {

		this.name = name;

		this.aliases = aliases;

		this.shortDesc = shortDesc;

		this.roomDesc = roomDesc;

		this.longDesc = longDesc;

		this.weight = weight;
		
		this.material = material;

	}

	public String getName() {

		synchronized (descLock) {
			
			return name;

		}

	}

	public void setName(String name) {

		synchronized (descLock) {

			this.name = name;

		}

	}

	public String[] getAliases() {

		synchronized (descLock) {

			return aliases;

		}

	}

	public void setAliases(String[] aliases) {

		synchronized (descLock) {

			this.aliases = aliases;

		}

	}

	public String[] getReferences() {

		synchronized (descLock) { 

			String[] references = new String[aliases.length + 1];

			references[0] = this.name;

			for (int i = 1; i < references.length; i++) {

				references[i] = aliases[i - 1];

			}

			return references;
			
		}

	}

	public String getShortDesc() {

		synchronized (descLock) {

			return shortDesc;

		}

	}

	public void setShortDesc(String shortDesc) {

		synchronized (descLock) {

			this.shortDesc = shortDesc;

		}

	}

	public String getLongDesc() {

		synchronized (descLock) {

			return longDesc;

		}

	}

	public void setLongDesc(String longDesc) {

		synchronized (descLock) {

			this.longDesc = longDesc;

		}

	}

	public String getRoomDesc() {

		synchronized (descLock) {

			return roomDesc;

		}

	}

	public void setRoomDesc(String roomDesc) {

		synchronized (descLock) {

			this.roomDesc = roomDesc;

		}

	}

	public int getWeight() {

		synchronized (propertyLock) {

			return weight;

		}

	}

	public void setWeight(int weight) {

		synchronized (propertyLock) {

			this.weight = weight;

		}

	}
	
	public Coordinates getCoordinates() {
		
		return coors;
		
	}
	
	public void setCoordinates(Coordinates coors) {
		
		this.coors = coors;
		
	}
	
	public void setRoom(Room room) {
		
		if (this.room != null) {
			
			this.room.removeEntity(this);
			
		}
		
		this.room = room;
		
		if (this.room != null) {
			
			this.room.addEntity(this);
			
		}
		
	}
	
	public LockObject getPropertyLock() {
		
		synchronized (propertyLock) {
			
			return propertyLock;
			
		}
		
	}
	
	public Pronouns getPronouns() {
		
		return pronouns;
		
	}
	
	public Room getRoom() {
		
		return room;
		
	}
	
	public Area getArea() {
		
		return area;
		
	}
	
	public void setArea(Area area) {
		
		if (this.area != null) {
			
			this.area.removeEntity(this);
			
		}
		
		this.area = area;
		
		if (this.area != null) {
			
			this.area.addEntity(this);
			
		}
		
	}

	public String toString() {

		return "name: " + name + "\naliases: " + aliases + "\nshortDesc: " + shortDesc + "\nroomDesc: " + roomDesc + "\nlongDesc: " + longDesc;

	}

}