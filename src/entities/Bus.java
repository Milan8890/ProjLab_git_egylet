package entities;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import user.BusDriver;
import playground.Lane;

/**
 * A BusDriver játékosok által irányított járművek, a stationA, és a stationB
 * között közlekednek
 * <p>
 * 
 * Felelősség <br>
 * Két megálló közti fordulók számolása. Elakadás, ha túl magas a hó. Csúszás
 * kezdeményezése. Ütközés kezelése, ütközés utáni újraindulás. Útvonal
 * követése.
 * Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el. Letaposás végzése.
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Bus extends Vehicle {
	/**
	 * A busz egyik végállomása.
	 */
	Crossing stationA;
	/**
	 * A busz másik végállomása.
	 */
	Crossing stationB;
	/**
	 * A busz tulajdonosa.
	 */
	BusDriver driver;
	/**
	 * Jelzi, hogy a busz éppen melyik végállomás felé tart.
	 */
	boolean isCurrentDestinationA;

	/**
     * Konstruktor.
     * 
     * @param stationA A busz első megállója.
     * @param stationB A busz második megállója.
     * @param driver    A busz tulajdonosa.
     */
	public Bus(Crossing stationA, Crossing stationB, BusDriver driver) {
		super(stationA);
		this.stationA = stationA;
		this.stationB = stationB;
		this.driver = driver;
	}
	
	/**
     * Meghívja az őse reachedCrossing metódusát, utána ha az egyik végállomáshoz ért, 
	 * akkor a sofőrnek ad egy pontot, és átállítja, hogy a másik végállomás felé menjen
     */
	public void reachedCrossing() {
		super.reachedCrossing();

		if(this.stationA.equals(this.lastCrossing) && this.isCurrentDestinationA) {
			driver.addPoint();
			this.isCurrentDestinationA = false;

			Logger.getGlobal().log(Level.INFO, "[Obj] reached [Obj] end point" , new Object[] {this, lastCrossing});
			Logger.getGlobal().log(Level.INFO, "[Obj] current destination [Obj]" , new Object[] {this, stationB});
			Logger.getGlobal().log(Level.INFO, "[Obj] added point to [Obj]" , new Object[] {this, driver});

		}
		else if(this.stationB.equals(this.lastCrossing) && !this.isCurrentDestinationA) {
			driver.addPoint();
			this.isCurrentDestinationA = true;

			Logger.getGlobal().log(Level.INFO, "[Obj] reached [Obj] end point" , new Object[] {this, lastCrossing});
			Logger.getGlobal().log(Level.INFO, "[Obj] current destination [Obj]" , new Object[] {this, stationA});
			Logger.getGlobal().log(Level.INFO, "[Obj] added point to [Obj]" , new Object[] {this, driver});
		}



	}
}