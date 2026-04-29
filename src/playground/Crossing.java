package playground;

import java.util.HashSet;
import java.util.Set;

/**
 * Kereszteződést leíró objektum. Nyilvántartja a belőle induló utakat.
 */
public class Crossing {
	Set<Road> outRoads = new HashSet<Road>();

	public Crossing() {
	
	}

	/**
	 * visszaadja a kimenő utakat
	 * 
	 * @return a kimenő utakat
	 */
	public Set<Road> getOutRoads() {

	}
}