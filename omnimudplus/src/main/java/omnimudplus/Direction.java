package omnimudplus;

public enum Direction {
   
      NORTH("n", "north", "south", '|', new Vector(0, 1)),
      NORTHEAST("ne", "northeast", "southwest", '/', new Vector(1, 1)),
      EAST("e", "east", "west", '-', new Vector(1, 0)),
      SOUTHEAST("se", "southeast", "northwest", '\\', new Vector(1, -1)),
      SOUTH("s", "south", "north", '|', new Vector(0, -1)),
      SOUTHWEST("sw", "southwest", "northeast", '/', new Vector(-1, -1)), 
      WEST("w", "west", "east", '-', new Vector(-1, 0)),
      NORTHWEST("nw", "northwest", "southeast", '\\', new Vector(-1, 1)),
      UP("u", "up", "down", '^', new Vector(0, 0, 1)),
      DOWN("d", "down", "up", 'v', new Vector(0, 0, -1));
      
      private String shortname;
      private String name;
      private String opposite;
      private char mapdir;
      private Vector offset;
      
      private Direction(String shortname, String name, String opposite, char mapdir, Vector offset) {
      
         this.shortname = shortname;
         this.name = name;
         this.opposite = opposite;
         this.mapdir = mapdir;
         this.offset = offset;
      
      }
      
      public String getName() {
      
         return name;
      
      }
      
      public String getShortName() {
      
         return shortname;
      
      }
      
      public String getOpposite() {
      
         return opposite;
      
      }
      
      public char getMapDir() {
    	  
    	  return mapdir;
    	  
      }
      
      public Vector getOffset() {
    	  
    	  return offset;
    	  
      }
      
      public static Direction getDirByAngle(double angle) {
			
    	  double testangle = 22.5;
			
    	  for (Direction dir : Direction.values()) {
				
    		  if (testangle > 360) {
    			  
    			  return Direction.NORTH;
    			  
    		  }
				
    		  if (angle <= testangle && angle > testangle - 45) {
    			  
    			  return dir;
    			  
    		  }
				
    		  testangle += 45;
				
    	  }
			
    	  return null;
			
      }
      
      public Direction returnDir(String input) {
    	  
    	  for (Direction dir : Direction.values()) {
    		  
    		  if (dir.getName().equals(input) || dir.getShortName().equals(input)) {
    			  
    			  return dir;
    			  
    		  }
    		  
    	  }
    	  
    	  return null;
    	  
      }
      
      public Direction returnOpposite() {
    	  
    	  for (Direction dir : Direction.values()) {
    		  
    		  if (dir.getOpposite().equals(name)) {
    			  
    			  return dir;
    			  
    		  }
    		  
    	  }
    	  
    	  return null;
    	  
      }
      
      public boolean isAdjacentTo(Direction dir) {
    	  
    	  // Ups and downs can't be adjacent. We're only worried about compass directions.
    	  
    	  if (this == UP || this == DOWN || dir == DOWN || dir == UP) {
    		  return false;
    	  }
    	  
    	  // Obviously, this counts as adjacent.
    	  
    	  if (this == dir) {
    		  return true;
    	  }
    	  
    	  // Now - hardcoded if block.
    	  
    	  if (this == NORTH) {
    		  
    		  if (dir == NORTHWEST || dir == NORTHEAST) {
    			  return true;
    		  }
    		  
    	  } else if (this == NORTHEAST) {
    		  
    		  if (dir == NORTH || dir == EAST) {
    			  return true;
    		  }
    		  
    	  } else if (this == EAST) {
    		  
    		  if (dir == NORTHEAST || dir == SOUTHEAST) {
    			  return true;
    		  }
    		  
    	  } else if (this == SOUTHEAST) {
    		  
    		  if (dir == EAST || dir == SOUTH) {
    			  return true;
    		  }
    		  
    	  } else if (this == SOUTH) {
    		  
    		  if (dir == SOUTHEAST || dir == SOUTHWEST) {
    			  return true;
    		  }
    		  
    	  } else if (this == SOUTHWEST) {
    		  
    		  if (dir == SOUTH || dir == WEST) {
    			  return true;
    		  }
    		  
    	  } else if (this == WEST) {
    		  
    		  if (dir == SOUTHWEST || dir == NORTHWEST) {
    			  return true;
    		  }
    		  
    	  } else if (this == NORTHWEST) {
    		  
    		  if (dir == NORTH || dir == WEST) {
    			  return true;
    		  }
    		  
    	  }
    	  
    	  return false;
    	  
      }

   }
