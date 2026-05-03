package playground;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

import entities.*;
import main.App;

/**
 * A sávokat tartalmazza, valamint egy referenciát a kereszteződésre amiből
 * kiindul, és amibe érkezik.
 * <p>
 * 
 * Felelősség <br>
 * Ha egy jármű megcsúszik az úton, akkor eldönti hogy beleütközik-e egy másik
 * járműbe, és jelzi nekik. Havazást szimulálva havat rak a sávjaira.
 */
public class Road {
	private static final int ONTICKSNOW = 1;
	private static final double SNOWCHANCE = 0.1;
	private static final int REVTIME = 12;
	/**
	 * Az úthoz tartozó sávok listája.
	 */
	List<Lane> lanes;
	/**
	 * A kereszteződés, amiből kiindul.
	 */
	Crossing fromCrossing;
	/**
	 * A kereszteződés, amibe érkezik.
	 */
	Crossing toCrossing;
	/**
	 * Az út hossza.
	 */
	double length;

	/**
	 * Út létrehozása
	 * 
	 * @param from       Kiinduló kereszteződés
	 * @param to         Végző kereszteződés
	 * @param numOfLanes Sávok száma
	 * @param length     Út hossza
	 */
	public Road(Crossing from, Crossing to, int numOfLanes, double length) {

		this.fromCrossing = from;
		this.toCrossing = to;
		this.length = length;
		this.lanes = new ArrayList<>(numOfLanes);
		for (int i = 0; i < numOfLanes; i++) {
			lanes.add(new Lane(this));
		}
		from.addOutRoad(this);
		
		Logger.getGlobal().log(Level.INFO,
				"[Obj] created between [Obj] and [Obj] with length " + this.length + " and " + numOfLanes + " lanes",
				new Object[] { this, from, to });
	}

	/**
	 * rak havat az összes hozzá tartozó sávra
	 */
	public void onTick() {
		if (RandomGenerator.getDefault().nextDouble() < SNOWCHANCE) {
			for (Lane lane : lanes) {
				if (!lane.hasSalt()) {
					lane.addSnow(ONTICKSNOW);
					Logger.getGlobal().log(Level.INFO, "[Obj] put 1 snow on [Obj]", new Object[] { this, lane });
				} else {
					Logger.getGlobal().log(Level.INFO, "[Obj] putting snow on [Obj] blocked by salt",
							new Object[] { this, lane });
				}
			}
		}
	}

	/**
	 * visszaadja az úthoz tartozó sávokat
	 * 
	 * @return az úthoz tartozó sávok
	 */
	public List<Lane> getLanes() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned lanes", new Object[] { this });

		return lanes;
	}

	/**
	 * visszaadja az út hosszát
	 * 
	 * @return az út hossza
	 */
	public double getLength() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned length " + length, new Object[] { this });

		return length;
	}

	/**
	 * visszaadja azt a kereszteződést amiből kiindul
	 * 
	 * @return a kereszteződés, amiből kiindul
	 */
	public Crossing getFromCrossing() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned start [Obj]", new Object[] { this, fromCrossing });

		return fromCrossing;
	}

	/**
	 * visszaadja azt a kereszteződést amibe be megy
	 * 
	 * @return a kereszteződés, amibe megy
	 */
	public Crossing getToCrossing() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned end [Obj]", new Object[] { this, toCrossing });

		return toCrossing;
	}

	/**
	 * Egy megcsúszott jármű hívja meg, összeütközteti azt egy másik járművel.
	 * <p>
	 * (Ha nincs másik jármű az úton akkor semmit nem csinál)
	 * 
	 * @param v
	 */
	public void crashVehicle(Vehicle v) {
		for (Lane lane : lanes) {
			for (Vehicle other : lane.getVehicles()) {
				if (other != v) {
					other.crashedInto(REVTIME);
					v.crashed(REVTIME);
					Logger.getGlobal().log(Level.INFO, "[Obj] collided [Obj] into [Obj] on [Obj]",
							new Object[] { this, v, other, lane });
					return;
				}
			}
		}
		Logger.getGlobal().log(Level.INFO, "[Obj] tried colliding [Obj], but no target was found",
				new Object[] { this, v });
	}

}
