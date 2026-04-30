package entities;

import java.util.Arrays;

import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;

/**
 * A járművek absztrakt ősosztálya.
 * <p>
 * 
 * Felelősség <br>
 * Automatikusan követi az utat. Ha túl nagy lenne a hóréteg, elakad. Jeges úton
 * egy adott eséllyel megcsúszik, ezt jelzi az útnak.
 */
public abstract class Vehicle {
	/**
	 * A legutóbbi kereszteződés, amin volt.
	 */
	Crossing lastCrossing;
	/**
	 * Az a sáv amin éppen megy.
	 */
	Lane currentLane;
	/**
	 * Éppen hol tart az sávon amin megy.
	 */
	double laneProgress;
	/**
	 * Hozzá tartozó útvonalterv.
	 */
	Path path;
	/**
	 * Be van-e ragadva a jármű.
	 */
	boolean isStuck;
	/**
	 * A jármű éppen balesetben van-e.
	 */
	boolean isCrashed;
	/**
	 * Mennyi ideig nem fog még tudni menni a baleset után.
	 */
	int revTimer;

	/**
	 * Konstruktor
	 * 
	 * @param lastCrossing A legutóbbi kereszteződés, amin volt.
	 */
	public Vehicle(Crossing lastCrossing) {
		this.lastCrossing = lastCrossing;
		this.currentLane = null;
		this.laneProgress = 0.0;
		this.path = new Path();
		this.isStuck = false;
		this.isCrashed = false;
		this.revTimer = 0;
	}

	/**
	 * Meghívódik, ha a jármű beleütközik valamibe.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Meghívódik, ha egy másik jármű megy neki ennek a járműnek.
	 * 
	 * @param r A várakozási idő (revTimer)
	 */
	public void crashedInto(int r) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Megkísérel ráhajtani a következő sávra az útvonalterv alapján.
	 * 
	 * @return Igaz, ha sikerült a haladás, egyébként hamis.
	 */
	protected boolean stepFollowPath() {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Kezeli az ütközés utáni kényszerpihenőt.
	 * 
	 * @return Igaz, ha a jármű újra mozgásképes, egyébként hamis.
	 */
	protected boolean stepWaitAfterCrash() {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Kezeli a kereszteződés elérését.
	 */
	protected void reachedCrossing() {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Visszaadja, hogy a jármű jelenleg kereszteződésben várakozik-e.
	 * <p>
	 * Ha a járműnek nincs kijelölt sávja (currentLane értéke null),
	 * akkor egy kereszteződésben tartózkodik.
	 * * @return true, ha kereszteződésben van, egyébként false.
	 */
	public boolean isInCrossing() {
		return currentLane == null;
	}

	/**
	 * Meghosszabbítja a jármű útvonalát egy új sávval.
	 * <p>
	 * A jármű a feladatot továbbítja a saját Path objektumának,
	 * amely elvégzi a sáv tényleges hozzáadását.
	 * * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
	 */
	public void extendPath(Lane l) {
		this.path.extendPath(l);
	}
}