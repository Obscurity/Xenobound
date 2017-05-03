package omnimudplus.Entities;

import java.util.LinkedList;

import omnimudplus.BuildingArea;
import omnimudplus.Color;
import omnimudplus.ConnectNode;
import omnimudplus.Coordinates;
import omnimudplus.Direction;
import omnimudplus.Exit;
import omnimudplus.Material;
import omnimudplus.Room;
import omnimudplus.Vector;

public class Building extends Feature {
	
	// Buildings are features that can be entered.
	
	static final private long serialVersionUID = 1L;
	
	private BuildingArea buildingArea;

	public void setOrigin(Room origin) {
		buildingArea.setOrigin(origin);
	}
	
	public Building() {
		
		super("building", new String[] {"object", "thing"}, "a building", "An uninitialized building stands here.", "This building is not very well initialized, is it?", 0, Material.UNDEFINED);
		
		buildingArea = new BuildingArea(this);
		
		newRoom();
		
	}
	
	public Building(Room room) {
		
		super("building", new String[] {"object", "thing"}, "a building", "An uninitialized building stands here.", "This building is not very well initialized, is it?", 0, Material.UNDEFINED);
		
		buildingArea = new BuildingArea(this);
		
		this.setParentRoom(room);
		
		newRoom();
		
	}
	
	public void newRoom() {
		
		buildingArea.newRoom();
		
	}
	
	public void newRoom(Room start, Direction dir) {
		
		buildingArea.newRoom(start, dir);
		
	}
	
	public int getSize() {
		
		return buildingArea.getRooms().size();
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public void setName(String name) {
		
		this.name = name;
		
	}
	
	public void addRoom(Room room) {
		
		LinkedList<Room> rooms = buildingArea.getRooms();
		
		rooms.add(room);
		
	}
	
	public void removeRoom(Room room) {
		
		LinkedList<Room> rooms = buildingArea.getRooms();
		
		rooms.remove(room);
		
	}
	
	public LinkedList<Room> getRooms() {
		
		LinkedList<Room> rooms = buildingArea.getRooms();
		
		return rooms;
		
	}
	
	public Room getOrigin() {
		
		Room origin = buildingArea.getOrigin();
		
		System.out.println(origin);
		
		return origin;
		
	}
	
	public Room getRoom(Coordinates coors) {
		
		LinkedList<Room> rooms = buildingArea.getRooms();
		
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
		
		Room room = mobile.getRoom();
		
		ConnectNode cn = mobile.getConnectNode();
		
		sb.append(room.getTerrain().getColor().getHighCode());
		sb.append(room.getBriefDesc());
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

}
