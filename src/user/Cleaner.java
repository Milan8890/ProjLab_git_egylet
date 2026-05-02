package user;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import playground.City;
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
	
	private static final int BREAKER_SNOWPLOWER_PRICE = 13000;
	private static final int EJECTOR_SNOWPLOWER_PRICE = 12000;



	/**
	 * Konstruktor, létrehoz egy új takarító játékost. A játékosnak kezdetben nincs pénze se hókotrója.
	 * 
	 * @param name a játékos neve
	 */
	public Cleaner(String name) {
		super(name);
		money=0;
		snowplowers = new HashSet<>();
	}

	/**
	 * Hozzáad pénzt a játékoshoz
	 * 
	 * @param m az hozzáadandó pénz összege
	 */
	public void addMoney(int m) {
		Logger.getGlobal().log(Level.INFO, "[Obj] received " + m + "$", new Object[] {this});
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
		if (money >= m) {
			money -= m;

			Logger.getGlobal().log(Level.INFO, "[Obj] deducted " + m + "$  successfully", new Object[] {this});
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] deducted " + m + "$ unsuccessfully", new Object[] {this});
			return false;
		}
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát,
	 * a hókotróihoz hozzáad egy új törő fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér
	 * hamissal.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyBreakerSnowplower() {
		Snowplower p = Snowplower.createWithBreaker(this, City.getSnowplowBase());

		Logger.getGlobal().log(Level.INFO, "[Obj] bought [Obj] with starting head Breaker successfully", new Object[] {this, p});
		Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy [Obj] with starting head Breaker, because not enough money", new Object[] {this, p});


		throw new UnsupportedOperationException("Még nincs kész");
		if(removeMoney(BREAKER_SNOWPLOWER_PRICE)){
			snowplowers.add(Snowplower.createWithBreaker(this, City.getSnowplowBase()));
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát,
	 * a hókotróihoz hozzáad egy új hányó fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér
	 * hamissal.
	 * 
	 * @return true, ha sikerült a vásárlás, false egyébként
	 */
	public boolean buyEjectorSnowplower() {
		Snowplower p = Snowplower.createWithEjector(this, City.getSnowplowBase());

		Logger.getGlobal().log(Level.INFO, "[Obj] bought [Obj] with starting head Ejector successfully", new Object[] {this, p});
		Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy [Obj] with starting head Ejector, because not enough money", new Object[] {this, p});
		
		throw new UnsupportedOperationException("Még nincs kész");
		if(removeMoney(EJECTOR_SNOWPLOWER_PRICE)){
			snowplowers.add(Snowplower.createWithEjector(this, City.getSnowplowBase()));
			return true;
		}
		else{
			return false;
		}
	}

}