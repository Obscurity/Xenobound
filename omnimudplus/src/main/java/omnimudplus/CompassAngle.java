package omnimudplus;

public enum CompassAngle {
	
	// X-axis right
	
	EAST("east", Direction.EAST),
	
	// Upper right quadrant
	
	EASTBYNORTH("east-by-north", Direction.EAST),
	EASTNORTHEAST("east-northeast", Direction.EAST),
	NORTHEASTBYEAST("northeast-by-east", Direction.NORTHEAST),
	NORTHEAST("northeast", Direction.NORTHEAST),
	NORTHEASTBYNORTH("northeast-by-north", Direction.NORTHEAST),
	NORTHNORTHEAST("north-northeast", Direction.NORTH),
	NORTHBYEAST("north-by-east", Direction.NORTH),
	
	// Y-axis up
	
	NORTH("north", Direction.NORTH),
	
	// Upper left quadrant
	
	NORTHBYWEST("north-by-west", Direction.NORTH),
	NORTHNORTHWEST("north-northwest", Direction.NORTH),
	NORTHWESTBYNORTH("northwest-by-north", Direction.NORTHWEST),
	NORTHWEST("northwest", Direction.NORTHWEST),
	NORTHWESTBYSOUTH("northwest-by-south", Direction.NORTHWEST),
	WESTNORTHWEST("west-northwest", Direction.WEST),
	WESTBYNORTH("west-by-north", Direction.WEST),
	
	// X-axis left

	WEST("west", Direction.WEST),

	// Lower left quadrant
	
	WESTBYSOUTH("west-by-south", Direction.WEST),
	WESTSOUTHWEST("west-southwest", Direction.WEST),
	SOUTHWESTBYWEST("southwest-by-west", Direction.SOUTHWEST),
	SOUTHWEST("southwest", Direction.SOUTHWEST),
	SOUTHWESTBYSOUTH("southwest-by-south", Direction.SOUTHWEST),
	SOUTHSOUTHWEST("south-southwest", Direction.SOUTH),
	SOUTHBYWEST("south-by-west", Direction.SOUTH),
	
	// Y-axis down
	
	SOUTH("south", Direction.SOUTH),
	
	// Lower right quadrant 
	
	SOUTHBYEAST("south-by-east", Direction.SOUTH),
	SOUTHSOUTHEAST("south-southeast", Direction.SOUTH),
	SOUTHEASTBYSOUTH("southeast-by-south", Direction.SOUTHEAST),
	SOUTHEAST("southeast", Direction.SOUTHEAST),
	SOUTHEASTBYEAST("southeast-by-east", Direction.SOUTHEAST),
	EASTSOUTHEAST("east-southeast", Direction.EAST),
	EASTBYSOUTH("east-by-south", Direction.EAST);
	
	private String name;
	private Direction dir;
	
	private CompassAngle(String name, Direction dir) {
		
		this.name = name;
		this.dir = dir;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public Direction getDirection() {
		
		return dir;
		
	}
	
	public static CompassAngle getAngle(int angle) {
		
		int count = 0;
		
		for (CompassAngle ca : CompassAngle.values()) {
			
			if (count == angle) {
				
				return ca;
				
			}
			
			count++;
			
		}
		
		return CompassAngle.NORTH;
		
	}

}
