package user;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import entities.Snowplower;
import playground.Crossing;

/**
 * Egyik játékos fajta, hókotrókat irányít.
 * 
 * Felelősségei: <br>
 * Játékos pénzének kezelése. Új hókotró vásárlása.
 * 
 */
public class Cleaner extends Player {
	/**
	 * Konstruktor, létrehoz egy új takarító játékost.
	 * 
	 * @param name  a játékos neve
	 * @param color a játékos színe
	 */
	public Cleaner(String name, Color color) {
		
	}

	/**
	 * Hozzáad pénzt a játékoshoz
	 * 
	 * @param m az hozzáadandó pénz összege
	 */
	public void addMoney(int m) {

	}

	/**
	 * Eltávolít pénzt a játékos pénzéből.
	 * Ha nincs elég pénz akkor visszatér false-al, <br>
	 * különben levonja a pézt és visszatér true-al
	 * 
	 * @param m az eltávolítandó pénz összege
	 * @return true, ha sikerült az eltávolítás, false egyébként
	 */
	public boolean removeMoney(int m) {
	
	}

	/**
	 * Vásárol egy breaker fejjel kezdő hókotrót.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyBreakerSnowplower() {
		
	}

	/**
	 * Vásárol egy ejector fejjel kezdő hókotrót.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyEjectorSnowplower() {
		
	}

}