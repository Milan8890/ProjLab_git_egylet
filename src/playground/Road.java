package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		App.CreateObject(this);
		Logger.getGlobal().log(Level.INFO, "[Src] created between [Obj] and [Obj]", new Object[] { from, to });

		this.fromCrossing = from;
		this.toCrossing = to;
		this.length = length;
		this.lanes = new ArrayList<>(numOfLanes);
		for (int i = 0; i < numOfLanes; i++) {
			lanes.add(new Lane(this));
		}
	}

	/**
	 * rak havat az összes hozzá tartozó sávra
	 */
	public void onTick() {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * visszaadja az úthoz tartozó sávokat
	 * 
	 * @return az úthoz tartozó sávok
	 */
	public List<Lane> getLanes() {
		return lanes;
	}

	/**
	 * visszaadja az út hosszát
	 * 
	 * @return az út hossza
	 */
	public double getLength() {
		return length;
	}

	/**
	 * visszaadja azt a kereszteződést amiből kiindul
	 * 
	 * @return a kereszteződés, amiből kiindul
	 */
	public Crossing getFromCrossing() {
		return fromCrossing;
	}

	/**
	 * visszaadja azt a kereszteződést amibe be megy
	 * 
	 * @return a kereszteződés, amibe megy
	 */
	public Crossing getToCrossing() {
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
		throw new UnsupportedOperationException("Még nincs kész");
	}

}
