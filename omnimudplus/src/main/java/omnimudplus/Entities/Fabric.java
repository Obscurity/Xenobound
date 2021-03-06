package omnimudplus.Entities;

// Fabric. The standard crafting unit for textiles. '1 bolt', in game lore,
// is not remotely intended to be quantifiable. Don't try to get yardage or
// width. You will go fucking crazy.

public class Fabric extends TakableEntity {
	
	private static final long serialVersionUID = 1L;
	
	public Fabric() {
		
		super("fabric", new String[] {"bolt", "cloth", "textile", "undefined"},
				"a bolt of undefined fabric",
				"A bolt of undefined fabric lies here.",
				"This fabric is undefined.",
				0, Material.UNDEFINED, Size.SMALL);
		
	}
	
	public Fabric(Material material) {
		
		super("fabric", new String[] {"bolt", "cloth", "textile", material.getName()},
				"a bolt of " + material.getName() + " fabric",
				"A bolt of " + material.getName() + " fabric lies here.",
				"This is a bolt of " + material.getName() +
				" fabric, neatly rolled into a transportable cylinder. It has been"
				+ " sealed inside a thin, protective layer of nanorobotic symbiotes.",
				0, material, Size.SMALL);
		
	}
	
}
