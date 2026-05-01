package entities;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.List;

import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import playground.Lane;

/**
 * A gépi irányítású járművek, home és work közt közlekednek
 * <p>
 * 
 * Felelősség <br>
 * A legrövidebb út újraterveztetése, ha blokkolva lenne egy sáv. Elakad, ha túl
 * magas a
 * hó. Kezdeményezi a csúszást. Ütközés kezelése. Nyilvántartja, hogy a
 * jelenlegi úton
 * hol helyezkedik el. Letaposás végzése. <br>
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Car extends Vehicle {
	/**
	 * A gépjármű otthona.
	 */
	Crossing home;
	/**
	 * A gépjármű munkahelye.
	 */
	Crossing work;
	/**
	 * Jelzi, hogy a gépjármű hazafelé tart-e.
	 */
	boolean isGoingHome;

	/**
	 * Konstruktor, beállítja az autó otthonát és munkahelyét.
	 * 
	 * @param home
	 * @param work
	 */
	public Car(Crossing home, Crossing work) {
		super(home);
		this.home = home;
		this.work = work;
		this.isGoingHome = false;
	}

	/**
	 * Meghívja az őse stepFollowPath()-jét, és ha ezután is
	 * kereszteződésben van (azaz nem tudott ráhajtani a sávra ami meg volt adva),
	 * akkor a
	 * City-től kér egy új útvonalat, abból a kereszteződésből amiben van, abba ami
	 * a
	 * célpontja.
	 * 
	 * @return Igaz, ha az ősosztály hívása sikeres volt, egyébként hamis.
	 */
	@Override
	protected boolean stepFollowPath() {
		boolean success = super.stepFollowPath();

		if (!success && isInCrossing()) {
			Crossing target = isGoingHome ? home : work;
			this.path = City.shortestPathFrom(this.lastCrossing, target);
		}
		return success;
	}

	/**
	 * Meghívja az őse stepWaitAfterCrash() metódusát, majd
	 * ha éppen felépült az ütközésből, akkor beállítja a helyzetét az otthonára,
	 * és beállítja hogy a munkája felé megy.
	 *
	 * @return Igaz, ha az autó már nincs mozgásképtelen állapotban, egyébként
	 *         hamis.
	 */
	@Override
	protected boolean stepWaitAfterCrash() {
		boolean recovered = super.stepWaitAfterCrash();
		// Ezen kéna javítani, hogy az IsCrashed-et állítsa false-ra

		if (recovered && this.isCrashed) {
			this.lastCrossing = home;
			this.isGoingHome = false;
			this.currentLane = null;
			this.isCrashed = false;
			this.path.clear();
		}

		return recovered;
	}

	/**
	 * Ha elérte célpontját, megváltoztatja a célpontját a másik kereszteződésre
	 * (ha hazaért a munkahelyre, ha munkába ért akkor haza), és kér egy új
	 * útvonalat a Citytől a pillanatnyi helyétől az új cél felé.
	 */
	@Override
	public void reachedCrossing() {
		super.reachedCrossing();

		if (isGoingHome && this.lastCrossing.equals(home)) {

			isGoingHome = false;
			this.path = City.shortestPathFrom(this.lastCrossing, work);

		} else if (!isGoingHome && this.lastCrossing.equals(work)) {

			isGoingHome = true;
			this.path = City.shortestPathFrom(this.lastCrossing, home);
		}
	}

	public void onTick() {

	}
}