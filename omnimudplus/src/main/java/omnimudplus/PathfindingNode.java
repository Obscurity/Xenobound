package omnimudplus;

import java.util.ArrayList;

public class PathfindingNode {
	
	private PathfindingNode parent;
	private Room room;
	private Room destination;
	private Coordinates coordinates;
	private Vector offset;
	private Direction priorDir;
	
	// F - sum.
	// G - cost to get here.
	// H - cost left to get there.
	
	private double f, g, h;
	
	// Parent constructor.
	
	public PathfindingNode(Room room, Room destination) {
		
		this.parent = null;
		this.room = room;
		this.destination = destination;
		this.priorDir = null;
		this.coordinates = room.getCoordinates();
		
		this.offset = new Vector(coordinates, destination.getCoordinates());
		
		// Compute some values.
		
		this.g = 0;
		this.h = offset.getDistance();
		
		this.f = g + h;
		
	}
	
	// Children constructor.
	
	public PathfindingNode(PathfindingNode parent, Room room, Direction dir) {
		
		this.parent = parent;
		this.room = room;
		this.destination = parent.getRoom();
		this.priorDir = dir;
		this.coordinates = room.getCoordinates();
		
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
			
			Room test = null;
				
			Exit temp = room.getExit(dir);
				
			if (temp != null) {
				
				test = room.getExit(dir).getDestination();
				
			}
			
			if (test != null) {
			
				PathfindingNode successor = new PathfindingNode(this, test, dir);
			
				successors.add(successor);
			
			}
			
		}
		
		return successors;
		
	}
	
	public Room getRoom() {
		
		return room;
		
	}
	
	public Room getDestination() {
		
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
