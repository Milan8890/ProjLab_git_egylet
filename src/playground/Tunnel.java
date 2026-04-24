package playground;

import java.util.ArrayList;

/**
 * Olyan fajta út, amire nem esik rá a hó (mert fedve van).
 */
public class Tunnel extends Road {
	public Tunnel(Crossing from, Crossing to, int numOfLanes, double length) {

	}

	/**
	 * Felülírja a Road függvényét, hogy ne havazzon.
	 */
	@Override
	public void onTick() {

	}
}