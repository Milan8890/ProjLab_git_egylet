package playground;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.App;

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
		// App.CreateObject(this);

		Logger.getGlobal().log(Level.FINEST, "Started creating [Obj]...", this);

		// param param

		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
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