package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import omnimudplus.Entities.Entity;
import omnimudplus.Entities.Mobile;

public class Area implements Serializable {
	
	protected String name;
	
	protected String longName;
	
	protected String description;
	
	protected LinkedList<Room> rooms = new LinkedList<Room>();
	
	protected Room origin;
	static final private long serialVersionUID = 1L;
	
	protected static final LockObject stringLock = new LockObject();
	
	protected String areaDesc;
	
	protected static final LockObject mobileLock = new LockObject();
	
	protected LinkedHashSet<Mobile> mobiles = new LinkedHashSet<Mobile>();

	protected static final LockObject contentLock = new LockObject();

	protected LinkedHashSet<Entity> contents = new LinkedHashSet<Entity>();
	
	public void setContents(LinkedHashSet<Entity> contents) {

		synchronized (contentLock) {

			this.contents = contents;

		}

	}

	public LinkedHashSet<Entity> getContents() {

		synchronized (contentLock) {

			return contents;

		}

	}

	public void addEntity(Entity entity) {

		synchronized (contentLock) {

			contents.add(entity);

		}

	}

	public void removeEntity(Entity entity) {

		synchronized (contentLock) {

			contents.remove(entity);

		}

	}

	public LinkedHashSet<Mobile> getMobiles() {

		synchronized (mobileLock) {

			return mobiles;

		}

	}
	
	public void addMobile(Mobile mobile) {

		synchronized (mobileLock) {

			mobiles.add(mobile);

		}

	}

	public void removeMobile(Mobile mobile) {

		synchronized (mobileLock) {

			mobiles.remove(mobile);

		}

	}
	
	public String getAreaDesc() {
		
		return areaDesc;
		
	}
	
	public void setAreaDesc(String areaDesc) {
		
		this.areaDesc = areaDesc;
		
	}
	
	public Area() {
		
		this.name = "uninitialized";
		this.longName = "An uninitialized area.";
		
	}
	
	public Area(String name, String longName) {
		
		this.name = name;
		this.longName = longName;
		
	}
	
	public void newRoom() {
		
		Room room = new Room();
		
		if (rooms.size() == 0) {
		
			rooms.add(room);
			origin = room;
			origin.coors = new Coordinates(0, 0);
		
		} else {
			
			rooms.add(room);
			
		}
		
		room.setArea(this);
		
	}
	
	public void newRoom(Room start, Direction dir) {
		
		// First block - make the room at the appropriate coordinates.
		
		Coordinates newCoors = new Coordinates(start.getCoordinates(), dir.getOffset());
		
		Room room = new Room(this, newCoors);
		
		room.setArea(this);
		
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
	
	public void setOrigin(Room origin) {
		
		this.origin = origin;
		
	}
	
	public Room getOrigin() {
		
		return origin;
		
	}
	
	public Room getRoom(Coordinates coors) {
		
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
		
		Room location = mobile.getRoom();
		
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
				
				Room targetroom = getRoom(targetcoors);
				
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
	
	public Room getDestination(Mobile mobile, Direction moveDir) {
		
		Room start = getRoom(mobile.getCoordinates());
		
		Exit test = start.getExit(moveDir);
		
		return test.getDestination();
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
