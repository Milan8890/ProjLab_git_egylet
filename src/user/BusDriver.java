package user;

import entities.Bus;
import entities.Snowplower;
import playground.City;
import playground.Crossing;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

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
	 * @param name a játékos neve
	 */
	public BusDriver(String name) {
		super(name);
		point = 0;
		Random random = new Random();
		if (City.getCrossings().size() < 2) {
			Logger.getGlobal().log(Level.SEVERE,
					"Nincs elég kereszteződés a városban egy busz létrehozásához! BusDriver: [Obj]",
					new Object[] { this });
			return;
		}
		Crossing crossingA = City.getCrossings().get(random.nextInt(City.getCrossings().size()));
		Crossing crossingB = City.getCrossings().get(random.nextInt(City.getCrossings().size()));

		while (crossingB == crossingA) {
			crossingB = City.getCrossings().get(random.nextInt(City.getCrossings().size()));
		}
		bus = new Bus(crossingA, crossingB, this);
		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
	}

	/**
	 * Visszaadja a játékos pénzét.
	 * 
	 * @return pontok
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * Visszaadja a játékos buszát.
	 * 
	 * @return A busz
	 */
	public Bus getBus() {
		return bus;
	}

	/**
	 * Ad pontot a játékosnak.
	 */
	public void addPoint() {
		Logger.getGlobal().log(Level.INFO, "[Obj] received one point", new Object[] { this });
		point++;
	}

}