package omnimudplus;
import java.io.*;

public class Entity implements Serializable {

	protected final LockObject nameLock = new LockObject();

	protected String name; /* The name of the entity. */
	
	protected final LockObject aliasLock = new LockObject();

	protected String[] aliases; /* The aliases the entity can be referenced by. Cannot be left blank. */
	
	protected final LockObject shortDescLock = new LockObject();

	protected String shortDesc; /* A short description of the entity - 'a short sword', 'a tall man' */
	
	protected final LockObject roomDescLock = new LockObject();

	protected String roomDesc; /* As it appears in a room - 'A short sword lies here.', 'A tall man stands here.' */
	
	protected final LockObject longDescLock = new LockObject();

	protected String longDesc; /* The description yielded upon closer examination. 'This short sword is sharp.', 'This man really is very tall.' */
	
	protected final LockObject tokenLock = new LockObject();

	protected char token; /* The token the item displays as, if seen on a map interface. 't', '@' */

	protected final LockObject weightLock = new LockObject();

	protected int weight; /* Weight in grams. 0 is effectively weightless, or close to. */

	protected final LockObject roomLock = new LockObject();
	
	protected Pronouns pronouns = Pronouns.IT;
	
	protected Material material;
	
	static final private long serialVersionUID = 1L;
	
	// Measured in meters per second (m/s). Physics runnables use them to determine position.
	
	/* These are used for temporary tracking, due to vertical distance being 
	 * measured in 3-meter increments. It should never be higher than 3 or
	 * lower than 0 - otherwise, we roll over into a new Z-level or resolve
	 * falling damage. Likewise, we can trace overflow with X and Y. */
	
	protected Coordinates coors;
	
	protected final LockObject pronounsLock = new LockObject();
	
	protected Location location = null;
	
	protected Zone zone = null;

	public Entity() {

		name = "entity";
		
		aliases = new String[] {"object", "thing"};

		shortDesc = "an entity";

		roomDesc = "An uninitialized entity is here.";

		longDesc = "This entity is uninitialized, and this is probably an issue.";

		token = '?';

		weight = 1;
		
		material = Material.UNDEFINED;

	}

	public Entity(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc, char token, int weight, Material material) {

		this.name = name;

		this.aliases = aliases;

		this.shortDesc = shortDesc;

		this.roomDesc = roomDesc;

		this.longDesc = longDesc;

		this.token = new Character(token);

		this.weight = weight;
		
		this.material = material;

	}

	public String getName() {

		synchronized (nameLock) {
			
			return name;

		}

	}

	public void setName(String name) {

		synchronized (nameLock) {

			this.name = name;

		}

	}

	public String[] getAliases() {

		synchronized (aliasLock) {

			return aliases;

		}

	}

	public void setAliases(String[] aliases) {

		synchronized (aliasLock) {

			this.aliases = aliases;

		}

	}

	public String[] getReferences() {

		synchronized (nameLock) { 

			synchronized (aliasLock) {

				String[] references = new String[aliases.length + 1];

				references[0] = this.name;

				for (int i = 1; i < references.length; i++) {

					references[i] = aliases[i - 1];

				}

				return references;

			}

		}

	}

	public String getShortDesc() {

		synchronized (shortDescLock) {

			return shortDesc;

		}

	}

	public void setShortDesc(String shortDesc) {

		synchronized (shortDescLock) {

			this.shortDesc = shortDesc;

		}

	}

	public String getLongDesc() {

		synchronized (longDescLock) {

			return longDesc;

		}

	}

	public void setLongDesc(String longDesc) {

		synchronized (longDescLock) {

			this.longDesc = longDesc;

		}

	}

	public String getRoomDesc() {

		synchronized (roomDescLock) {

			return roomDesc;

		}

	}

	public void setRoomDesc(String roomDesc) {

		synchronized (roomDescLock) {

			this.roomDesc = roomDesc;

		}

	}

	public Character getToken() {

		synchronized (tokenLock) {

			return token;

		}

	}

	public void setToken(Character token) {

		synchronized (tokenLock) {

			this.token = token;

		}

	}

	public int getWeight() {

		synchronized (weightLock) {

			return weight;

		}

	}

	public void setWeight(int weight) {

		synchronized (weightLock) {

			this.weight = weight;

		}

	}
	
	public Coordinates getCoordinates() {
		
		return coors;
		
	}
	
	public void setCoordinates(Coordinates coors) {
		
		this.coors = coors;
		
	}
	
	public void setLocation(Location location) {
		
		if (this.location != null) {
			
			this.location.removeEntity(this);
			
		}
		
		this.location = location;
		
		if (this.location != null) {
			
			this.location.addEntity(this);
			
		}
		
	}
	
	public LockObject getPronounsLock() {
		
		synchronized (pronounsLock) {
			
			return pronounsLock;
			
		}
		
	}
	
	public Pronouns getPronouns() {
		
		return pronouns;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public Zone getZone() {
		
		return zone;
		
	}
	
	public void setZone(Zone zone) {
		
		if (this.zone != null) {
			
			this.zone.removeEntity(this);
			
		}
		
		this.zone = zone;
		
		if (this.zone != null) {
			
			this.zone.addEntity(this);
			
		}
		
	}

	public String toString() {

		return "name: " + name + "\naliases: " + aliases + "\nshortDesc: " + shortDesc + "\nroomDesc: " + roomDesc + "\nlongDesc: " + longDesc + "\ntoken: " + token;

	}

}