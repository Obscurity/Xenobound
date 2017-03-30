package omnimudplus;
import java.util.*;

public class Container extends Entity {
	
	protected final LockObject capacityLock = new LockObject();

	private int capacity; // How many items this can hold, at maximum.

	private int weightCarried; // How much weight it's holding now, calculated from objects carried.

	private int maxWeight; // How much weight it can hold, in grams.
	
	protected final LockObject typeLock = new LockObject();

	private String[] types; // Types of items it can accept. If "any" is included, it'll hold anything.
	
	protected final LockObject contentLock = new LockObject();

	private LinkedHashSet<Entity> contents = new LinkedHashSet<Entity>(); // The items it's holding.
	
	static final private long serialVersionUID = 22L;

	public Container() {

		super("container", new String[] {"object, thing"}, "a container", "An uninitialized container is here.", "This " +
				"container is uninitialized.", 'u', 1, Material.UNDEFINED);

		capacity = 5;

		maxWeight = 1600;

		types = new String[1];

		types[0] = "any";

	}

	public Container(String name, String[] aliases, String shortDesc, String roomDesc, String longDesc, char token, int weight, 
			Material material, int capacity, int maxWeight, String[] types, LinkedHashSet<Entity> contents) {

		super(name, aliases, shortDesc, roomDesc, longDesc, token, weight, material);

		this.capacity = capacity;

		this.maxWeight = maxWeight;

		setTypes(types);

		setContents(contents);

	}

	public void setContents(LinkedHashSet<Entity> contents) {
		
		synchronized (contentLock) {

			this.contents = contents;
			
			setWeightCarried();
		
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
			
			setWeightCarried();
			
		}

	}

	public void removeEntity(Entity entity) {
		
		synchronized (contentLock) {

			contents.remove(entity);
			
			setWeightCarried();
			
		}

	}

	public void setCapacity(int capacity) {
		
		synchronized (contentLock) {

			this.capacity = capacity;
		
		}

	}

	public int getCapacity() {
		
		synchronized (capacityLock) {

			return capacity;
		
		}

	}
	
	public void setWeight(Integer weight) {

		synchronized (weightLock) {

			this.weight = weight;
			
		}

	}
	
	public int getWeight() {

		synchronized (weightLock) {

			return weight + weightCarried;

		}

	}
	
	public void setWeightCarried() {
		
		synchronized (weightLock) {
		
			this.weightCarried = 0;
			
			for (Entity object : contents) {
			
				this.weightCarried += object.getWeight();
			
			}
		
		}
		
	}
	
	public int getWeightCarried() {
		
		synchronized (weightLock) {
		
			return weightCarried;
		
		}
		
	}
	
	public void setMaxWeight(int maxWeight) {
		
		synchronized (weightLock) {
			
			this.maxWeight = maxWeight;
			
		}
		
	}
	
	public int getMaxWeight() {
		
		synchronized (weightLock) {
			
			return maxWeight;
			
		}
		
	}

	public void setTypes(String[] types) {

		synchronized (typeLock) {

			if (types != null) {

				this.types = types;

			} else {

				this.types = new String[1];

				this.types[0] = "any";

			}

		}

	}

	public String[] getTypes() {

		synchronized (typeLock) {

			return types;

		}

	}

	public String toString() {

		String str = super.toString();

		str = str + "\ncapacity: " + capacity + "\ncontents: ";

		for(Entity object : contents) {

			str = str + object.getShortDesc() + ", ";

		}

		return str;

	}

}