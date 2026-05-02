package user;

import entities.Bus;
import entities.Snowplower;
import playground.City;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Egyik játékos fajta, buszt irányít 2 megálló között.
 * 
 * Felelősségei: <br>
 * Megtett fordulók számolása, busz nyilvántartása.
 * 
 */
public class BusDriver extends Player {
	/**
	 * A játékos pontjainak száma.
	 */
	int point;
	/**
	 * A játékoshoz tartozó busz.
	 */
	Bus bus;
	
	/**
	 * Konstruktor, létrehoz egy új buszvezető játékost.
	 * 
	 * @param name  a játékos neve
	 */
	public BusDriver(String name) {
		super(name);
		point = 0;
	}

	/**
	 * Ad pontot a játékosnak.
	 */
	public void addPoint() {
		Logger.getGlobal().log(Level.INFO, "[Obj] recived one point", new Object[] {this});
		point++;
	}

}