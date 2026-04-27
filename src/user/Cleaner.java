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
 * Játékos pénzének kezelése. 
 * Új hókotró vásárlása. 
 * Játékos Hókotróinak nyilvántartása.
 * 
 */
public class Cleaner extends Player {
	/**
	 * A játékos pénze, amiből vásárolhat.
	 */
	int money;

	/**
	 * A játékos által irányított hókotrók halmaza.
	 */
	Set<Snowplower> snowplowers;
	
	/**
	 * Konstruktor, létrehoz egy új takarító játékost.
	 * 
	 * @param name  a játékos neve
	 */
	public Cleaner(String name) {
		
	}

	/**
	 * Hozzáad pénzt a játékoshoz	
	 * 
	 * @param m az hozzáadandó pénz összege
	 */
	public void addMoney(int m) {
		money += m;
	}

	/**
	 * Eltávolít pénzt a játékos pénzéből.
	 * Ha nincs elég pénz akkor visszatér false-al, <br>
	 * különben levonja a pézt és visszatér true-al.
	 * 
	 * @param m az eltávolítandó pénz összege
	 * @return true, ha sikerült az eltávolítás, false egyébként
	 */
	public boolean removeMoney(int m) {
		if(money >= m){
			money -= m;
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát, 
	 * a hókotróihoz hozzáad egy új törő fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér hamissal.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyBreakerSnowplower() {
		
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát,
	 * a hókotróihoz hozzáad egy új hányó fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér hamissal.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyEjectorSnowplower() {
		
	}

}