package omnimudplus;

import java.util.Comparator;

public class PathfindingComparator implements Comparator<PathfindingNode> {

	public int compare(PathfindingNode a, PathfindingNode b) {
		
		if (a.getF() < b.getF()) {
			
			return -1;
			
		} else if (a.getF() > b.getF()) {
			
			return 1;
			
		} else {
			
			return 0;
			
		}
		
	}
	
}
