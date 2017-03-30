package omnimudplus;

public enum Terrain {
	
	UNINITIALIZED("uninitialized", "An uninitialized place", Color.MAGENTA, 1),
	GRASSLAND("grassland", "In the grassland", Color.GREEN, 1),
	DESERT("desert", "In the desert", Color.BOLDYELLOW, 1),
	PLAIN("plain", "In the plain", Color.ORANGEWHITE1, 1),
	UNDERGROUND("underground", "Underground", Color.ROSYBROWN, 1),
	MOUNTAINS("mountains", "In the mountains", Color.PURPLE, 3),
	HILLS("hills", "In the hills", Color.BOLDGREEN, 2)
	;
	
	private String name;
	private String shortdesc;
	private Color color;
	private int cost;
	
	private Terrain(String name, String shortdesc, Color color, int cost) {
		
		this.name = name;
		this.shortdesc = shortdesc;
		this.color = color;
		this.cost = cost;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public String getShortDesc() {
		
		return shortdesc;
		
	}
	
	public Color getColor() {
		
		return color;
		
	}
	
	public int getCost() {
		
		return cost;
		
	}

}
