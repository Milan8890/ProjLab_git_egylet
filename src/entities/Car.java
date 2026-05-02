package entities;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

	/**
	 * Ha elérte célpontját, megváltoztatja a célpontját a másik kereszteződésre
	 * (ha hazaért a munkahelyre, ha munkába ért akkor haza), és kér egy új
	 * útvonalat a Citytől a pillanatnyi helyétől az új cél felé.
	 */
	@Override
	public void reachedCrossing() {
		super.reachedCrossing();

		// TODO path-et nem kell újat kérni, majd kér a followPath

		if (isGoingHome && this.lastCrossing.equals(home)) {
			isGoingHome = false;
			Logger.getGlobal().log(Level.INFO, "[Obj] reached home, going to work", new Object[] { this });
		} else if (!isGoingHome && this.lastCrossing.equals(work)) {
			isGoingHome = true;
			Logger.getGlobal().log(Level.INFO, "[Obj] reached work, going to home", new Object[] { this });
		}
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
			Logger.getGlobal().log(Level.INFO, "[Obj] has no path, requesting new path between [Obj] and [Obj]", new Object[] { this, lastCrossing, target });
			this.path = City.shortestPathFrom(this.lastCrossing, target, this);
		}
		return success;
	}

	/**
	 * Beállítja a helyzetét az otthonára, és elindítja a munkahelye felé.
	 */
	@Override
	protected void revive() {
		Logger.getGlobal().log(Level.INFO, "[Obj] after accident, starting again from home", new Object[] { this });
		this.lastCrossing = home;
		this.isGoingHome = false;
		this.currentLane = null;
		this.path.clear();
	}

	/**
	 * Megvizsgálja, hogy a jelenlegi sávon található-e elakadt jármű.
	 * <p>
	 * Ha a sávon bármelyik jármű isStuck állapotban van, az az egész sávot
	 * blokkolja. Ekkor az autó újratervezi az útvonalát és várakozik.
	 * 
	 * @return Hamis, ha van elakadt jármű a sávon, egyébként igaz.
	 */
	@Override
	protected boolean stepWaitBecauseOfStuck() {
		if (currentLane.hasStuckVehicle()) {
			// TODO Nem kellett meghívni a shortestPath-et, mert itt nem tervez újra, csak
			// áll.
			Logger.getGlobal().log(Level.INFO, "[Obj] [Obj] is blocked by crash, stopping and clearing [Obj]", new Object[] { this, currentLane, path });
			return false;
		}

		return true;
	}
}