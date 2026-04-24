package entities;

import java.util.Arrays;
import java.util.List;


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
	Crossing stationA;
	Crossing stationB;

	BusDriver owner;

	public Bus(Crossing stationA, Crossing stationB, BusDriver owner) {

	}
	
	public void onTick() {

	}
}