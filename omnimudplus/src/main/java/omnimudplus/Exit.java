package omnimudplus;
import java.io.*;

public class Exit implements Serializable {
	
	private Room destination;
	
	static final private long serialVersionUID = 5L;
	
	public Exit(Room destination) {
		
		this.destination = destination;
		
	}
	
	public Room getDestination() {
			
		return destination;

	}

}