package playground;

import java.util.HashSet;
import java.util.Set;

/**
 * Kereszteződést leíró objektum.
 * 
 * Felelősségei: <br>
 * Nyilvántartja a belőle induló utakat.
 */
public class Crossing {
	/**
	 * A kereszteződésből kiinduló utak.
	 */
	Set<Road> outRoads = new HashSet<Road>();

	/**
	 * Konstruktor, létrehoz egy új kereszteződést.
	 */
	public Crossing() {
	
	}

	/**
	 * Visszaadja a kimenő utakat
	 * 
	 * @return a kimenő utakat
	 */
	public Set<Road> getOutRoads() {
		return outRoads;
	}

	/**
	 * Hozzáad egy utat a belőle kiinduló utakhoz.
	 * 
	 * @param r a hozzáadandó út
	 */
	public void addOutRoad(Road r) {
		outRoads.add(r);
	}
	
}