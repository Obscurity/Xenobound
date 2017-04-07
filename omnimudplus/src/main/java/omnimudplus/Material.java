package omnimudplus;

public enum Material {
	
	// Mohs rating, Buildable, Casable, Toolable, Bludgeonable, Edgable, Conductive, Ornamentable,
	// Edible, Textile
	
	// Undefined.
	
	UNDEFINED("undefined", 1, true, true, true, true, true, true, true, false, false),
	
	// Organic.
	
	WOOD("wood", 3, true, true, true, true, false, false, true, false, false),
	FLESH("flesh", 1, false, false, false, false, false, false, false, false, false),
	BONE("bone", 1, false, false, true, true, true, false, true, false, false),
	
	// Organic fibers.
	
	COTTON("cotton", 1, false, false, false, false, false, false, false, false, true),
	FLAX("flax", 1, false, false, false, false, false, false, false, true, true),
	
	// Rock and other similar things.
	
	CONCRETE("concrete", 6, true, false, false, true, false, false, false, false, false),
	STONE("stone", 5, true, true, false, true, false, false, true, false, false),
	
	// Common metals.
	
	ALUMINIUM("aluminium", 3, true, true, true, false, false, true, true, false, false),
	BRASS("brass", 3, false, false, false, false, false, false, true, false, false),
	BRONZE("bronze", 3, false, true, true, true, true, false, true, false, false),
	COPPER("copper", 3, false, false, true, false, true, true, true, false, false),
	IRON("iron", 4.5, true, true, true, true, true, false, true, false, false),
	LEAD("lead", 1.5, false, false, false, false, false, false, true, false, false),
	STEEL("steel", 8, true, true, true, true, true, false, true, false, false),
	
	// Rare metals.
	
	GOLD("gold", 3, false, false, false, false, false, true, true, false, false),
	SILVER("silver", 3, false, false, false, false, false, true, true, false, false),
	TITANIUM("titanium", 6, true, true, true, true, false, false, true, false, false),
	PLATINUM("platinum", 4.5, false, false, false, false, false, true, true, false, false),
	TUNGSTEN("tungsten", 7.5, false, false, true, false, false, true, true, false, false),
	
	// Gems
	
	DIAMOND("diamond", 10, false, false, true, false, false, false, true, false, false);
	
	// The material's name.
	
	private String name;
	
	// Durability. We're trying to go by the Mohs scale, or closest approximation.
	
	private double durability;
	
	// Can this be used as a building material?
	
	private boolean buildable;
	
	// Can this be used as a handle, stock, casing, or armor?
	
	private boolean casable;
	
	// Can we make tool heads out of this?
	
	private boolean toolable;
	
	// Can we make blunt weapons out of this?
	
	private boolean bludgeonable;
	
	// Can we make sharp weapons out of this?
	
	private boolean edgable;
	
	// Can it be used for wiring?
	
	private boolean conductive;
	
	// Can it be used as an ornament?
	
	private boolean ornamentable;
	
	// Can it be eaten?
	
	private boolean edible;
	
	// Is it a textile?
	
	private boolean textile;
	
	private Material(String name, double durability, boolean buildable,
			boolean casable, boolean toolable, boolean bludgeonable,
			boolean edgable, boolean conductive, boolean ornamentable,
			boolean edible, boolean textile) {
		
		this.name = name;
		this.durability = durability;
		this.buildable = buildable;
		this.casable = casable;
		this.toolable = toolable;
		this.bludgeonable = bludgeonable;
		this.edgable = edgable;
		this.conductive = conductive;
		this.ornamentable = ornamentable;
		this.edible = edible;
		this.textile = textile;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDurability() {
		return durability;
	}
	public void setDurability(double durability) {
		this.durability = durability;
	}
	
	public boolean isBuildable() {
		
		return buildable;
		
	}
	
	public boolean isCasable() {
		
		return casable;
		
	}
	
	public boolean isToolable() {
		
		return toolable;
		
	}
	
	public boolean isBludgeonable() {
		
		return bludgeonable;
		
	}
	
	public boolean isEdgable() {
		
		return edgable;
		
	}
	
	public boolean isConductive() {
		
		return conductive;
		
	}
	
	public boolean isOrnamentable() {
		
		return ornamentable;
		
	}
	
	public boolean isEdible() {
		
		return edible;
		
	}
	
	public boolean isTextile() {
		
		return textile;
		
	}

}