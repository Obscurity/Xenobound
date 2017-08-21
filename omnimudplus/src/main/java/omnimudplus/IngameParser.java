package omnimudplus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import omnimudplus.Colors.Color;
import omnimudplus.Entities.Appendage;
import omnimudplus.Entities.BodyPart;
import omnimudplus.Entities.Building;
import omnimudplus.Entities.Container;
import omnimudplus.Entities.Entity;
import omnimudplus.Entities.Feature;
import omnimudplus.Entities.Mobile;
import omnimudplus.Entities.Shell;
import omnimudplus.Entities.TakableEntity;
import omnimudplus.Geography.Area;
import omnimudplus.Geography.BuildingArea;
import omnimudplus.Geography.BuildingRoom;
import omnimudplus.Geography.Coordinates;
import omnimudplus.Geography.Direction;
import omnimudplus.Geography.Room;

public class IngameParser {

	public static void parse(String[] command, ConnectNode cn) {
		
		Shell shell = cn.getShell();
		
		
		
		System.out.print(shell.getName() + ":");
		
		for (String s : command) {
			
			System.out.print(" " + s);
			
		}
		
		System.out.println();

		for (Direction test : Direction.values()) {
			
			if (test.getName().equals(command[0].toLowerCase()) || test.getShortName().equals(command[0].toLowerCase())) {
				
				move(shell, test);
				return;
				
			}
			
		}
		
		switch (command[0].toLowerCase()) {
		
		case "": break;
		/* case "north": case "n": travel(mobile, Direction.NORTH); break;
		case "northeast": case "ne": travel(mobile, Direction.NORTHEAST); break;
		case "east": case "e": travel(mobile, Direction.EAST); break;
		case "southeast": case "se": travel(mobile, Direction.SOUTHEAST); break;
		case "south": case "s": travel(mobile, Direction.SOUTH); break;
		case "southwest": case "sw": travel(mobile, Direction.SOUTHWEST); break;
		case "west": case "w": travel(mobile, Direction.WEST); break;
		case "northwest": case "nw": travel(mobile, Direction.NORTHWEST); break;
		case "up": case "u": travel(mobile, Direction.UP); break;
		case "down": case "d": travel(mobile, Direction.DOWN); break; */
		case "findpath": findPath(fromIndex(command, 1), shell); break;
		case "stop": stop(shell); break;
		case "look": case "l": if (command.length <= 1) { look(shell); } else { look(command, shell); } break;
		case "map": cn.print(shell.getArea().getMapString(shell, 5)); break;
		case "status": case "stat": status(cn); break;
		case "quit": case "qq": quit(cn); break;
		case "say": case "'": case "sing": say(command, shell); break;
		case "i": case "inv": case "inventory": inventory(cn); break;
		case "get": case "take": if (command.length > 1) {
													get(command, shell);
												} else {
													cn.println(command[0].substring(0, 1).toUpperCase() + command[0].substring(1) + " what?");
												}
												break;
		case "drop": if (command.length > 1) { drop(command, shell); } else { cn.println("Drop what?"); } break;
		case "put": if (command.length > 1) { put(command, shell); } else { cn.println("Put what in what?"); } break;
		case "go": if (command.length > 1) { go(fromIndex(command, 1), cn); } else { cn.println("Go where?"); } break;
		case "who": Omnimud.who(cn); break;
		case "colors": if (command.length > 1) { colors(fromIndex(command, 1), cn); } else { colors(cn); } break;
		case "end": end(cn); break;
		case "newroom": if (command.length > 1) { newRoom(fromIndex(command, 1), shell); } else { cn.println("Create a new room where?"); } break;
		case "punch": if (command.length > 1) { attack(shell, fromIndex(command, 1), Attack.PUNCH); } else { cn.println("Punch what?"); } break;
		case "fireball": if (command.length > 1) { attack(shell, fromIndex(command, 1), Attack.FIREBALL); } else { cn.println("Cast fireball at what?"); }; break;
		case "enter": enter(shell); break;
		case "leave": leave(shell); break;
		case "area": System.out.println(shell.getName() + ": " + shell.getArea()); break;
		case "body": body(shell); break;
		default: invalid(cn); break;

		}

	}
	
	public static String[] fromIndex(String[] input, int index) {
		
		if (index > input.length - 1) {
			
			return input;
			
		} else {
			
			String[] output = new String[input.length - index];
			
			for (int i = index; i < input.length; i++) {
				
				output[i - index] = input[i];
				
			}
			
			return output;
			
		}
		
	}

	public static void move(Shell shell, Direction moveDir) {
		
		ConnectNode cn = shell.getConnectNode();
		
		if (!shell.hasMoveBalance()) {
			
			cn.println("You can't move that quickly.");
			return;
			
		}
		
		GameFunction.autoMove(moveDir, shell);

	}
	
	public static void stop(Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		if (cn.getShell().isTraveling()) {
			
			cn.getShell().travelStop();
			cn.println("You stop traveling.");
			
		}
		
	}

	public static void look(Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();

		cn.print(shell.getRoom().printRoom(shell));

	}

	public static void quit(ConnectNode cn) {

		Mobile mobile = cn.getShell();
		
		GameFunction.roomMsg(mobile.getRoom(), mobile.getName() + " has disconnected.", cn);

		cn.println("You have disconnected.");
		
		Omnimud.cleanup(cn);

	}

	public static void invalid(ConnectNode cn) {

		Random rand = new Random();
		
		String[] invalid = {"That is not a valid command.",
				"Come again?", "I don't follow your meaning.",
				"I don't recognize that command.", "Huh? What?",
				"Try again?", "Command not recognized."
		};
		
		cn.println(invalid[rand.nextInt(invalid.length)]);

	}

	public static void say(String[] words, Shell shell) {

		ConnectNode cn = shell.getConnectNode();
		
		String speech = "";

		String flavorText = "";

		boolean flavor = false;

		if (words.length > 1) {

			if (words[1].length() > 0) {

				if (words[1].charAt(0) == '(' && words[1].charAt(Math.min(1, words[1].length())) != ')') {

					flavor = true;

				} else if (words[1].charAt(0) == '(' && words[1].charAt(Math.min(1, words[1].length())) == ')') {

					words[1] = "";

				}

			}

		}

		for(int i = 1; i < words.length; i++) {

			if (flavor == true) {

				System.out.println(flavorText);

				flavorText = flavorText + words[i];

				System.out.println(flavorText);

				if (words[i].charAt(words[i].length() - 1) != ')') {

					flavorText = flavorText + " ";

				} else {

					flavorText = flavorText.substring(1, flavorText.length() - 1);

					flavorText = flavorText.substring(0, 1).toLowerCase() + flavorText.substring(1);

					if (flavorText.charAt(flavorText.length() - 1) == '.' ||
							flavorText.charAt(flavorText.length() - 1) == '?' ||
							flavorText.charAt(flavorText.length() - 1) == '!') {

						flavorText = flavorText.substring(0, flavorText.length() - 1);

					}

					flavor = false;

				}

			} else {

				speech = speech + words[i];

				if (i < words.length - 1) {

					speech = speech + " ";

				}

			}

		}

		if (speech.length() > 0) {

			String verb;

			speech = speech.trim();

			speech = speech.substring(0, Math.min(1, speech.length())).toUpperCase() + speech.substring(Math.min(1, speech.length()));

			switch(speech.charAt(Math.max(0, speech.length() - 1))) {

			case '?': verb = "ask"; break;
			case '!': verb = "exclaim"; break;
			case '.': verb = "say"; break;
			case ',': verb = "say"; break;
			default: verb = "say"; speech = speech + "."; break;

			}

			if (words[0].toLowerCase().equals("sing")) { verb = "sing"; }
			
			Room<?> room = shell.getRoom();

			Iterator<Shell> shells = room.getShells();
			
			StringBuilder builder = new StringBuilder();

			builder.append(shell.getName());
			builder.append(" ");
			builder.append(verb);
			builder.append("s, \"");
			builder.append(speech);
			builder.append("\"");
			
			String output = builder.toString();
					
			while (shells.hasNext()) {
					
				ConnectNode oc = shells.next().getConnectNode();
					
				if (oc == cn || oc == null) {
					continue;
				}
					
				oc.println(oc.getColorScheme().getSpeech() + output);
				oc.putPrompt();
						
			}
				
			cn.println(cn.getColorScheme().getSpeech() + "You " + verb + ", \"" + speech + "\"" + cn.getColorScheme().getForeground());

		} else {

			cn.println("You exercise your right to remain silent.");
			
		}

	}

	public static <T extends TakableEntity> void look (String[] input, Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		String query;
		
		if ((input[1].equals("at") || input[1].equals("in") || input[1].equals("into")) && input.length > 2) {
			
			query = input[2].toLowerCase();
			
		} else {
			
			query = input[1].toLowerCase();
			
		}
		
		Entity reference = GameFunction.findFromReference(query, cn);
		
		if (reference == null) {
			
			reference = GameFunction.findFromInventory(query, cn);
			
		}
		
		if (reference == null) {
			
			cn.println("You see no '" + query + "' here.");
			
		} else if (reference == cn.getShell()) {
			
			
			cn.println("(looking at yourself)");
			cn.println(reference.getLongDesc());
			
		} else {
			
			if (!query.equals(reference.getName().toLowerCase())) {
			
				cn.println("(looking at " + reference.getName() + ")");
			
			}
			
			cn.println(reference.getLongDesc());
			
			if (reference instanceof Container<?>) {
				
				@SuppressWarnings("unchecked")
				Container<T> container = (Container<T>)reference;
				
				Iterator<T> contents = container.getContents();
				
				if (contents.hasNext()) {
				
					cn.print("It is currently holding ");
				
					cn.print(GameFunction.printIteratorList(contents));
					
				}
				
			}
			
		}

	}
	
	public static void status (ConnectNode cn) {
		
		int maxWeight = cn.getShell().getMaxWeight();
		
		int maxpounds = maxWeight / 16;
		
		int maxounces = maxWeight % 16;
		
		if (maxounces == 0) {
			
			cn.println("You can carry a maximum of " + maxpounds + " pounds.");
			
		} else {
			
			cn.println("You can carry a maximum of " + maxpounds + " pounds and " + maxounces + " ounces.");
			
		}
		
	}
	
	public static void inventory (ConnectNode cn) {
		
		Iterator<TakableEntity> inventory = cn.getShell().getInventory();
		
		int upperbound = cn.getShell().getInventoryCount();
		
		if (inventory.hasNext()) {
		
			int items = 0;
			
			cn.println("You are carrying:");
		
			while (inventory.hasNext()) {
				
				items++;
				
				cn.print(inventory.next().getShortDesc());
			
				if (items < upperbound - 2) {
			
					cn.print(", ");
				
				} else {
					
					cn.print(", and ");
					
				}
			
			}
		
			cn.println(".");
			
			int weightCarried = cn.getShell().getWeightCarried();
			
			int pounds = weightCarried / 16;
			
			int ounces = weightCarried % 16;
			
			if (ounces == 0) {
			
				cn.println("You have " + upperbound + " items, weighing " + pounds + " pounds.");
				
			} else {
				
				cn.println("You have " + upperbound + " items, weighing " + pounds + " pounds and " + ounces + " ounces.");
				
			}
		
		} else {
			
			cn.println("You are carrying nothing.");
			
		}
		
	}
	
	public static void get (String[] input, Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		String query = null;
		String indirect = null;
		Container<?> container = null;
		
		if (input.length < 4) {
		
			query = input[1].toLowerCase();
		
		} else {
			
			query = input[1].toLowerCase();
			
			if (input[2].toLowerCase().equals("from")) {
				
				indirect = input[3].toLowerCase();
				
				Entity temp = GameFunction.findFromInventory(indirect, cn);
				
				if (temp instanceof Container) {
				
					container = (Container<?>)temp;
				
				}
				
				temp = GameFunction.findFromReference(indirect, cn);
				
				if (temp instanceof Container) {
				
					container = container == null ? (Container<?>)temp : container;
				
				}
				
			}
			
		}
		
		Entity reference;
		
		if (container == null) {
			
			reference = GameFunction.findFromReference(query, cn);
			
		} else {
			
			reference = GameFunction.findFromContainer(query, container, cn);
			
		}
		
		if (reference == null) {
			
			if (container == null) {
				
				cn.println("You see no '" + query + "' here.");
			
			} else {
				
				cn.println("You see no '" + query + "' inside " + container.getShortDesc() + ".");
				
			}
			
		} else if (cn.getShell().hasEntity(reference)) {
			
			cn.println("You're already holding " + reference.getShortDesc() + "!");
			
		} else if (cn.getShell() == reference) {
			
			cn.println("No matter how enamored, you are incapable of picking yourself up.");
			
		} else {
			
			if (!(reference instanceof TakableEntity)) {
				
				cn.println("You cannot pick up " + reference.getShortDesc() + ".");
				return;
				
			}
			
			if (container == null) {
			
				if (!query.equals(reference.getName())) {
				
					cn.println("(picking up " + reference.getName() + ")");
				
				}
			
				cn.println("You pick up " + reference.getShortDesc() + ".");
				
				GameFunction.roomMsg(shell.getRoom(), shell.getName() + " picks up " + reference.getShortDesc() + ".", cn);
			
				shell.getRoom().removeEntity(reference);
			
				shell.addEntity((TakableEntity)reference);
			
			} else {
				
				if (!query.equals(reference.getName()) || !indirect.equals(container.getName())) {
					
					cn.println("(removing " + reference.getName() + " from " + container.getName() + ")");
					
				}
				
				cn.println("You remove " + reference.getShortDesc() + " from " + container.getShortDesc() + ".");
				
				GameFunction.roomMsg(shell.getRoom(), shell.getName() + " removes " + reference.getShortDesc() + " from " + container.getShortDesc() + ".", cn);
				
				container.removeEntity((TakableEntity)reference);
				
				shell.addEntity((TakableEntity)reference);
				
			}
			
		}
		
	}
	
	public static void drop (String[] input, Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		String query = input[1].toLowerCase();
		
		Entity reference = GameFunction.findFromInventory(query, cn);
		
		if (reference == null) {
			
			cn.println("You're not holding a '" + query + "'.");
			
		} else {
			
			if (!query.equals(reference.getName())) {
				
				cn.println("(dropping " + reference.getName() + ")");
				
			}
			
			cn.println("You drop " + reference.getShortDesc() + ".");
			
			Room<?> room = shell.getRoom();
			
			GameFunction.roomMsg(room, shell.getShortDesc() + " drops " + reference.getShortDesc() + ".", cn);
			
			room.addEntity(reference);
			
			room.removeEntity(reference);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends TakableEntity> void put(String[] input, Shell shell) {

		ConnectNode cn = shell.getConnectNode();
		
		String query = null;
		String indirect = null;
		Entity containerCheck = null;
		Container<T> container = null;

		if (input.length < 4) {

			cn.println("Syntax: PUT <item> IN <container>.");
			
			return;

		} else {

			query = input[1].toLowerCase();

			if (input[2].toLowerCase().equals("in") || input[2].toLowerCase().equals("inside") || input[2].toLowerCase().equals("into")) {

				indirect = input[3].toLowerCase();

				containerCheck = GameFunction.findFromInventory(indirect, cn);

				if (containerCheck == null) {
					
					containerCheck = GameFunction.findFromReference(indirect, cn);
					
				}

			}

		}

		TakableEntity object = GameFunction.findFromInventory(query, cn);

		if (object == null) {
			
			cn.println("You have no '" + query + "'.");
			
			return;

		}
		
		if (containerCheck == null) {
			
			cn.println("You see no '" + indirect + "' here.");
					
			return;

		}
		
		if (!(containerCheck instanceof Container)) {
			
			cn.println("You cannot put anything into " + containerCheck.getShortDesc() + ".");
			
			return;
			
		} else {
			
			container = (Container<T>)containerCheck;
			
		}
		
		if (object == container) {
			
			cn.println("Unfortunately, this endeavor is constrained by Euclidean geometry.");
			
			return;
			
		}

		if (container.getTypes() != null) {

			if (!container.getTypes()[0].equals("any")) {

				boolean found = false;

				for (String ref : object.getReferences()) {

					for (String q : container.getTypes()) {

						if (q.toLowerCase().equals(ref.toLowerCase())) {

							found = true;
							break;

						}

					}

					if (found) {
						break;
					}

				}

				if (!found) {

					cn.println("You find that " + container.getShortDesc() + " cannot hold " + object.getShortDesc() + ".");
					
					return;

				}

			}

		}

		if ((container.getWeightCarried() + object.getWeight()) > container.getMaxWeight()) {
			
			cn.println("It seems that " + container.getShortDesc() + " cannot hold any more weight.");
			
			return;

		} else {
			
			cn.println("You put " + object.getShortDesc() + " into " + container.getShortDesc() + ".");
			
			GameFunction.roomMsg(shell.getRoom(), shell.getShortDesc() + " puts " + object.getShortDesc() + " into " + container.getShortDesc() + ".", cn);
			shell.removeEntity(object);
			container.addEntity((T)object);

		}


	}
	
	public static void go(String[] input, ConnectNode cn) {
		
		Shell shell = cn.getShell();
		
		int Xcoor = Integer.parseInt(input[0]);
		int Ycoor = Integer.parseInt(input[1]);
		
		Coordinates coors = new Coordinates(Xcoor, Ycoor);
		
		Room<?> destination = shell.getArea().getRoom(coors);
		
		if (destination != null) {
			
			GameFunction.roomMsg(shell.getRoom(), shell.getName() + " leaves to the ether.", cn);

			shell.setRoom(destination);

			GameFunction.roomMsg(shell.getRoom(), shell.getName() + " arrives from the ether.", cn);

			look(shell);
			
		} else {
			
			cn.println("Invalid destination specified.");
			
		}
		
	}
	
	public static void enter(Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		if (!shell.hasMoveBalance()) {
			
			cn.println("You can't move that quickly.");
			return;
			
		}
		
		Room<?> room = shell.getRoom();
			
		Feature feature = room.getFeature();
			
		if (feature == null) {
				
			cn.println("You don't see a building here.");
				
		} else if (feature instanceof Building ) {
			
			Building building = (Building)feature;
			
			cn.println("You enter " + building.getShortDesc() + ".");
			
			shell.setRoom(building.getOrigin());
				
			shell.takeMoveBalance(shell.getMoveSpeed(), false);
			
			cn.print(shell.getRoom().printRoom(shell));
				
		}
		
	}
	
	public static void leave(Shell shell) {
		
		ConnectNode cn = shell.getConnectNode();
		
		if (!shell.hasMoveBalance()) {
			
			cn.println("You can't move that quickly.");
			return;
			
		}
		
		Room<?> room = shell.getRoom();
		
		if (room instanceof BuildingRoom) {
		
			BuildingRoom br = (BuildingRoom)room;
			
			BuildingArea ba = (BuildingArea)room.getArea();
			
			if (br.isOrigin()) {
				
				Building building = ba.getBuilding();
				
				cn.println("You leave " + building.getShortDesc() + ".");
				
				shell.setRoom(building.getParentRoom());
				
				shell.takeMoveBalance(shell.getMoveSpeed(), false);
				
				cn.print(shell.getRoom().printRoom(shell));
				
				return;
				
			}
			
		} else {
			
			cn.println("You're not inside a building.");
			return;
			
		}
		
		cn.println("You can't leave from this point.");
		
	}
    
    public static void colors(ConnectNode cn) {
    	
    	int count = 0;
    	
    	for (Color c : Color.values()) {
    		
    		cn.println("[" + count + "] <" + c.getName() + ">" + c.getName());
    		count++;
    		
    	}
    	
    }
    
    public static void colors(String[] input, ConnectNode cn) {
    	
    	int count = 0;
    	
    	for (Color c : Color.values()) {
    		
    		if (c.getName().contains(input[0])) {
    			
        		cn.println("[" + count + "] <" + c.getName() + ">" + c.getName());
    			
    		}
    		
    		count++;
    		
    	}
    	
    }
    
    public static void body(Shell shell) {
    	
    	ConnectNode cn = shell.getConnectNode();
    	
    	Iterator<Appendage> parts = shell.getBody().getParts();
    	
    	while (parts.hasNext()) {
    		
    		Appendage a = parts.next();
    		
    		cn.println(a.toString());
    		
    	}
    	
    }
    
    public static void attack(Shell shell, String[] input, Attack attack) {
    	
    	ConnectNode cn = shell.getConnectNode();
    	
	    Entity target = GameFunction.findFromReference(input[0], cn);
	    	
	    if (target != null) {
	    	
	    	if (target instanceof Mobile) {
	    			
	    		attack.attackUse(shell, (Mobile)target);
	    		return;
	    		
	    	} else {
	    			
	    		cn.println("You can't attack that.");
	    		
	    		return;
	    			
	    	}
	    	
	    } else {

	    	cn.println("You can't see that here.");
	    	
	    	return;
	    		
	    }
    	
    }
    
    public static void newRoom(String[] command, Shell shell) {
    	
    	ConnectNode cn = shell.getConnectNode();
    	
    	Room<?> room = shell.getRoom();
    	
    	String check = command[0].toLowerCase();
    	
    	for (Direction dir : Direction.values()) {
    		
    		if (check.equals(dir.getName()) || check.equals(dir.getShortName())) {
    			
    			Area<?> area = room.getArea();
    			
    			Coordinates offset = new Coordinates(room.getCoordinates(), dir.getOffset());
    			
    			Room<?> destination = area.getRoom(offset);
    			
    			if (destination != null) {
    				
    				cn.println("Existing room to the " + dir.getName() + ".");
    				return;
    				
    			}
    					
    			area.newRoom(room, dir);
    			cn.println("New room created to the " + dir.getName() + ".");
    			return;
    			
    		}
    		
    	}
    	
    	cn.println("Specify a valid direction.");
    	
    }
    
    public static void findPath(String[] command, Shell shell) {
    	
    	ConnectNode cn = shell.getConnectNode();
    	
    	int Xcoor = Integer.parseInt(command[0]);
    	int Ycoor = Integer.parseInt(command[1]);
    	
    	Coordinates coors = new Coordinates(Xcoor, Ycoor);
    	
    	Room<?> room = shell.getArea().getRoom(coors);
    	
    	if (room == null) {
    		
    		cn.println("No path found.");
    		return;
    		
    	}
    	
    	ArrayList<Direction> path = GameFunction.findPath(shell, room);
    	
    	if (path == null) {
    		
    		cn.println("No path found.");
    		
    		return;
    		
    	}
    	
    	if (path.size() == 0) {
    		
    		cn.println("You're already there.");
    		
    		return;
    		
    	}
    	
    	cn.print("Path: ");
    		
	    for (int i = 0; i < path.size(); i++) {
	    		
	    	cn.print(path.get(i).getShortName());
	    		
	    	if (i + 1 < path.size()) {
	    			
	    		cn.print(", ");
	    		
	    	}
	    		
	    }
	    	
	    cn.println();
    	
    	shell.setTravelCommand(path, 0);
    	
    }
	
	public static void end (ConnectNode cn) {
		
		System.out.println("Shutting down server.");
        cn.println("Server shutting down. Have a nice day!");
		Omnimud.cleanup(cn);
		Omnimud.shutdown();
		
	}

}
