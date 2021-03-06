package omnimudplus.Entities;

import java.util.LinkedHashMap;
import java.util.Set;

// Edible. The base template for food.

public class Edible extends TakableEntity {
	
	private static final long serialVersionUID = 1L;
	
	// 
	
	private LinkedHashMap<Material, Integer> components;
	
	public Edible() {
		
		super("ration", new String[] {"edible", "food", "undefined"},
				"an undefined ration",
				"An undefined ration lies here.",
				"This ration is undefined.",
				0, Material.UNDEFINED, Size.DIMINUTIVE);
		
	}
	
	public Edible(LinkedHashMap<Material, Integer> components) {
		
		super("ration", new String[] {"edible", "food", "undefined"},
				"an undefined ration",
				"An undefined ration lies here.",
				"This ration is undefined.",
				0, Material.UNDEFINED, Size.DIMINUTIVE);
		
	}
	
	public LinkedHashMap<Material, Integer> getComponents() {
		
		return components;
		
	}
	
	public String[] componentsToArray() {
		
		// TODO
		
		// This is awful. I'll need to fix it if I get a chance.
		
		String[] componentsList = new String[components.size()];
		
		Material[] materials = (Material[])components.keySet().toArray();
		
		for (int i = 0; i < components.size(); i++) {
			
			componentsList[i] = materials[i].getName();
			
		}
		
		return componentsList;
		
	}
	
}