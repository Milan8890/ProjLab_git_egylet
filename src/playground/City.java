package playground;

import java.util.Arrays;
import java.util.Set;

import entities.*;
import playground.Crossing;

/**
 * City
 * <p>
 * 
 * Felelősség <br>
 * Utak, kereszteződések felvétele, autók felvétele, hókotróbázis kijelölése, azaz város inicializálása.
 * Autók számára legrövidebb járható út számolása.
 * Mivel a játék folyamán csak egy várost fogunk használni, és sok helyről szeretnénk elérni, statikus osztály lesz.
 */
public class City {
	/**
	 * A városban lévő kereszteződések.
	 */
	static Set<Crossing> crossings;
	/**
	 * A városban lévő utak.
	 */
	static Set<Road> roads;
	/**
	 * A városban lévő autók.
	 */
	static Set<Car> cars;
	/**
	 * A hókotróbázis.
	 */
	static Crossing snowplowBase;

	/**
	 * A várost inicializáló függvény
	 */
	public static void initCity() {

	}

	/**
	 * Városban lévő kereszteződések lekérdezése.
	 */
	public static Set<Crossing> getCrossings() {
		return crossings;
	}

	/**
	 * Hókotróközpont lekérdezése.
	 */
	public static Crossing getSnowplowBase() {
		return snowplowBase;
	}

	/**
	 * A városban két kereszteződés között a legrövidebb útvonalat adja vissza.
	 * @param from a kiindulási kereszteződés
	 * @param to a célkereszteződés
	 * @return a két kereszteződés közötti legrövidebb Path
	 */
	public static Path shortestPathFrom(Crossing from, Crossing to) {

	}
}