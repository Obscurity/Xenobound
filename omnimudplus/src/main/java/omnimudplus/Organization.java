package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashSet;

import omnimudplus.Entities.Mobile;

public class Organization implements Serializable {
	
	LinkedHashSet<Mobile> members = new LinkedHashSet<Mobile>();
	
	private static final long serialVersionUID = 1L;

}
