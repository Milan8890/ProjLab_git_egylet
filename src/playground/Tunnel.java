package playground;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Olyan fajta út, amire nem esik rá a hó (mert fedve van).
 * <p>
 * 
 */
public class Tunnel extends Road {
	/**
	 * Konstruktor, ami meghívja a Road konstruktorát.
	 */
	public Tunnel(Crossing from, Crossing to, int numOfLanes, double length) {
		super(from, to, numOfLanes, length);
	}

	/**
	 * Felülírja a Road függvényét, hogy ne havazzon.
	 */
	@Override
	public void onTick() {
		Logger.getGlobal().log(Level.INFO, "[Obj] blocked snowing", new Object[] { this });
	}
}