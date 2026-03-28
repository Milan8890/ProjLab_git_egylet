package playground;

import java.util.ArrayList;

import main.Skeleton;

public class Tunnel extends Road {
	public Tunnel(Crossing from, Crossing to, int numOfLanes, double length) {
		// elm OK?
		super(from, to, numOfLanes, length);
	}

	/**
	 * Felülírja a Road függvényét, hogy ne havazzon.
	 */
	@Override
	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", new ArrayList<>());

		Skeleton.logFunctionEnd();
	}
}