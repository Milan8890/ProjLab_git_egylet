package entities;

import main.Skeleton;
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

	protected boolean canEnterLane(Lane l) {
		return true;
	}

	// A beállított útvonalat követi.
	// Leveszi a következő lane-t, arra átvált, elindul rajta.
	// Ha nem OK a path, mert van rajta ütközött jármű, stb, akkor törli a path-et.

	// Autónál ha üres a path amikor crossingban van, újratervez
	protected void followPath() {
		if (this.isInCrossing()) {
		}
	}

	// kell revive?

	// Ütközés után vár, ha eltelt az idő újraéled.

	// Újraéledés hogyan? Legyen egy revive függvény?
	// Ha vár, akkor hogyan jelzi az onTick-nek?
	protected void waitAfterCrash() {

	}

	// Ha van azon a sávon ütközés, ahol ő van, akkor vár.

	// HA STUCK, MENGÉZNI
	protected void waitBecauseOfStuck() {

	}

	// Ha túl magas a hó, sávot próbál váltani, vagy megakad benne
	protected void stuckInSnow() {

	}

	// Jeges felületen megcsúszik a jármű
	protected void slipOnIce() {

	}

	// Dolgok amiket pluszban csinál, ha egy kereszteződésbe ér
	// Mindenhol külön át lesz definiálva, de megmarad üresen, ha lenne egy random
	// másik jármű típus, amin nem kell átírni.
	protected void reachedCrossing() {

	}

	// Úton haladás, és annak végére érés
	protected void moveOnLane() {
		reachedCrossing();
	}

	/**
	 * Minden órajelkor meghívódó absztrakt függvény, mert minden jármű mást csinál.
	 */
	public void onTick() {
		followPath();
		waitAfterCrash();
		waitBecauseOfStuck();
		stuckInSnow();
		slipOnIce();
		moveOnLane();
	}

	/**
	 * Meghívódik, ha a jármű beleütközik valamibe.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {
		Skeleton.logFunctionStart(this, "crashed", Arrays.asList(String.valueOf(r)));

		Skeleton.logFunctionEnd();
	}

	/**
	 * Meghívódik, ha egy másik jármű megy neki ennek a járműnek.
	 * 
	 * @param r A várakozási idő (revTimer)
	 */
	public void crashedInto(int r) {
		Skeleton.logFunctionStart(this, "crashedInto", Arrays.asList(String.valueOf(r)));

		Skeleton.logFunctionEnd();
	}

	/**
	 * Visszaadja, hogy a jármű jelenleg kereszteződésben várakozik-e.
	 * <p>
	 * Ha a járműnek nincs kijelölt sávja (currentLane értéke null),
	 * akkor egy kereszteződésben tartózkodik.
	 * * @return true, ha kereszteződésben van, egyébként false.
	 */
	public boolean isInCrossing() {
		Skeleton.logFunctionStart(this, "isInCrossing", null);

		int crossingAnswer = Skeleton.questionMultiple("Kereszteződésben van-e?", Arrays.asList("Igen", "Nem"));

		boolean result = (crossingAnswer == 1);

		Skeleton.logFunctionEnd();
		return result;
	}

	/**
	 * Meghosszabbítja a jármű útvonalát egy új sávval.
	 * <p>
	 * A jármű a feladatot továbbítja a saját Path objektumának,
	 * amely elvégzi a sáv tényleges hozzáadását.
	 * * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
	 */
	public void extendPath(Lane l) {
		Skeleton.logFunctionStart(this, "extendPath", Arrays.asList(Skeleton.createNameOfObject(l)));

		// Itt a jármű meghívja a Path-et ezzel a lane-el
		Skeleton.Market.path.extendPath(l);

		Skeleton.logFunctionEnd();
	}
}