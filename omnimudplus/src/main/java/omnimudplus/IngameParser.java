package omnimudplus;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

public class IngameParser {

	public static void parse(String[] command, ConnectNode cn) {
		
		Mobile mobile = cn.getShell();
		
		
		
		System.out.print(mobile.getName() + ":");
		
		for (String s : command) {
			
			System.out.print(" " + s);
			
		}
		
		System.out.println();

		for (Direction test : Direction.values()) {
			
			if (test.getName().equals(command[0].toLowerCase()) || test.getShortName().equals(command[0].toLowerCase())) {
				
				move(mobile, test);
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
		case "findpath": findPath(fromIndex(command, 1), mobile); break;
		case "stop": stop(mobile); break;
		case "look": case "l": if (command.length <= 1) { look(mobile); } else { look(command, mobile); } break;
		case "map": cn.print(mobile.getZone().getMapString(mobile, mobile.getPerception())); break;
		case "status": case "stat": status(cn); break;
		case "quit": case "qq": quit(cn); break;
		case "say": case "'": case "sing": say(command, mobile); break;
		case "i": case "inv": case "inventory": inventory(cn); break;
		case "get": case "take": if (command.length > 1) {
													get(command, mobile);
												} else {
													if (cn != null) { cn.println(command[0].substring(0, 1).toUpperCase() + command[0].substring(1) + " what?"); };
												}
												break;
		case "drop": if (command.length > 1) { drop(command, mobile); } else { if (cn != null) { cn.println("Drop what?"); } } break;
		case "put": if (command.length > 1) { put(command, mobile); } else { if (cn != null) { cn.println("Put what in what?"); } } break;
		case "go": if (command.length > 1) { go(fromIndex(command, 1), cn); } else { if (cn != null) { cn.println("Go where?"); } } break;
		case "who": who(cn); break;
		case "colors": if (command.length > 1) { colors(fromIndex(command, 1), cn); } else { colors(cn); } break;
		case "end": end(cn); break;
		case "perceptionset": if (command.length > 1) { mobile.setPerception(Integer.parseInt(command[1])); } else { cn.println("Set perception to what?"); }; break;
		case "newroom": if (command.length > 1) { newRoom(fromIndex(command, 1), mobile); } else { cn.println("Create a new room where?"); } break;
		case "punch": if (command.length > 1) { attack(mobile, fromIndex(command, 1), Attack.PUNCH); } else { if (cn != null) { cn.println("Punch what?"); } } break;
		case "fireball": if (command.length > 1) { attack(mobile, fromIndex(command, 1), Attack.FIREBALL); } else { if (cn != null) { cn.println("Cast fireball at what?"); } }; break;
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

	public static void move(Mobile mobile, Direction moveDir) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		if (!mobile.hasMoveBalance()) {
			
			cn.println("You can't move that quickly.");
			return;
			
		}
		
		GameFunction.autoMove(moveDir, mobile);

	}
	
	public static void stop(Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		if (cn.getShell().isTraveling()) {
			
			cn.getShell().travelStop();
			cn.println("You stop traveling.");
			
		}
		
	}

	public static void look(Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		if (cn == null) {
			return;
		}

		cn.print(mobile.getLocation().printRoom(mobile));

	}

	public static void quit(ConnectNode cn) {

		Mobile mobile = cn.getShell();
		
		GameFunction.locationMsg(mobile.getLocation(), mobile.getName() + " has disconnected.", cn);

		cn.println("You have disconnected.");
		
		cn.cleanup();

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

	public static void say(String[] words, Mobile mobile) {

		ConnectNode cn = mobile.getConnectNode();
		
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

			LinkedHashSet<Mobile> mobiles = null;
			
			Location location = mobile.getLocation();
			
			if (location instanceof Room) {
				
				mobiles = ((Room)location).getMobiles();
				
			}
			
			StringBuilder builder = new StringBuilder();

			builder.append(mobile.getName());
			builder.append(" ");
			builder.append(verb);
			builder.append("s, \"");
			builder.append(speech);
			builder.append("\"");
			
			String output = builder.toString();
					
			for (Mobile other : mobiles) {
					
				ConnectNode oc = other.getConnectNode();
					
				if (oc == cn || oc == null) {
					continue;
				}
					
				oc.println(oc.getColorScheme().getSpeech() + output);
				oc.putPrompt();
						
			}
				
			if (cn != null) {
				
				cn.println(cn.getColorScheme().getSpeech() + "You " + verb + ", \"" + speech + "\"" + cn.getColorScheme().getForeground());
				
			}

		} else {
			
			if (cn != null) {

				cn.println("You exercise your right to remain silent.");

			}
			
		}

	}

	public static void look (String[] input, Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		if (cn == null) {
			return;
		}
		
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
			
			if (!query.equals(reference.getName())) {
			
				cn.println("(looking at " + reference.getName() + ")");
			
			}
			
			cn.println(reference.getLongDesc());
			
			if (reference instanceof Container) {
				
				Container container = (Container)reference;
				
				if (container.getContents().size() > 0) {
				
					cn.print("It is currently holding ");
				
					int items = 0;
				
					for (Entity object : container.getContents()) {
					
						items++;
					
						cn.print(object.getShortDesc());
				
						if (items <= container.getContents().size() - 2) {
				
							cn.print(", ");
					
						} else if (items == container.getContents().size() - 1) {
						
							cn.print(", and ");
						
						}
				
					}
			
					cn.println(".");
					
				}
				
			}
			
		}

	}
	
	public static void status (ConnectNode cn) {
		
		cn.println("Strength     : " + cn.getShell().getStrength());
		
		cn.println("Dexterity    : " + cn.getShell().getDexterity());
		
		cn.println("Constitution : " + cn.getShell().getConstitution());
		
		cn.println("Willpower    : " + cn.getShell().getWillpower());
		
		cn.println("Perception   : " + cn.getShell().getPerception());
		
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
		
		if (cn.getShell().getInventory().size() > 0) {
		
			int items = 0;
			
			cn.println("You are carrying:");
		
			for (Entity object : cn.getShell().getInventory()) {
			
				items++;
				
				cn.print(object.getShortDesc());
			
				if (items <= cn.getShell().getInventory().size() - 2) {
			
					cn.print(", ");
				
				} else if (items == cn.getShell().getInventory().size() - 1) {
					
					cn.print(", and ");
					
				}
			
			}
		
			cn.println(".");
			
			int weightCarried = cn.getShell().getWeightCarried();
			
			int pounds = weightCarried / 16;
			
			int ounces = weightCarried % 16;
			
			if (ounces == 0) {
			
				cn.println("You have " + cn.getShell().getInventory().size() + " items, weighing " + pounds + " pounds.");
				
			} else {
				
				cn.println("You have " + cn.getShell().getInventory().size() + " items, weighing " + pounds + " pounds and " + ounces + " ounces.");
				
			}
		
		} else {
			
			cn.println("You are carrying nothing.");
			
		}
		
	}
	
	public static void get (String[] input, Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		String query = null;
		String indirect = null;
		Container container = null;
		
		if (input.length < 4) {
		
			query = input[1].toLowerCase();
		
		} else {
			
			query = input[1].toLowerCase();
			
			if (input[2].toLowerCase().equals("from")) {
				
				indirect = input[3].toLowerCase();
				
				container = (Container)GameFunction.findFromInventory(indirect, cn);
				
				container = container == null ? (Container)GameFunction.findFromReference(indirect, cn) : container;
				
			}
			
		}
		
		Entity reference = container == null ? GameFunction.findFromReference(query, cn) : GameFunction.findFromContainer(query, container, cn);
		
		if (reference == null) {
			
			if (container == null) {
			
				if (cn != null) {
				
					cn.println("You see no '" + query + "' here.");
				
				}
			
			} else {
				
				if (cn != null) {
				
					cn.println("You see no '" + query + "' inside " + container.getShortDesc() + ".");
					
				}
				
			}
			
		} else if (cn.getShell().getInventory().contains(reference)) {
			
			if (cn != null) {
			
				cn.println("You're already holding " + reference.getShortDesc() + "!");
			
			}
			
		} else if (cn.getShell() == reference) {
			
			if (cn != null) {
			
				cn.println("No matter how enamored, you are incapable of picking yourself up.");
			
			}
			
		} else {
			
			if (container == null) {
			
				if (!query.equals(reference.getName())) {
					
					if (cn != null) {
				
						cn.println("(picking up " + reference.getName() + ")");
					
					}
				
				}
				
				if (cn != null) {
			
					cn.println("You pick up " + reference.getShortDesc() + ".");
				
				}
				
				GameFunction.locationMsg(mobile.getLocation(), mobile.getName() + " picks up " + reference.getShortDesc() + ".", cn);
			
				mobile.getLocation().removeEntity(reference);
			
				mobile.addEntity(reference);
			
			} else {
				
				if (!query.equals(reference.getName()) || !indirect.equals(container.getName())) {
					
					if (cn != null) {
					
						cn.println("(removing " + reference.getName() + " from " + container.getName() + ")");
					
					}
					
				}
				
				if (cn != null) {
				
					cn.println("You remove " + reference.getShortDesc() + " from " + container.getShortDesc() + ".");
				
				}
				
				GameFunction.locationMsg(mobile.getLocation(), mobile.getName() + " removes " + reference.getShortDesc() + " from " + container.getShortDesc() + ".", cn);
				
				container.removeEntity(reference);
				
				mobile.addEntity(reference);
				
			}
			
		}
		
	}
	
	public static void drop (String[] input, Mobile mobile) {
		
		ConnectNode cn = mobile.getConnectNode();
		
		String query = input[1].toLowerCase();
		
		Entity reference = GameFunction.findFromInventory(query, cn);
		
		if (reference == null) {
			
			if (cn != null) {
			
				cn.println("You're not holding a '" + query + "'.");
				
			}
			
		} else {
			
			if (!query.equals(reference.getName())) {
				
				if (cn != null) {
				
					cn.println("(dropping " + reference.getName() + ")");
				
				}
				
			}
			
			if (cn != null) {
			
				cn.println("You drop " + reference.getShortDesc() + ".");
			
			}
			
			Location location = mobile.getLocation();
			
			GameFunction.locationMsg(location, mobile.getShortDesc() + " drops " + reference.getShortDesc() + ".", cn);
			
			location.addEntity(reference);
			
			location.removeEntity(reference);
			
		}
		
	}
	
	public static void put(String[] input, Mobile mobile) {

		ConnectNode cn = mobile.getConnectNode();
		
		String query = null;
		String indirect = null;
		Entity container1 = null;
		Container container2 = null;

		if (input.length < 4) {
			
			if (cn != null) {

				cn.println("Syntax: PUT <item> IN <container>.");
			
			}
			
			return;

		} else {

			query = input[1].toLowerCase();

			if (input[2].toLowerCase().equals("in") || input[2].toLowerCase().equals("inside") || input[2].toLowerCase().equals("into")) {

				indirect = input[3].toLowerCase();

				container1 = GameFunction.findFromInventory(indirect, cn);

				container1 = container1 == null ? GameFunction.findFromReference(indirect, cn) : container1;

			}

		}

		Entity object = GameFunction.findFromInventory(query, cn);

		if (object == null) {

			if (cn != null) {
			
				cn.println("You have no '" + query + "'.");
			
			}
			
			return;

		}
		
		if (container1 == null) {

			if (cn != null) {
			
				cn.println("You see no '" + indirect + "' here.");
			
			}
					
			return;

		}
		
		if (!(container1 instanceof Container)) {
			
			if (cn != null) {
			
				cn.println("You cannot put anything into " + container1.getShortDesc() + ".");
			
			}
			
			return;
			
		} else {
			
			container2 = (Container)container1;
			
		}
		
		if (object == container2) {
			
			if (cn != null) {
			
				cn.println("Unfortunately, this endeavor is constrained by Euclidean geometry.");
			
			}
			
			return;
			
		}

		if (container2.getTypes() != null) {

			if (!container2.getTypes()[0].equals("any")) {

				boolean found = false;

				for (String ref : object.getReferences()) {

					for (String q : container2.getTypes()) {

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
					
					if (cn != null) {

						cn.println("You find that " + container2.getShortDesc() + " cannot hold " + object.getShortDesc() + ".");
					
					}
					
					return;

				}

			}

		}

		if ((container2.getWeightCarried() + object.getWeight()) > container2.getMaxWeight()) {

			if (cn != null) {
			
				cn.println("It seems that " + container2.getShortDesc() + " cannot hold any more weight.");
			
			}
			
			return;

		} else {

			if (cn != null) {
			
				cn.println("You put " + object.getShortDesc() + " into " + container2.getShortDesc() + ".");
			
			}
			
			GameFunction.locationMsg(mobile.getLocation(), mobile.getShortDesc() + " puts " + object.getShortDesc() + " into " + container2.getShortDesc() + ".", cn);
			mobile.removeEntity(object);
			container2.addEntity(object);

		}


	}
	
	public static void who(ConnectNode cn) {
		
		synchronized (Omnimud.playerlock) {
		
			cn.println("Currently connected:");
			
			for (ConnectNode node : Omnimud.players) {
				
				cn.println(node.getShell().getName());
				
			}
			
			cn.println(Omnimud.players.size() + " players are connected.");
		
		}
		
	}
	
	public static void go(String[] input, ConnectNode cn) {
		
		Mobile mobile = cn.getShell();
		
		int Xcoor = Integer.parseInt(input[0]);
		int Ycoor = Integer.parseInt(input[1]);
		
		Coordinates coors = new Coordinates(Xcoor, Ycoor);
		
		Location destination = mobile.getZone().getLocation(coors);
		
		if (destination != null) {
			
			GameFunction.locationMsg(mobile.getLocation(), mobile.getName() + " leaves to the ether.", cn);

			mobile.setLocation(destination);

			GameFunction.locationMsg(mobile.getLocation(), mobile.getName() + " arrives from the ether.", cn);

			look(mobile);
			
		} else {
			
			cn.println("Invalid destination specified.");
			
		}
		
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
    
    public static void attack(Mobile mobile, String[] input, Attack attack) {
    	
    	ConnectNode cn = mobile.getConnectNode();
    	
	    Entity target = GameFunction.findFromReference(input[0], cn);
	    	
	    if (target != null) {
	    	
	    	if (target instanceof Mobile) {
	    			
	    		attack.attackUse(mobile, (Mobile)target);
	    		return;
	    		
	    	} else {
	    		
	    		if (cn != null) {
	    			
	    			cn.println("You can't attack that.");
	    		
	    		}
	    		
	    		return;
	    			
	    	}
	    	
	    } else {
	    		
	    	if (cn != null) {
	    	
	    		cn.println("You can't see that here.");
	    	
	    	}
	    	
	    	return;
	    		
	    }
    	
    }
    
    public static void newRoom(String[] command, Mobile mobile) {
    	
    	ConnectNode cn = mobile.getConnectNode();
    	
    	if (!(mobile.getLocation() instanceof Room)) {
    		
    		cn.println("You're not standing in a room.");
    		return;
    		
    	}
    	
    	Room cast = (Room)mobile.getLocation();
    	
    	String check = command[0].toLowerCase();
    	
    	for (Direction dir : Direction.values()) {
    		
    		if (check.equals(dir.getName()) || check.equals(dir.getShortName())) {
    			
    			Area area = cast.getZone();
    			
    			Exit temp = cast.getExit(dir);
    			
    			Location dest = area.getLocation(new Coordinates(mobile.getCoordinates(), dir.getOffset()));
    			
    			if (temp != null) {
    				
    				if (temp.getDestination() instanceof Room) {
    				
    					cn.println("Existing room to the " + dir.getName() + ".");
    					return;
    				
    				}
    				
    			} else if (dest != null) {
    				
					cn.println("Existing room to the " + dir.getName() + ".");
					return;
    				
    			}
    					
    			area.newRoom(cast, dir);
    			cn.println("New room created to the " + dir.getName() + ".");
    			return;
    			
    		}
    		
    	}
    	
    	cn.println("Specify a valid direction.");
    	
    }
    
    public static void findPath(String[] command, Mobile mobile) {
    	
    	ConnectNode cn = mobile.getConnectNode();
    	
    	int Xcoor = Integer.parseInt(command[0]);
    	int Ycoor = Integer.parseInt(command[1]);
    	
    	Coordinates coors = new Coordinates(Xcoor, Ycoor);
    	
    	Location test = mobile.getZone().getLocation(coors);
    	
    	if (test == null) {
    		
    		cn.println("No path found.");
    		return;
    		
    	}
    	
    	ArrayList<Direction> path = GameFunction.findPath(mobile, test);
    	
    	if (path == null) {
    		
    		if (cn != null) {
    		
    			cn.println("No path found.");
    		
    		}
    		
    		return;
    		
    	}
    	
    	if (path.size() == 0) {
    		
    		if (cn != null) {
    		
    			cn.println("You're already there.");
    		
    		}
    		
    		return;
    		
    	}
    	
    	if (cn != null) {
    	
    		cn.print("Path: ");
    		
	    	for (int i = 0; i < path.size(); i++) {
	    		
	    		cn.print(path.get(i).getShortName());
	    		
	    		if (i + 1 < path.size()) {
	    			
	    			cn.print(", ");
	    			
	    		}
	    		
	    	}
	    	
	    	cn.println();
    	
    	}
    	
    	mobile.setTravelCommand(path, 0);
    	
    }
	
	public static void end (ConnectNode cn) {
		
		System.out.println("Shutting down server.");
        cn.println("Server shutting down. Have a nice day!");
		cn.cleanup();
		Omnimud.shutdown();
		
	}

}
