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
			lanes.add(new Lane(this));
		}

		fromCrossing = from;
		toCrossing = to;

		Skeleton.initSettingUpObjectEnd();
	}

	// TODO ennyi elég?
	/**
	 * rak havat az összes hozzá tartozó sávra
	 */
	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", new ArrayList<>());

		int ans = Skeleton.questionValue("Mennyi havat adjon hozzá a sávokhoz?");
		for (Lane l : lanes) {
			l.addSnow((double) ans);
		}

		Skeleton.logFunctionEnd();
	}

	/**
	 * visszaadja az úthoz tartozó sávokat
	 * 
	 * @return az úthoz tartozó sávok
	 */
	public List<Lane> getLanes() {
		Skeleton.logFunctionStart(this, "getLanes", new ArrayList<>());

		Skeleton.logFunctionEnd();
		return lanes;
	}

	/**
	 * visszaadja az út hosszát
	 * 
	 * @return az út hossza
	 */
	public double getLength() {
		Skeleton.logFunctionStart(this, "getLength", new ArrayList<>());

		int ans = Skeleton.questionValue("Milyen hosszú az út?");

		Skeleton.logFunctionEnd();
		return (double) ans;
	}

	/**
	 * visszaadja azt a kereszteződést amiből kiindul
	 * 
	 * @return a kereszteződés, amiből kiindul
	 */
	public Crossing getFromCrossing() {
		Skeleton.logFunctionStart(this, "getFromCrossing", new ArrayList<>());

		Skeleton.logFunctionEnd();
		return fromCrossing;
	}

	/**
	 * visszaadja azt a kereszteződést amibe be megy
	 * 
	 * @return a kereszteződés, amibe megy
	 */
	public Crossing getToCrossing() {
		Skeleton.logFunctionStart(this, "getToCrossing", new ArrayList<>());

		Skeleton.logFunctionEnd();
		return toCrossing;
	}

	/**
	 * Egy megcsúszott jármű hívja meg, összeütközteti azt egy másik járművel.
	 * <p>
	 * (Ha nincs másik jármű az úton akkor semmit nem csinál)
	 */
	public void crashVehicle() {
		Skeleton.logFunctionStart(this, "crashVehicle", new ArrayList<>());
		// ???
		// most akkor a lane-eken lévőt össze kéne szednie, de hogyan?
		// eltárolják?
		// kéne központi objektumtár?

		// TODO ha kész a lane
		Skeleton.logFunctionEnd();
	}

}
