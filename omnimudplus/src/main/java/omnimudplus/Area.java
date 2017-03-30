package omnimudplus;

import java.util.LinkedList;

public class Area extends Zone {
	
	private String name;
	
	private String longName;
	
	private LinkedList<Room> rooms = new LinkedList<Room>();
	
	private Room origin;
	
	static final private long serialVersionUID = 1L;
	
	public Area() {
		
		this.name = "uninitialized";
		this.longName = "An uninitialized area.";
		
	}
	
	public Area(String name, String longName) {
		
		this.name = name;
		this.longName = longName;
		
	}
	
	public void newRoom() {
		
		Room room = new Room(this, null);
		
		rooms.add(room);
		
		if (rooms.size() == 1) {
			
			origin = room;
			origin.coors = new Coordinates(0, 0);
			
		}
		
	}
	
	public void newRoom(Room start, Direction dir) {
		
		// First block - make the room at the appropriate coordinates.
		
		Coordinates newCoors = new Coordinates(start.getCoordinates(), dir.getOffset());
		
		Room room = new Room(this, newCoors);
		
		rooms.add(room);
		
		// Second block - link up the room.
		
		start.setExit(dir, room);
		
		room.setExit(dir.returnOpposite(), start);
		
	}
	
	public int getSize() {
		
		return rooms.size();
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public String getLongName() {
		
		return longName;
		
	}
	
	public void setName(String name) {
		
		this.name = name;
		
	}
	
	public void setLongName(String longName) {
		
		this.longName = longName;
		
	}
	
	public void addRoom(Room room) {
		
		rooms.add(room);
		
	}
	
	public void removeRoom(Room room) {
		
		rooms.remove(room);
		
	}
	
	public LinkedList<Room> getRooms() {
		
		return rooms;
		
	}
	
	public Room getOrigin() {
		
		return origin;
		
	}
	
	public Room getLocation(Coordinates coors) {
		
		for (Room room : rooms) {
			
			if (room.getCoordinates().equals(coors)) {
				
				return room;
				
			}
			
		}
		
		return null;
		
	}
	
	public String getMapString(Mobile mobile, int radius) {
		
		Coordinates center = mobile.getCoordinates();
		
		System.out.println();
		
		String[][] map = new String[radius*4 + 1][radius*4 + 1];
		
		StringBuilder sb = new StringBuilder();
		
		Room location = (Room)mobile.getLocation();
		
		ConnectNode cn = mobile.getConnectNode();
		
		sb.append(location.getTerrain().getColor().getHighCode());
		sb.append(location.getBriefDesc());
		sb.append(" ");
		sb.append(cn.getColorScheme().getForeground());
		sb.append(center);
		sb.append("\n");
		
		for (int i = -radius; i <= radius; i++) {
			
			for (int j = radius; j >= -radius; j--) {
				
				Coordinates targetcoors = new Coordinates(center.getX() + i, center.getY() - j, center.getZ());
				
				Room targetroom = (Room)getLocation(targetcoors);
				
				int mapX = radius*2 + i*2;
				int mapY = radius*2 + j*2;
				
				System.out.println(targetcoors.getX() + ", " + targetcoors.getY() + " are going into " + mapY + ", " + mapX);
				
				if (targetroom == null) {
					
					if (map[mapY][mapX] == null) {
					
						map[mapY][mapX] = " ";
					
					}
					
					continue;
					
				} else if (targetcoors.equals(center)) {
					
					map[mapY][mapX] = Color.WHITE.getHighCode() + "*";
					
				} else {
				
					map[mapY][mapX] = targetroom.getTerrain().getColor().getHighCode() + "#";
					
				}
				
				for (Direction dir : Direction.values()) {
					
					if (dir.equals(Direction.UP) || dir.equals(Direction.DOWN)) {
						continue;
					}
					
					Vector vector = dir.getOffset();
					
					int offsetX = mapX + vector.getXoffset();
					int offsetY = mapY - vector.getYoffset();
					
					if ((offsetX < 0 || offsetY < 0) || (offsetX >= radius*4 + 1 || offsetY >= radius*4 + 1))  {
						continue;
					}
					
					if (targetroom.getExit(dir) != null) {
					
						if (map[offsetY][offsetX] == null || map[offsetY][offsetX].equals(" ")) {
						
							map[offsetY][offsetX] = Color.WHITE.getHighCode() + dir.getMapDir();
							
						} else if (map[offsetY][offsetX].contains("\\") && dir.getMapDir() != '\\') {
							
							map[offsetY][offsetX] = Color.WHITE.getHighCode() + "x";
							
						} else if (map[offsetY][offsetX].contains("/") && dir.getMapDir() != '/') {
							
							map[offsetY][offsetX] = Color.WHITE.getHighCode() + "x";
							
						}
						
					}
					
				}
				
			}
			
		}
		
		for (int i = 0; i < radius*4 + 1; i++) {
			
			for (int j = 0; j < radius*4 + 1; j++) {
				
				if (map[i][j] == null) {
				
					sb.append(" ");
					
				} else {
					
					sb.append(map[i][j]);
				
				}
				
			}
			
			sb.append("\n");
			
		}
		
		return sb.toString();
		
	}
	
	public Location getDestination(Mobile mobile, Direction moveDir) {
		
		Room start = getLocation(mobile.getCoordinates());
		
		Exit test = start.getExit(moveDir);
		
		return test.getDestination();
		
	}
	
}
