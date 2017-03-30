package omnimudplus;
import java.io.*;

public class Exit implements Serializable {
	
	private Location destination;
	
	static final private long serialVersionUID = 5L;
	
	public Exit(Location destination) {
		
		this.destination = destination;
		
	}
	
	public Location getDestination() {
			
		return destination;

	}

}