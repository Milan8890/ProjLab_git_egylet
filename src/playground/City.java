package playground;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.*;
import playground.Crossing;

/**
 * City
 * <p>
 * 
 * Felelősség <br>
 * Utak, kereszteződések felvétele, autók felvétele, hókotróbázis kijelölése,
 * azaz város inicializálása.
 * Autók számára legrövidebb járható út számolása.
 * Mivel a játék folyamán csak egy várost fogunk használni, és sok helyről
 * szeretnénk elérni, statikus osztály lesz.
 */
public class City {
	/**
	 * A városban lévő kereszteződések.
	 */
	static Set<Crossing> crossings = new HashSet<>();
	/**
	 * A városban lévő utak.
	 */
	static Set<Road> roads = new HashSet<>();
	/**
	 * A városban lévő autók.
	 */
	static Set<Car> cars = new HashSet<>();
	/**
	 * A hókotróbázis.
	 */
	static Crossing snowplowBase = null;

	/**
	 * A várost inicializáló függvény
	 */
	public static void initCity() {
		Logger.getGlobal().log(Level.INFO, "City initialized" , new Object[] {});
	}

	/**
	 * Városban lévő kereszteződések lekérdezése.
	 */
	public static Set<Crossing> getCrossings() {
		Logger.getGlobal().log(Level.INFO, "City returned crossings", new Object[] {});
		return crossings;
	}

	/**
	 * Hókotróközpont lekérdezése.
	 */
	public static Crossing getSnowplowBase() {
		Logger.getGlobal().log(Level.INFO, "City returned [Obj] as snowplower base", new Object[] {snowplowBase});
		return snowplowBase;
	}

	/**
	 * A városban két kereszteződés között a legrövidebb útvonalat adja vissza.
	 * 
	 * @param from a kiindulási kereszteződés
	 * @param to   a célkereszteződés
	 * @return a két kereszteződés közötti legrövidebb Path
	 */
	public static Path shortestPathFrom(Crossing from, Crossing to, Vehicle v) {
		Path p = new Path(v);
		Logger.getGlobal().log(Level.INFO, "City calculated shortest path from [Obj] to [Obj] as [Obj]", new Object[] {from, to, p});

		throw new UnsupportedOperationException("Még nincs kész");
	}
}