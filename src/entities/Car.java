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
		this.home = home;
		this.work = work;
		this.isGoingHome = false;
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

	public void onTick() {

	}
}