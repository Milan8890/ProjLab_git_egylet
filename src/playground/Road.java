package playground;

import main.Skeleton;

import java.rmi.server.SkeletonMismatchException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.LimitExceededException;

/**
 * A sávokat tartalmazza, valamint egy referenciát a kereszteződésre amiből
 * kiindul, és amibe érkezik.
 * <p>
 * Ha egy jármű megcsúszik az úton, akkor eldönti hogy beleütközik-e egy másik
 * járműbe, és jelzi nekik. Havazást szimulálva havat rak a sávjaira.
 */
public class Road {
	// Lehet nem kell?
	List<Lane> lanes;
	Crossing fromCrossing;
	Crossing toCrossing;

	public Road(Crossing from, Crossing to, int numOfLanes, double length) {
		Skeleton.initSettingUpObjectStart(this);
		lanes = new ArrayList<Lane>();
		// TODO ez kéne?
		for (int i = 0; i < numOfLanes; i++) {
			lanes.add(new Lane());
		}

		fromCrossing = from;
		toCrossing = to;

		Skeleton.initSettingUpObjectEnd();
	}

	public void onTick() {
		for (Lane l : lanes) {
			// TODO add hó
		}
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	public double getLength() {
		int ans = Skeleton.questionValue("Milyen hosszú a " + Skeleton.createNameOfObject(this) + " út?");
		return (double) ans;
	}

	public Crossing getFromCrossing() {
		return fromCrossing;
	}

	public Crossing getToCrossing() {
		return toCrossing;
	}

	public void crashVehicle() {
		// ???
		// most akkor a lane-eken lévőt össze kéne szednie, de hogyan?
		// eltárolják?
		// kéne központi objektumtár?

		// TODO ha kész a lane
	}

}
