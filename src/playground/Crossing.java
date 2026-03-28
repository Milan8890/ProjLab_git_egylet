package playground;

import java.util.ArrayList;
import java.util.Set;

import main.Skeleton;

/**
 * Kereszteződést leíró objektum. Nyilvántartja a belőle induló utakat.
 */
public class Crossing {
	Set<Road> outRoads;

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
		Skeleton.logFunctionStart(this, "getOutRoads", new ArrayList<>());

		Skeleton.logFunctionEnd();
		return outRoads;
	}
}