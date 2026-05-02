package playground;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

	/**
	 * Visszaadja a kimenő utakat
	 * 
	 * @return a kimenő utakat
	 */
	public Set<Road> getOutRoads() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned outgoing roads" , new Object[] {this});
		return outRoads;
	}

	/**
	 * Hozzáad egy utat a belőle kiinduló utakhoz.
	 * 
	 * @param r a hozzáadandó út
	 */
	public void addOutRoad(Road r) {
		Logger.getGlobal().log(Level.INFO, "[Obj] added [Obj] to outgoing roads" , new Object[] {this, r});
		outRoads.add(r);
	}

}