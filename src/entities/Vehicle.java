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
 * Járművek absztrakt ősosztálya, egy alapvető viselkedést implementál, amit a leszármazottak felülírhatnak:
 * Automatikusan követi az utat. Ha túl nagy lenne a hóréteg, elakad.
 * Jeges úton egy adott eséllyel megcsúszik, ezt jelzi az útnak.
 * Ütközés kezelése. Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el.
 * Letaposás végzése.
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
	 * Éppen hol tart az úton ahol megy.
	 */
	double laneProgress;
	/**
	 * Hozzá tartozó útvonal.
	 */
	Path path;
	/**
	 * Be van-e ragadva az autó (karambol vagy hóba), lezárja a sávot.
	 */
	boolean isStuck;
	/**
	 * Az autó éppen karambolban van-e, emiatt nem záródik le a sáv.
	 */
	boolean isChrashed;
	/**
	 * Mennyi ideig nem fog még mozogni egy karambol után.
	 */
	int revTimer;

	/**
	 * Minden órajelkor meghívódó függvény, kezeli a járművek mozgását.
	 * Sorban meghívja az alábbi függvényeket.
	 * Az egyes lépések igazzal térnek vissza, ha futhat tovább a többi lépés, hamissal, ha nem
	 */
	abstract protected void onTick();

	/**
	 * Ha kereszteződésben van a jármű, akkor lekéri a következő sávot amire be van állítva hogy hajtson. 
	 * Ha van ilyen és nincs lezárva, akkor ráhajt és visszatér true-val,
	 * különben törli a beállított útvonalát, és false-al tér vissza.
	 */
	protected boolean stepFollowPath() {

		return true;
	}

	/**
	 * Az egyes járművekben definiálandó virtuális függvény. Visszaadja, hogy az adott jármű behajthat-e a sávra.
	 */
	protected boolean canEnterLane(Lane l) {
		return true;	//mivel minden csak a kotró hsználja ott felül van írva, a többinek jó a true
	}

	/**
	 * Ha éppen Ütközött állapotban van a jármű, 
	 * akkor csökkenti azt az időt ameddig még ütközött állapotban van.
	 * Ez után, ha ez az idő 0-ra csökkent, akkor true-val tér vissza, különben false-val. 
	 * Ha nem volt ütközött állapotban, true-val tér vissza.
	 */
	protected boolean stepWaitAfterCrash() {

		return true;
	}

	/**
	 * Ha a sávon van más jármű ami eltorlaszolja az utat, akkor false-al, különben true-val tér vissza.
	 */
	protected boolean stepWaitBecauseOfStuck(){

		return true;
	}

	/**
	 * Ha túl magas a hó abban a sávban amin van, akkor megnézi hogy van-e az úton másik sáv ahol nem túl mély a hó.
	 * Ha van, akkor átmegy rá. Ha nincs, akkor a Stuck állapotot beállítja true értékre, és visszatér false-val.
	 * Ha sikeresen tud tovább haladni, visszatér true-val.
	 */
	protected boolean stepStuckInSnow() {

		return true;
	}

	/**
	 * Ha a sávon amin halad a jármű egy határérték felett van a jég mennyisége,
	 * és azt nem védi zuzalék, akkor egy megadott eséllyel meghívja az út crashVehicle() függvényét paraméterül megadva önmagát.
	 * Ez után ellenőrzi azt, hogy ütköztek-e belé. Ha igen, false-al tér vissza, ha nem, true-val.
	 */
	protected boolean stepSlipOnIce() {

		return true;
	}

	/**
	 *  Halad a jármű az úton egy megadott mennyiséget. Ha ezzel az út végére ér, akkor meghívja a reachedCrossing() metódust.
	 */
	protected void stepMoveOnLane() {

	}

	/**
	 * Leveszi magát a sávról aminek a végére ért, és beállítja a helyzetét arra a kereszteződésbe,
	 * ami annak az útnak a végpontja, ami tartalmazza a sávját.
	*/
	protected void reachedCrossing() {

	}

	/**
	 * Visszaadja, hogy el van-e akadva.
	 */
	public boolean isStuck() {
		return isStuck;
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
	 * Hozzáadja a path-hez az “l” sávot.
	 * <p>
	 * A jármű a feladatot továbbítja a saját Path objektumának,
	 * amely elvégzi a sáv tényleges hozzáadását.
	 * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
	 */
	public boolean extendPath(Lane l) {
		return path.extendPath(l);
	}

	/**
	 * Meghívódik, ha ő karambolozik bele valamibe, isCrashed igaz lesz.
	 * 
	 * @param r A várakozási idő (revTimer)
	 */
	public void crashedInto(int r) {

	}

	/**
	 * Meghívódik ha neki ütköznek, isStuck és isCrashed igaz lesz.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {

	}

}