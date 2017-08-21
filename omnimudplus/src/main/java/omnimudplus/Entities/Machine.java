package omnimudplus.Entities;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public abstract class Machine<T extends TakableEntity> extends IndoorFeature implements IHoldsTakables<T>, IProductive {
	
	private String[] types;
	
	private Container<T> container;
	
	private static final long serialVersionUID = 1L;
	
	public Machine() {
		
		super("machine", new String[] {"object", "thing"}, "a machine",
				"An uninitialized machine is here.",
				"This machine is uninitialized, and this is probably an issue.",
				1, Material.UNDEFINED);
		
		types = new String[] {"any"};
		
		container = new Container<T>();
		
	}
	
	public int getCapacity() {
		return container.getCapacity();
	}
	
	public void setCapacity(int capacity) {
		container.setCapacity(capacity);
	}
	
	public int getMaxWeight() {
		return container.getMaxWeight();
	}
	
	public void setMaxWeight(int maxWeight) {
		container.setMaxWeight(maxWeight);
	}
	
	public int getWeightCarried() {
		return container.getWeightCarried();
	}
	
	public void setWeightCarried() {
		container.setWeightCarried();
	}
	
	public String[] getTypes() {
		return types;
	}
	
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	public Iterator<T> getContents() {
		
		return container.getContents();
		
	}
	
	public boolean hasContainer(Container container) {
		
		return this.container == container;
		
	}
	
	public boolean containsEntity(TakableEntity entity) {
		
		return container.containsEntity(entity);
		
	}
	
	public void addEntity(T entity) {
		
		container.addEntity(entity);
		
	}
	
	public void removeEntity(T entity) {
		
		container.removeEntity(entity);
		
	}
	
	public String toString() {

		String str = super.toString();

		str = str + "\ncapacity: " + container.getCapacity() + "\ncontents: ";

		Iterator<T> iterator = getContents();
		
		while(iterator.hasNext()) {

			str = str + iterator.next().getShortDesc() + ", ";

		}

		return str;

	}

}
