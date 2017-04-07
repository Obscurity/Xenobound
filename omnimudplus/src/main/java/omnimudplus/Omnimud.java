package omnimudplus;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Omnimud {

    public static List<Mobile> mobiles = new ArrayList<Mobile>();
    
    public static List<Entity> objects = new ArrayList<Entity>();

    public static final LockObject playerlock = new LockObject();
    
    public static List<ConnectNode> players = new ArrayList<ConnectNode>();
    
    public static final HashMap<Mobile, ConnectNode> playershells = new LinkedHashMap<Mobile, ConnectNode>();
    
    // TODO
    private static ObjectInputStream ois;
    
    // TODO
    private static ObjectOutputStream oos;
    
    public static Area areatest;
    
    public static void main(String[] args) {
    	
    	try {
    		
    		run();
    		
    	} catch (Exception e) {
    		
    		e.printStackTrace();
    		
    	}

    }

    public static void run() {
    	
        try {
        	
            // Selector stuff
            
    	    Selector selector = Selector.open();
        	System.out.println("Selector initialized: " + selector.isOpen());
        	
        	// Initial server socket stuff
        	
            ServerSocketChannel serversocket = ServerSocketChannel.open();
            InetSocketAddress serveraddress = new InetSocketAddress("192.168.201.117", 5000);
            serversocket.bind(serveraddress);
            serversocket.configureBlocking(false);
            int ops = serversocket.validOps();
            
            System.out.println("Server address " + serveraddress.getHostName() + " at port " + serveraddress.getPort() + " open: " + !serveraddress.isUnresolved());
        	
            // Registry
    	    
            serversocket.register(selector, ops);
            
            // Read in the rooms

            /* try {
            
	            FileInputStream fis = new FileInputStream("OmnimudData");
	            
	            ois = new ObjectInputStream(fis);
	            
	            int indexcheck = 0;
            
            	try {
            
            		while (true) {
	            	
            			rooms.add((Room)ois.readObject());
            			rooms.get(indexcheck).clearPlayers();
            			System.out.print("Loading rooms... (" + (indexcheck + 1) + ")\r");
            			indexcheck++;
            			
            		}
	            	
            	} catch (EOFException e) {
            		
                    ois.close();
            		
            	}
            	
            	System.out.println(Omnimud.rooms.size() + " rooms loaded from file.");
            	
            } catch (FileNotFoundException e) { 
        	
	        	System.out.println("File problem - loading from hardcode defaults.");
	
	        	rooms.add(new Room("spawn_point", "a spawning point", "This spawning point is devoid of any interesting detail.",
	        		new LinkedHashSet<Entity>(), false));
	
	        	rooms.add(new Room("north_room", "a northern room", "This room is very northerly.", new LinkedHashSet<Entity>(), false));
	
	        	rooms.add(new Room("east_room", "an eastern room", "This room is very eastern.", new LinkedHashSet<Entity>(), false));
	        	
	        	rooms.get(0).makeExit(Direction.NORTH, rooms.get(1));
	        
	        	rooms.get(0).makeExit(Direction.EAST, rooms.get(2));
	        
	        	rooms.get(1).makeExit(Direction.SOUTHEAST, rooms.get(2));
	        
	        	rooms.get(1).makeExit(Direction.SOUTH, rooms.get(0));
	        
	        	rooms.get(2).makeExit(Direction.WEST, rooms.get(0));
	        
	        	rooms.get(2).makeExit(Direction.NORTHWEST, rooms.get(1));
	        
	        	rooms.get(2).addEntity(new Entity());
	
	        	rooms.get(1).addEntity(new Container());
            	
            } */
            
            areatest = new Area();
            
            areatest.newRoom();
            
            Room origin = areatest.getOrigin();
            
            for (Direction dir : Direction.values()) {
            
	            areatest.newRoom(origin, dir);
            		
            }
            
            System.out.println("Test area generated, with surrounding rooms.");
            
            Building testbuilding = new Building();
            
            origin.setFeature(testbuilding);
            
            testbuilding.setArea(origin.getArea());
            
            Physics.populateRadialMap();
            
            System.out.println("Radial map populated.");
            
            Physics.populateSquareRootMap();
            
            System.out.println("Square root map populated.");

        	while (true) {
	        	
		        System.out.println("Waiting for select...");
		        	
		        int readyChannels = selector.select();
		        
		        if (readyChannels == 0) { continue; }
		        
		        System.out.println("Number of selected keys: " + readyChannels);
		            
		        Set<SelectionKey> keys = selector.selectedKeys();
		        Iterator<SelectionKey> selectorIterator = keys.iterator();
		        
		        while (selectorIterator.hasNext()) {
		        	
		        	SelectionKey key = selectorIterator.next();
		        	selectorIterator.remove();
		        	
		        	try {
		        	
			        	if (key.isAcceptable() && key.isValid()) {
			        		
			        		SocketChannel client = serversocket.accept();
			        		
			        		client.configureBlocking(false);
			        		
			        		/* String[] aliases = {"person", "player"};
			        		
			        		Tile spawnpoint = null;
			        		
			        		for (Tile[] l1 : grid.getTiles()) {
			        			
			        			for (Tile l2 : l1) {
			        				
			        				if (l2.getMobile() == null) {
			        					
			        					System.out.println("Spawnpoint selected: " + l2.getCoordinates().toString());
			        					
			        					spawnpoint = l2;
			        					
			        					break;
			        					
			        				}
			        				
			        			}
			        			
			        			if (spawnpoint != null) {
			        				
			        				break;
			        				
			        			}
			        			
			        		}
			        		
			        		Mobile shell = new Mobile(null, "guest", aliases, "a guest", "A guest stands here.", "This guest is unremarkable.", '@', 160,
			        				Material.FLESH, 1, 1, 1, 1, 10);
			        		
			        		shell.setConnectNode(cn);
			        		
			        		*/
			        		
			        		ConnectNode cn = new ConnectNode(client);
			        		
			        		client.register(selector, SelectionKey.OP_READ, cn);
			        		
			        		System.out.println("Connection received from guest: " + client);
			        		
			        		cn.println(GameFunction.titleSplash());
			        		
			        		cn.println(GameFunction.connectOptions());
			        		
			        		cn.putPrompt();
			        		
			        		synchronized (playerlock) {
			        		
			        			players.add(cn);
			        		
			        		}
			        		
			        		// cn.println("You have connected to Omnimud!\n");
			        		
			        		// Utilities.execute(new WorkerRunnable(cn, "look"));
			        		
			        		// GameFunction.locationMsg(spawnpoint, shell.getName() + " has connected.", cn);
			        		
			        		// spawnpoint = null;
			        		
			        	} else if (key.isReadable() && key.isValid()) {
			        		
			        		SocketChannel client = (SocketChannel)key.channel();
			        		
			        		ByteBuffer buffer = ByteBuffer.allocate(2048);
			        		
			        		try {
			        		
			        			client.read(buffer);
			        			
			        		} catch (IOException e) {
			        			
			        			System.out.println("Read error - cleaning up key.");
			        			ConnectNode temp = (ConnectNode)key.attachment();
			        			temp.cleanup();
			        			continue;
			        			
			        		}
			        		
			        		String result = new String(buffer.array()).trim();
			        		
			        		String[] commands = result.split("\\n");
			        		
			        		ConnectNode data = (ConnectNode)key.attachment();
			        		
			        		for (String s : commands) {
			        				
			        			try {
			        			
			        				Utilities.execute(new WorkerRunnable(data, s));
			        				
			        			} catch (RejectedExecutionException e) {
			        				
			        				e.printStackTrace();
			        				
			        			}
					        
			        		}
			        		
			        	}
		        	
		        	} catch (CancelledKeyException e) {
		        		
		        		System.out.println("Key canceled.");
		        		ConnectNode temp = (ConnectNode)key.attachment();
		        		
		        		temp.cleanup();
		        		
		        	}
		        	
		        }
		        
        	}
        	
        } catch (Exception e) {
        
        	e.printStackTrace();
        	
        }
        
    }
        
    public static void shutdown() {
        	
        try {
        
	        try {
	        	
	        	synchronized (playerlock) {
	
			        for (ConnectNode cn : players) {
			
			            System.out.println("Killing player connection (" + cn.getShell().getName() + ")");
			
			            cn.println("Server shutting down. Have a nice day!");
			
			            cn.cleanup();
			
			        }
		        
	        	}
	        
	        } catch (Exception e) {
	        	
	        	e.printStackTrace();
	        	
	        }
	        
	        /* try {
	        
	        	FileOutputStream fos = new FileOutputStream("OmnimudData", false);
	        
	        	oos = new ObjectOutputStream(fos);
	        	
	        	int indexcheck = 0;
	        	
	        	while(indexcheck < rooms.size()) {
	        	
	            	System.out.print("\rSaving rooms... (" + (indexcheck + 1) + ")");
	        		rooms.get(indexcheck).clearPlayer();
	        		oos.writeObject(rooms.get(indexcheck++));
	        		
	        	}
	        	
	        	oos.close();
	        	
	        	System.out.println("\n" + Omnimud.rooms.size() + " rooms written to file.");
	        	
	        } catch (Exception e) {
	        	
	        	e.printStackTrace();
	        	
	        } */
        
	        System.out.println("Closing down. Have a nice day!");
	        
	        System.exit(0);
        
        } catch (Exception e) {
        	
        	e.printStackTrace();
        	
        }

    }
   
}