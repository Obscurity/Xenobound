package omnimudplus.Entities;
import java.io.*;
import java.util.LinkedHashSet;

import omnimudplus.LockObject;
import omnimudplus.Geography.Area;
import omnimudplus.Geography.Coordinates;
import omnimudplus.Geography.Room;

public abstract class Entity implements Serializable {

	private final LockObject descLock = new LockObject();

	private String name; /* The name of the entity. */

	private LinkedHashSet<String> aliases; /* The aliases the entity can be referenced by. Cannot be left blank. */

	private String shortDesc; /* A short description of the entity - 'a short sword', 'a tall man' */

	private String roomDesc; /* As it appears in a room - 'A short sword lies here.', 'A tall man stands here.' */

	private String longDesc; /* The description yielded upon closer examination. 'This short sword is sharp.', 'This man really is very tall.' */

	private final LockObject propertyLock = new LockObject();

	private int weight; /* Weight in grams. 0 is effectively weightless, or close to. */
	
	private Pronouns pronouns = Pronouns.IT;
	
	private Material material;
	
	private Size size;
	
	private int durability;
	
	private int maxDurability;
	
	static final private long serialVersionUID = 1L;
	
	private Room<?> room = null;
	
	private Area<?> area = null;

	public Entity() {

		name = "entity";
		
		aliases = new LinkedHashSet<String>();

		aliases.add("object");
		
		aliases.add("thing");
		
		shortDesc = "an entity";

		roomDesc = "An uninitialized entity is here.";

		longDesc = "This entity is uninitialized, and this is probably an issue.";

		weight = 1;
		
		setMaterial(Material.UNDEFINED);
		
		setSize(Size.FINE);
		
		initDurability();

	}

	public Entity(String name, String[] aliases, String shortDesc, String roomDesc,
			String longDesc, int weight, Material material, Pronouns pronouns, Size size) {

		this.name = name;

		this.aliases = new LinkedHashSet<String>();
		
		for (String s : aliases) {
			
			this.aliases.add(s);
			
		}

		this.shortDesc = shortDesc;

		this.roomDesc = roomDesc;

		this.longDesc = longDesc;

		this.weight = weight;
		
		setMaterial(material);
		
		this.pronouns = pronouns;

		setSize(size);
		
		initDurability();
		
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

	public LinkedHashSet<String> getAliases() {

		synchronized (descLock) {

			return aliases;

		}

	}
	
	public void setAliases(String[] aliases) {
		
		synchronized (descLock) {
			
			this.aliases = new LinkedHashSet<String>();
			
			for (String s : aliases) {
				
				this.aliases.add(s);
				
			}
			
		}
		
	}

	public void setAliases(LinkedHashSet<String> aliases) {

		synchronized (descLock) {
			
			this.aliases = aliases;

		}

	}

	public LinkedHashSet<String> getReferences() {

		synchronized (descLock) { 

			@SuppressWarnings("unchecked")
			LinkedHashSet<String> temp = new LinkedHashSet<String>();
			
			temp.add(name);
			
			temp.addAll(aliases);
			
			return temp;
			
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
	
	public void setRoom(Room<?> room) {
		
		synchronized (propertyLock) {
		
			if (this.room != null) {
				
				this.room.removeEntity(this);
				
			}
			
			this.room = room;
			
			if (this.room != null) {
				
				this.room.addEntity(this);
				
			}
		
		}
		
	}
	
	public LockObject getPropertyLock() {
		
		synchronized (propertyLock) {
			
			return propertyLock;
			
		}
		
	}
	
	public Pronouns getPronouns() {
		
		synchronized (propertyLock) {
		
			return pronouns;
		
		}
		
	}
	
	public Room<?> getRoom() {
		
		synchronized (propertyLock) {
		
			return room;
		
		}
		
	}
	
	public Area<?> getArea() {
		
		synchronized (propertyLock) {
		
			if (room == null) { return null; }
			
			return room.getArea();
		
		}
		
	}

	public String toString() {

		synchronized (propertyLock) {
		
			return "name: " + name + "\naliases: " + aliases + "\nshortDesc: "
			+ shortDesc + "\nroomDesc: " + roomDesc + "\nlongDesc: " + longDesc;

		}
		
	}

	public Material getMaterial() {
		
		synchronized (propertyLock) {
		
			return material;
		
		}
		
	}

	public void setMaterial(Material material) {
		
		synchronized (propertyLock) {
		
			this.material = material;
		
		}
		
	}

	public Size getSize() {
		
		synchronized (propertyLock) {
		
			return size;
		
		}
		
	}

	public void setSize(Size size) {
		
		synchronized (propertyLock) {
		
			this.size = size;
		
		}
		
	}
	
	protected void initDurability() {
		
		synchronized (propertyLock) {
		
			int differential = getSize().getDifferential();
			
			setWeight(differential);
			this.durability = (int)(differential*getMaterial().getDurability());
			this.maxDurability = (int)(differential*getMaterial().getDurability());
		
		}
		
	}
	
	protected void setDurability(int durability) {
		this.durability = durability;
	}
	
	public void addDurability(int offset) {
		
		synchronized (propertyLock) {
		
			durability+=offset;
			if (durability > maxDurability) {
				durability = maxDurability;
			}
		
		}
		
	}
	
	public void removeDurability(int offset) {
		
		synchronized (propertyLock) {
		
			durability-=offset;
			if (durability < 0) {
				durability = 0;
			}
		
		}
		
	}
	
	public boolean isDamaged() {
		
		synchronized (propertyLock) {
		
			if (durability < maxDurability) {
				return true;
			}
			return false;
		
		}
		
	}
	
	public int getDurability() {
		return durability;
	}
	
	public int getMaxDurability() {
		return maxDurability;
	}
	
	protected void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}
	
	public boolean isBroken() {
	
		synchronized (propertyLock) {
			
			if (durability == 0) {
				return true;
			}
			return false;
		
		}
		
	}

}