package omnimudplus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class Utilities {
	
	/* public static final CacheLoader<Coordinates, LocationLevel> loader = new CacheLoader<Coordinates, LocationLevel>() {
		public LocationLevel load(Coordinates key) {
			
			int length = Omnimud.grid.getSize();
			
			if (!(key.getX() > -1 && key.getX() < length && key.getY() > -1 && key.getY() < length)) {
				
				return null;
				
			}
			
			Location location = Omnimud.grid.getLocations()[key.getY()][key.getX()];
			
			if (key.getZ() > location.getGroundLevel()) {
				
				LocationLevel temp = new LocationLevel(location, key.getZ(), Terrain.SKY);
				
				return temp;
				
			} else if (key.getZ() < location.getGroundLevel()) {
				
				LocationLevel temp = new LocationLevel(location, key.getZ(), Terrain.EARTH);
				temp.setSolid(true);
				
				return temp;
				
			} else {
				
				LocationLevel temp = new LocationLevel(location, key.getZ(), Terrain.UNINITIALIZED);
				
				return temp;
				
			}
			
		}
	};
	
	public static RemovalListener<Coordinates, LocationLevel> removalListener = new RemovalListener<Coordinates, LocationLevel>() {
		
		public void onRemoval(RemovalNotification<Coordinates, LocationLevel> removal) {
			
			Coordinates coors = removal.getKey();
			LocationLevel temp = removal.getValue();
			Location location = temp.getLocation();
			
			System.out.println("Removal is firing! (" + coors.getX() + ", " + coors.getY() + ", " + coors.getZ() + ")");
			
			if (temp.getContents().size() == 0 && temp.getMobile() == null &&
					(temp.getTerrain().getName().equals("sky") || temp.getTerrain().getName().equals("earth"))) {
				
				location.setLevel(temp.getFieldZ(), null);
				System.out.println("NULLIFIED!");
				
			}
			
		}
	};
	
	public static final LoadingCache<Coordinates, LocationLevel> cache =
			CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterAccess(10, TimeUnit.SECONDS)
			.removalListener(removalListener)
			.build(loader);
			
			*/
	
    public static final ScheduledThreadPoolExecutor game = new ScheduledThreadPoolExecutor(10);
    
    public static final ThreadPoolExecutor shark = new ThreadPoolExecutor(1, 10, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1000));
    
    public static void execute(Runnable r) {
    	
    	shark.execute(r);
    	
    }
    
    public static ScheduledFuture<?> schedule(Runnable r, long time, TimeUnit tu) {
    	
    	return game.schedule(r, time, tu);
    	
    }
    
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long time, TimeUnit tu) {
    	
    	return game.scheduleAtFixedRate(r, delay, time, tu);
    	
    }
    
    /* public static LoadingCache<Coordinates, LocationLevel> getCache() {
    	
    	return cache;
    	
    }
    
    public static LocationLevel load(Coordinates key) {
    	
    	try {
    	
    		return cache.get(key);
    	
    	} catch (Exception e) {
    		
    		e.printStackTrace();
    		
    		return null;
    		
    	}
    	
    } */
    
}
