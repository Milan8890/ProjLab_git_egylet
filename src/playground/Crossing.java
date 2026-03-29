package playground;

import java.util.HashSet;
import java.util.Set;

import main.Skeleton;

/**
 * Kereszteződést leíró objektum. Nyilvántartja a belőle induló utakat.
 */
public class Crossing {
	Set<Road> outRoads = new HashSet<Road>();

	public Crossing() {
		Skeleton.initSettingUpObjectStart(this);

		Skeleton.initSettingUpObjectEnd();
	}

	/**
	 * visszaadja a kimenő utakat
	 * 
	 * @return a kimenő utakat
	 */
	public Set<Road> getOutRoads() {
		Skeleton.logFunctionStart(this, "getOutRoads", null);

		Skeleton.logFunctionEnd();
		return outRoads;
	}
}