package omnimudplus;

import java.util.ArrayList;

public class PathfindingNode {
	
	private PathfindingNode parent;
	private Location location;
	private Location destination;
	private Coordinates coordinates;
	private Vector offset;
	private Direction priorDir;
	
	// F - sum.
	// G - cost to get here.
	// H - cost left to get there.
	
	private double f, g, h;
	
	// Parent constructor.
	
	public PathfindingNode(Location location, Location destination) {
		
		this.parent = null;
		this.location = location;
		this.destination = destination;
		this.priorDir = null;
		this.coordinates = location.getCoordinates();
		
		this.offset = new Vector(coordinates, destination.getCoordinates());
		
		// Compute some values.
		
		this.g = 0;
		this.h = offset.getDistance();
		
		this.f = g + h;
		
	}
	
	// Children constructor.
	
	public PathfindingNode(PathfindingNode parent, Location location, Direction dir) {
		
		this.parent = parent;
		this.location = location;
		this.destination = parent.getDestination();
		this.priorDir = dir;
		this.coordinates = location.getCoordinates();
		
		this.offset = new Vector(coordinates, destination.getCoordinates());
		
		// Compute some values.
		
		this.g = parent.getG() + priorDir.getOffset().getAbsoluteOffset();
		this.h = offset.getDistance();
		
		this.f = g + h;
		
	}
	
	public PathfindingNode getParent() {
		
		return parent;
		
	}
	
	public ArrayList<PathfindingNode> getSuccessors() {
		
		ArrayList<PathfindingNode> successors = new ArrayList<PathfindingNode>();
		
		for (Direction dir : Direction.values()) {
			
			Location test = null;
			
			if (location instanceof Room) {
				
				Exit temp = ((Room)location).getExit(dir);
				
				if (temp != null) {
				
					test = ((Room)location).getExit(dir).getDestination();
				
				}
				
			}
			
			if (test != null) {
			
				PathfindingNode successor = new PathfindingNode(this, test, dir);
			
				successors.add(successor);
			
			}
			
		}
		
		return successors;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public Location getDestination() {
		
		return destination;
		
	}
	
	public Coordinates getCoordinates() {
		
		return coordinates;
		
	}
	
	public double getF() {
		
		return f;
		
	}
	
	public double getG() {
	
		return g;
		
	}
	
	public Direction getPriorDir() {
		
		return priorDir;
		
	}

}
