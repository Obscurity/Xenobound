package omnimudplus;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;

public class TravelRunnable implements Runnable {
	
	private Mobile mobile = null;
	private Direction dir = null;
	private Coordinates coors = null;
	private int count = -1;
	private ArrayList<Direction> path = null;
	
	public TravelRunnable(Mobile mobile, Direction dir) {
		
		this.mobile = mobile;
		this.dir = dir;
		
	}
	
	public TravelRunnable(Mobile mobile, Coordinates coors) {
		
		this.mobile = mobile;
		this.coors = coors;
		
	}
	
	public TravelRunnable(Mobile mobile, Direction dir, int count) {
		
		this.mobile = mobile;
		this.dir = dir;
		this.count = count;
		
	}
	
	public TravelRunnable(Mobile mobile, Coordinates coors, int count) {
		
		this.mobile = mobile;
		this.coors = coors;
		this.count = count;
		
	}
	
	public TravelRunnable(Mobile mobile, ArrayList<Direction> path, int count) {
		
		this.mobile = mobile;
		this.path = path;
		this.count = count;
		
	}
	
	public void run() {
		
		synchronized (mobile.getTravelLock()) {
		
			ConnectNode cn = mobile.getConnectNode();
			
			try {
				
				if (path != null && count < path.size()) {
				
					GameFunction.autoMove(path.get(count), mobile);
					
					if (count + 1 < path.size()) {
					
						mobile.setTravelCommand(path, count + 1);
					
					} else {
						
						mobile.travelStop();
						
						if (cn != null) {
							
							cn.println("You stop traveling.");
							IngameParser.parse(new String[] {"look"}, cn);
							cn.putPrompt();
						
						}
						
					}
					
				} else if (dir != null) {
					
					if (count == -1) {
					
						GameFunction.autoMove(dir, mobile);
						
					} else if (count > 0) {
						
						GameFunction.autoMove(dir, mobile);
						mobile.setTravelCommand(dir, count - 1);
						
					} else {
						
						mobile.travelStop();
						
						if (cn != null) {
							
							cn.println("You stop traveling.");
							IngameParser.parse(new String[] {"look"}, cn);
							cn.putPrompt();
						
						}
						
					}
					
				} else {
					
					Zone zone = mobile.getZone();
					
					Location test = null;
					
					if (zone instanceof Area) {
						
						test = ((Area)zone).getLocation(coors);
						
					}
					
					if (mobile.getLocation() == test) {
						
						mobile.travelStop();
						
						if (cn != null) {
						
							cn.println("You stop traveling.");
							IngameParser.parse(new String[] {"look"}, cn);
							cn.putPrompt();
						
						}
						
					}
					
				}
				
			} catch (NonWritableChannelException e) {
				
				e.printStackTrace();
				
				if (cn != null) {
				
					cn.cleanup();
				
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				if (cn != null) {
				
					cn.println("You have caused a general exception (" + e.getMessage() + "). Please don't do that again.");
					cn.putPrompt();
				
				}
				
			}
		
		}
		
	}

}
