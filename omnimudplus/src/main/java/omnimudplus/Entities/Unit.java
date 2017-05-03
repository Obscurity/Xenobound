package omnimudplus.Entities;

import omnimudplus.Material;

// A unit is a versatile, standard crafting unit. '1 unit', in game lore,
// will never, ever be quantified beyond the point of 'it's enough to do
// this.'

public class Unit extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	public Unit() {
		
		super("unit", new String[] {"thing", "object"},
				"an undefined unit",
				"An undefined unit lies here.",
				"This unit is undefined.",
				0, Material.UNDEFINED);
		
	}
	
	public Unit(Material material) {
		
		super("unit", new String[] {"thing", "object", material.getName()},
				"a unit of " + material.getName(),
				"A unit of " + material.getName() + " lies here.",
				"This is a unit of " + material.getName() +
				". It has been neatly compartmentalized inside"
				+ " of a thin, protective shell of dormant"
				+ " nanorobotic symbiotes.",
				0, material);
		
	}
	
	public Material getMaterial() {
		
		return material;
		
	}
	
}
