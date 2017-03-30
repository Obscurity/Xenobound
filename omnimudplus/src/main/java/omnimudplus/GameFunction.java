package omnimudplus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class GameFunction {
	
	public static void autoMove(Direction moveDir, Mobile mobile) {
		
		Location location = mobile.getLocation();
		
		Zone zone = mobile.getZone();
		
		ConnectNode cn = mobile.getConnectNode();
		
		Location destination = null;

		if (location instanceof Room) {
			
			Exit exit = ((Room)location).getExit(moveDir);
			
			if (exit != null) {
			
				destination = ((Room)location).getExit(moveDir).getDestination();
			
			}
		
		}
		
		if (destination != null) {
				
			Location prior = mobile.getLocation();
			
			mobile.setLocation(destination);
					
			Vector coors = new Vector(prior.getCoordinates(), destination.getCoordinates());
			
			mobile.takeMoveBalance((int)(mobile.getMoveSpeed()*coors.getAbsoluteOffset()), false);
			
			if (prior instanceof Room) {
				
				locationMsg(prior, mobile.getName() + " leaves to the " + moveDir.getName() + ".", cn);
				
			}
				
			if (destination instanceof Room) {
				
				locationMsg(destination, mobile.getName() + " arrives from the " + moveDir.getOpposite() + ".", cn);
				
				if (!mobile.isTraveling()) {
				
					cn.print(destination.printRoom(mobile));
				
				}
				
			}

		} else {
			
			if (mobile.isTraveling()) {
				
				mobile.travelStop();
				
				if (cn != null) {

					cn.println("You stop traveling.");
					cn.print(destination.printRoom(mobile));
					cn.putPrompt();
				
				}
				
			} else {
				
				cn.println("You can't go " + moveDir.getName() + ".");
				
			}

		}

	}
	
	public static Entity findFromInventory(String query, ConnectNode cn) {
		
		for (Entity object : cn.getShell().getInventory()) {
			
			for (String q : object.getReferences()) {
				
				if (query.equals(q.toLowerCase())) {
					
					return object;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static Entity findFromContainer(String query, Container container, ConnectNode cn) {
		
		for (Entity object : container.getContents()) {
			
			for (String q : object.getReferences()) {
				
				if (query.equals(q.toLowerCase())) {
					
					return object;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static Entity findFromReference(String query, ConnectNode cn) {
		
		Mobile mobile = cn.getShell();
		
		if (query != null) {
		
			if (query.equals("me") || query.equals("myself")) {
			
				return cn.getShell();
			
			}
		
			Zone zone = mobile.getZone();
			
			Location location = mobile.getLocation();

			List<Entity> objects = new ArrayList<Entity>();
			
			for (Mobile other : zone.getMobiles()) {
				
				if (other != mobile) {
				
					objects.add(other);
				
				}
				
			}

			objects.addAll(location.getContents());

			for (Entity object : objects) {

				for (String q : object.getReferences()) {

					if (query.equals(q.toLowerCase())) {
					
						return object;

					}

				}

			}
		
			return null;
			
		}
		
		return null;
		
	}
	
	public static void locationMsg(Location source, String msg) {

		Zone zone = source.getZone();
		
		if (zone instanceof Area) {
			
			Room temp = (Room)source;
			
			for (Mobile mobile : temp.getMobiles()) {
				
				ConnectNode cn = mobile.getConnectNode();
				
				if (cn == null) {
					continue;
				}
				
				cn.println("<white>" + msg);
				
				cn.putPrompt();
				
			}
			
		}

	}

	public static void locationMsg(Location source, String msg, ConnectNode exclude) {
		
		Zone zone = source.getZone();
		
		if (zone instanceof Area) {
			
			Room temp = (Room)source;
			
			for (Mobile mobile : temp.getMobiles()) {
				
				ConnectNode cn = mobile.getConnectNode();
				
				if (cn == exclude || cn == null) {
					continue;
				}
				
				cn.println("<white>" + msg);
				
				cn.putPrompt();
				
			}
			
		}

	}
    
    public static int getApproximateDistance(Mobile mobile, Location location) {
    	
    	Coordinates mobileCoors = mobile.getCoordinates();
    	Coordinates locationCoors = location.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, locationCoors);
    	
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
    
    public static Direction getAngleDirection(Mobile mobile, Location location) {
    	
    	Coordinates mobileCoors = mobile.getCoordinates();
    	
    	Coordinates locationCoors = location.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, locationCoors);
    	
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
    
    public static String getCompassString(Mobile mobile, Location location) {
    	
    	Coordinates mobileCoors = mobile.getCoordinates();
    	
    	Coordinates locationCoors = location.getCoordinates();
    	
    	Vector coorOffset = new Vector(mobileCoors, locationCoors);
    	
    	return getCompassAngle(coorOffset).getName();
    	
    }
 
    public static ArrayList<Direction> findPath(Mobile mobile, Location destination) {
    
    	PriorityQueue<PathfindingNode> openQueue = 
    			new PriorityQueue<PathfindingNode>(25, new PathfindingComparator());
    	
    	HashSet<Location> closedSet =
    			new HashSet<Location>();
    	
    	//ArrayList<PathfindingNode> openList = new ArrayList<PathfindingNode>();
    	//ArrayList<PathfindingNode> closedList = new ArrayList<PathfindingNode>();
    	
    	PathfindingNode originNode = new PathfindingNode(mobile.getLocation(), destination);
    	
    	openQueue.add(originNode);
    	
    	PathfindingNode q = originNode;
    	
    	while (openQueue.size() > 0) {
    		
    		// Pop the most promising candidate.
    		
    		q = openQueue.poll();
    		
    		System.out.println("Trying node: " + q.getCoordinates());
    		
    		// Add it to the closed set.
    		
    		closedSet.add(q.getLocation());
    		
    		ArrayList<PathfindingNode> successors = q.getSuccessors();
    		
    		for (PathfindingNode successor : successors) {
    			
    			if (successor.getLocation() == destination) {
    				q = successor;
    				break;
    			}
    			
    			boolean flagpast = false;
    			
    			for (PathfindingNode temp2 : openQueue) {
    				
    				if (temp2.getLocation() == successor.getLocation() && temp2.getF() <= successor.getF()) {
    					flagpast = true;
    					break;
    				}
    				
    			}
    			
    			if (flagpast) {
    				continue;
    			} else {
    				
    				if (closedSet.contains(successor.getLocation())) {
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
    		
    		if (q.getLocation() == destination) {
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
    	
    	for (Mobile mobile : account.getCharacters()) {
    		
    		display.append(mobile.getName());
    		display.append("\n");
    		
    	}
    	
    	display.append("\nOPTIONS:\n1. Create character\n2. Disconnect\n\n");
    	
    	return display.toString();
    	
    }
    
}
