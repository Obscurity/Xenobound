package omnimudplus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import omnimudplus.Account.Account;
import omnimudplus.Entities.Container;
import omnimudplus.Entities.Entity;
import omnimudplus.Entities.IHoldsTakables;
import omnimudplus.Entities.Mobile;
import omnimudplus.Entities.Shell;
import omnimudplus.Entities.TakableEntity;
import omnimudplus.Geography.Coordinates;
import omnimudplus.Geography.Direction;
import omnimudplus.Geography.Exit;
import omnimudplus.Geography.PathfindingComparator;
import omnimudplus.Geography.PathfindingNode;
import omnimudplus.Geography.Room;
import omnimudplus.Geography.Vector;

public class GameFunction {
	
	public static void autoMove(Direction moveDir, Mobile mobile) {
		
		if (mobile instanceof Shell) {
			
			autoMove(moveDir, (Shell)mobile);
			return;
			
		}
		
		Room<?> room = mobile.getRoom();
		
		Room<?> destination = null;
			
		Exit exit = room.getExit(moveDir);
			
		if (exit != null) {
			
			destination = room.getExit(moveDir).getDestination();
			
		}
		
		if (destination != null) {
				
			Room<?> prior = mobile.getRoom();
			
			mobile.setRoom(destination);
					
			Vector coors = new Vector(prior.getCoordinates(), destination.getCoordinates());
			
			mobile.takeMoveBalance((int)(mobile.getMoveSpeed()*coors.getAbsoluteOffset()));
			
			if (prior instanceof Room) {
				
				roomMsg(prior, mobile.getName() + " leaves to the " + moveDir.getName() + ".");
				
			}
				
			if (destination instanceof Room) {
				
				roomMsg(destination, mobile.getName() + " arrives from the " + moveDir.getOpposite() + ".");
				
			}

		} else {
			
			if (mobile.isTraveling()) {
				
				mobile.travelStop();
				
			}

		}

	}
	
	public static void autoMove(Direction moveDir, Shell shell) {
		
		Room<?> room = shell.getRoom();
		
		ConnectNode cn = shell.getConnectNode();
		
		Room<?> destination = null;
			
		Exit exit = room.getExit(moveDir);
			
		if (exit != null) {
			
			destination = room.getExit(moveDir).getDestination();
			
		}
		
		if (destination != null) {
				
			Room<?> prior = shell.getRoom();
			
			shell.setRoom(destination);
					
			Vector coors = new Vector(prior.getCoordinates(), destination.getCoordinates());
			
			shell.takeMoveBalance((int)(shell.getMoveSpeed()*coors.getAbsoluteOffset()), false);
			
			if (prior instanceof Room) {
				
				roomMsg(prior, shell.getName() + " leaves to the " + moveDir.getName() + ".", cn);
				
			}
				
			if (destination instanceof Room) {
				
				roomMsg(destination, shell.getName() + " arrives from the " + moveDir.getOpposite() + ".", cn);
				
				if (!shell.isTraveling()) {
				
					cn.print(destination.printRoom(shell));
				
				}
				
			}

		} else {
			
			if (shell.isTraveling()) {
				
				shell.travelStop();

				cn.println("You stop traveling.");
				cn.print(shell.getRoom().printRoom(shell));
				cn.putPrompt();
				
			} else {
				
				cn.println("You can't go " + moveDir.getName() + ".");
				
			}

		}

	}
	
	public static TakableEntity findFromInventory(String query, ConnectNode cn) {
		
		Iterator<TakableEntity> inventory = cn.getShell().getInventory();
		
		return iteratorFind(query, inventory);
		
	}
	
	public static <T extends TakableEntity> T findFromContainer(String query, IHoldsTakables<T> container, ConnectNode cn) {
		
		Iterator<T> contents = container.getContents();
		
		while(contents.hasNext()) {
			
			T object = contents.next();
			
			for (String q : object.getReferences()) {
				
				if (query.equals(q.toLowerCase())) {
					
					return object;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static Entity findFromReference(String query, ConnectNode cn) {
		
		Shell shell = cn.getShell();
		
		if (query != null) {
		
			if (query.equals("me") || query.equals("myself")) {
			
				return cn.getShell();
			
			}
			
			Room<?> room = shell.getRoom();

			Iterator<Entity> contents = room.getAllContents();
			
			return iteratorFind(query, contents);
			
		}
		
		return null;
		
	}
	
	private static <E extends Entity> E iteratorFind(String query, Iterator<E> iterator) {
		
		while (iterator.hasNext()) {
			
			E object = iterator.next();
			
			for (String q : object.getReferences()) {

				if (query.equals(q.toLowerCase())) {
				
					return object;

				}

			}
			
		}
		
		return null;
		
	}
	
	public static <E extends Entity> String printIteratorList(Iterator<E> iterator) {
		
		StringBuilder sb = new StringBuilder();
		
		if (iterator.hasNext()) {
		
			while (iterator.hasNext()) {
			
				E object = iterator.next();
			
				sb.append(object.getShortDesc());
		
				if (iterator.hasNext()) {
		
					sb.append(", ");
			
				}
		
			}
	
			sb.append(".");
			
		}
		
		return sb.toString();
		
	}
	
	public static Entity getFromRoom(Mobile mobile, TakableEntity entity) {
		
		Room<?> room = mobile.getRoom();
		
		if (room == null) {
			return null;
		}
		
		if (!room.containsEntity(entity)) {
			return mobile;
		}
		
		room.removeEntity(entity);
		entity.setRoom(null);
		mobile.addEntity(entity);
		
		roomMsg(room, mobile.getName() + " picks up " + entity.getShortDesc() + ".");
		
		return entity;
		
	}
	
	public static <T extends TakableEntity> Entity getFromContainer(Mobile mobile, Container<T> container, T entity) {
		
		Room<?> room = mobile.getRoom();
		
		if (!room.containsEntity(container) || !mobile.hasEntity(container)) {
			return null;
		}
		
		if (!container.containsEntity(entity)) {
			return mobile;
		}
		
		container.removeEntity(entity);
		mobile.addEntity(entity);
		
		roomMsg(room, mobile.getName() + " removes " + entity.getShortDesc() + " from " + container.getShortDesc() + ".");
		
		return entity;
		
	}
	
	public static <T extends TakableEntity> Entity getFromIHoldsTakables(Mobile mobile, IHoldsTakables<T> container, T entity) {
		
		Room<?> room = mobile.getRoom();
		
		if (!room.containsEntity(container)) {
			return null;
		}
		
		if (!container.containsEntity(entity)) {
			return mobile;
		}
		
		container.removeEntity(entity);
		mobile.addEntity(entity);
		
		roomMsg(room, mobile.getName() + " removes " + entity.getShortDesc() + " from " + container.getShortDesc() + ".");
		
		return entity;
		
	}
	
	public static Entity drop(Mobile mobile, Entity entity) {
		
		Room<?> room = mobile.getRoom();
		
		if (room == null) {
			
			return null;
			
		}
		
		if (!mobile.hasEntity(entity)) {
			
			return mobile;
			
		}
		
		mobile.removeEntity(entity);
		mobile.getRoom().addEntity(entity);
		entity.setRoom(mobile.getRoom());
		
		roomMsg(room, mobile.getName() + " drops " + entity.getShortDesc() + ".");
		
		return entity;
		
	}
	
	public static <T extends TakableEntity> Entity putInContainer(Mobile mobile, Container<T> container, T entity) {
		
		Room<?> room = mobile.getRoom();
		
		if (!room.containsEntity(container) || !mobile.hasEntity(container)) {
			
			return null;
			
		}
		
		if (!mobile.hasEntity(entity)) {
			
			return mobile;
			
		}
		
		mobile.removeEntity(entity);
		
		container.addEntity(entity);
		
		roomMsg(room, mobile.getName() + " puts " + entity.getShortDesc() + " into " + container.getShortDesc() + ".");
		
		return entity;
		
	}
	
	public static void roomMsg(Room<?> source, String msg) {
		
		Iterator<Shell> shells = source.getShells();
		
		while (shells.hasNext()) {
				
			ConnectNode cn = shells.next().getConnectNode();
				
			if (cn == null) {
				continue;
			}
				
			cn.println("<white>" + msg);
				
			cn.putPrompt();
				
		}

	}

	public static void roomMsg(Room<?> source, String msg, ConnectNode exclude) {
		
		Iterator<Shell> shells = source.getShells();
		
		while (shells.hasNext()) {
				
			ConnectNode cn = shells.next().getConnectNode();
				
			if (cn == exclude || cn == null) {
				continue;
			}
				
			cn.println("<white>" + msg);
				
			cn.putPrompt();
				
		}

	}
    
    public static int getApproximateDistance(Mobile mobile, Room<?> room) {
    	
    	Coordinates mobileCoors = mobile.getRoom().getCoordinates();
    	Coordinates roomCoors = room.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, roomCoors);
    	
    	int squaredDistance = coorOffset.getSquaredDistance();
    	
    	synchronized (Physics.squarerootmaplock) {
    		
    		if (Physics.squarerootmap.get(squaredDistance) != null) {
    		
    			return Physics.squarerootmap.get(squaredDistance);
    		
    		} else {
    			
    			int squareRoot = (int)(Math.sqrt(squaredDistance) + 0.5);
    			Physics.squarerootmap.put(squaredDistance, squareRoot);
    			return squareRoot;
    			
    		}
    		
    	}	
    	
    }
    
    public static Direction getAngleDirection(Mobile mobile, Room<?> room) {
    	
    	Coordinates mobileCoors = mobile.getRoom().getCoordinates();
    	
    	Coordinates roomCoors = room.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, roomCoors);
    	
    	int Xdist = coorOffset.getXoffset();
    	
    	int Ydist = coorOffset.getYoffset();
    	
    	// Test the easy ones first. If only they could all be this way.
    	
    	if (Xdist == 0) {
    		
    		if (Ydist > 0) {
    			
    			return Direction.NORTH;
    			
    		}
    			
    		return Direction.SOUTH;
    		
    	}
    	
    	if (Ydist == 0) {
    		
    		if (Xdist > 0) {
    			
    			return Direction.WEST;
    			
    		}
    			
    		return Direction.EAST;
    		
    	}
    	
    	// Intercardinal directions next.
    	
    	if (Xdist == Ydist) {
    		
    		if (Xdist > 0) {
    		
    			return Direction.NORTHWEST;
    			
    		}
    			
    		return Direction.SOUTHEAST;
    		
    	}
    	
    	if (Xdist == 0 - Ydist) {
    		
    		if (Xdist > 0) {
    		
    			return Direction.SOUTHWEST;
    			
    		}
    			
    		return Direction.NORTHEAST;
    		
    	}
    	
    	// Unfortunately, we've been reduced to the expensive way.
    	
    	double angle = Math.atan(((double)Ydist/(double)Xdist)) * (180 / Math.PI);
    	
    	if (Xdist > 0) {
        	// Right quadrants
    		angle += 90;
    		
    	} else {
        	// Left quadrants
    		angle += 270;
    		
    	}
    	
    	// Since the angle points the way FROM the given tile, we need to flip it, like so.
    	
    	angle += 180;
    	
    	// Are we over? Fix that.
    	
    	if (angle > 360) {
    		
    		angle -= 360;
    		
    	}
    	
    	return Direction.getDirByAngle(angle);
    	
    }
    
    public static CompassAngle getCompassAngle(Vector coorOffset) {
    	
    	int Xdist = coorOffset.getXoffset();
    	int Ydist = coorOffset.getYoffset();
    	
    	// These test for easy cases - the eight cardinal directions, along straight and diagonals.
    	
    	if (Xdist == 0) {
    		
    		if (Ydist > 0) {
    			
    			return CompassAngle.NORTH;
    			
    		} else {
    			
    			return CompassAngle.SOUTH;
    			
    		}
    		
    	} else if (Ydist == 0) {
    		
    		if (Xdist > 0) {
    			
    			return CompassAngle.EAST;
    			
    		} else {
    			
    			return CompassAngle.WEST;
    			
    		}
    		
    	} else if (Xdist == Ydist) {
    		
    		if (Xdist > 0) {
    			
    			return CompassAngle.NORTHEAST;
    			
    		} else {
    			
    			return CompassAngle.SOUTHWEST;
    			
    		}
    		
    	} else if (Xdist == 0 - Ydist) {
    		
    		if (Xdist > 0) {
    			
    			return CompassAngle.SOUTHEAST;
    			
    		} else {
    			
    			return CompassAngle.NORTHWEST;
    			
    		}
    		
    	}
    	
    	// We're forced to use the more expensive method.
    	
    	// We add an offset of 5.625 here, so that we don't round down to an inaccurate angle.
    	
    	int angle = (int)(Math.atan(((double)Ydist/(double)Xdist)) * (180 / Math.PI) + 5.625);
    	
    	if (Xdist < 0) {
    		// Left quadrants
    		angle += 180;
    		
    	} else if (Xdist > 0 && Ydist < 0) {
    		// Lower right
    		angle += 360;
    		
    	}
    	
    	// We're turning this into a nice integer between 0 and 31, inclusive, to access an angle.
    	
    	angle *= 100;
    	angle /= 1125;
    	
    	/* This is like dividing by 11.25 - or, in other words, what 360 / 32 equals.
    	 * Thus, we end up with 360 / 11.25 = 32, and the angle divided by 11.25 gives
    	 * us 0 - 31, which corresponds nicely to our list of compass angles.
    	 */
    	
    	return CompassAngle.getAngle(angle);
    	
    }
    
    public static String getCompassString(Mobile mobile, Room<?> room) {
    	
    	Coordinates mobileCoors = mobile.getRoom().getCoordinates();
    	
    	Coordinates locationCoors = room.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, locationCoors);
    	
    	return getCompassAngle(coorOffset).getName();
    	
    }
 
    public static ArrayList<Direction> findPath(Mobile mobile, Room<?> destination) {
    
    	PriorityQueue<PathfindingNode> openQueue = 
    			new PriorityQueue<PathfindingNode>(25, new PathfindingComparator());
    	
    	HashSet<Room<?>> closedSet =
    			new HashSet<Room<?>>();
    	
    	//ArrayList<PathfindingNode> openList = new ArrayList<PathfindingNode>();
    	//ArrayList<PathfindingNode> closedList = new ArrayList<PathfindingNode>();
    	
    	PathfindingNode originNode = new PathfindingNode(mobile.getRoom(), destination);
    	
    	openQueue.add(originNode);
    	
    	PathfindingNode q = originNode;
    	
    	while (openQueue.size() > 0) {
    		
    		// Pop the most promising candidate.
    		
    		q = openQueue.poll();
    		
    		System.out.println("Trying node: " + q.getCoordinates());
    		
    		// Add it to the closed set.
    		
    		closedSet.add(q.getRoom());
    		
    		ArrayList<PathfindingNode> successors = q.getSuccessors();
    		
    		for (PathfindingNode successor : successors) {
    			
    			if (successor.getRoom() == destination) {
    				q = successor;
    				break;
    			}
    			
    			boolean flagpast = false;
    			
    			for (PathfindingNode temp2 : openQueue) {
    				
    				if (temp2.getRoom() == successor.getRoom() && temp2.getF() <= successor.getF()) {
    					flagpast = true;
    					break;
    				}
    				
    			}
    			
    			if (flagpast) {
    				continue;
    			} else {
    				
    				if (closedSet.contains(successor.getRoom())) {
    					flagpast = true;
    				}
    				
    				if (flagpast) {
    					continue;
    				}
    				
    			}
    			
    			System.out.println("Successor added: " + successor.getCoordinates());
    			
    			q = successor;
    			
    			openQueue.add(successor);
    			
    		}
    		
    		if (q.getRoom() == destination) {
    			break;
    		}
    		
    		if (openQueue.size() == 0) {
    			// Exhausted our possibilities.
    			System.out.println("Our list has been exhausted!");
    			q = null;
    		}
    		
    	}
    	
    	if (q != null) {
    		
    		return reconstructPath(q);
    		
    	} else {
    	
    		// No valid path.
    		
    		return null;
    	
    	}
    	
    }
    
    public static ArrayList<Direction> reconstructPath(PathfindingNode tail) {
    	
    	ArrayList<Direction> path = new ArrayList<Direction>();
    	PathfindingNode temp = tail;
    	
    	while (temp.getPriorDir() != null) {
    		
    		path.add(0, temp.getPriorDir());
    		
    		temp = temp.getParent();
    		
    	}
    	
    	return path;
    	
    }
    
    public static void parseAbility(Ability ability) {
    	
    	
    	
    }
    
    public static String stringArrayToList(String[] a) {
    	
		StringBuilder builder = new StringBuilder();
		
		if (a.length == 0) {
			
			return "";
			
		} else if (a.length == 1) {
			
			return a[0];
			
		} else if (a.length == 2) {
			
			builder.append(a[0]);
			builder.append(" and ");
			builder.append(a[1]);
			
			return builder.toString();
			
		} else if (a.length > 2) {
			
			for (int i = 0; i < a.length - 1; i++) {
				
				builder.append(a[i]);
				builder.append(", ");
				
			}
			
			builder.append("and ");
			builder.append(a[a.length - 1]);
			
			return builder.toString();
			
		} else {
			
			return "Please report an error if you see this.";
			
		}
    	
    }
    
    public static String titleSplash() {
    	
    	// TODO
    	
    	return "OMNIMUD. (imagine there's a really cool title splash here.)\n\n";
    	
    }
    
    public static String connectOptions() {
    	
    	// TODO
    	
    	return "1. Create account\n2. ???\n3. lolol\n\n";
    	
    }
    
    public static String displayAccount(Account account) {
    	
    	// TODO
    	
    	StringBuilder display = new StringBuilder();
    	
    	display.append("ACCOUNT: ");
    	
    	display.append(account.getName());
    	
    	display.append("\n\nPOTENTIAL: ");
    	
    	display.append(account.getPotential());
    	
    	display.append("\n\nCHARACTERS:\n");
    	
    	Iterator<Shell> characters = account.getCharacters();
    	
    	while (characters.hasNext()) {
    		
    		display.append(characters.next().getName());
    		display.append("\n");
    		
    	}
    	
    	display.append("\nOPTIONS:\n1. Create character\n2. Disconnect\n\n");
    	
    	return display.toString();
    	
    }
    
}
