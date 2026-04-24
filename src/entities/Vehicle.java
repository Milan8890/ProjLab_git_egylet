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
	Crossing lastCrossing;
	Lane currentLane;

	/**
	 * Minden órajelkor meghívódó absztrakt függvény, mert minden jármű mást csinál.
	 */
	abstract public void onTick();

	/**
	 * Meghívódik, ha a jármű beleütközik valamibe.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {

	}

	/**
	 * Meghívódik, ha egy másik jármű megy neki ennek a járműnek.
	 * 
	 * @param r A várakozási idő (revTimer)
	 */
	public void crashedInto(int r) {

	}

	/**
	 * Visszaadja, hogy a jármű jelenleg kereszteződésben várakozik-e.
	 * <p>
	 * Ha a járműnek nincs kijelölt sávja (currentLane értéke null),
	 * akkor egy kereszteződésben tartózkodik.
	 * * @return true, ha kereszteződésben van, egyébként false.
	 */
	public boolean isInCrossing() {

	}

	/**
	 * Meghosszabbítja a jármű útvonalát egy új sávval.
	 * <p>
	 * A jármű a feladatot továbbítja a saját Path objektumának,
	 * amely elvégzi a sáv tényleges hozzáadását.
	 * * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
	 */
	public void extendPath(Lane l) {

	}
}