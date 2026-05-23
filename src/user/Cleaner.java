package user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	List<Snowplower> snowplowers;

	private static final int BREAKER_SNOWPLOWER_PRICE = 13000;
	private static final int EJECTOR_SNOWPLOWER_PRICE = 12000;

	/**
	 * Konstruktor, létrehoz egy új takarító játékost. A játékosnak kezdetben nincs
	 * pénze se hókotrója.
	 * 
	 * @param name a játékos neve
	 */
	public Cleaner(String name) {
		super(name);
		money = 0;
		snowplowers = new ArrayList<>();
		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
	}

	/**
	 * Visszaadja a játékos pénzét.
	 * 
	 * @return pénz
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Hozzáad pénzt a játékoshoz
	 * 
	 * @param m az hozzáadandó pénz összege
	 */
	public void addMoney(int m) {
		Logger.getGlobal().log(Level.INFO, "[Obj] received " + m + "$", new Object[] { this });
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

			Logger.getGlobal().log(Level.INFO, "[Obj] deducted " + m + "$ successfully", new Object[] { this });
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] deducted " + m + "$ unsuccessfully", new Object[] { this });
			return false;
		}
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát,
	 * a hókotróihoz hozzáad egy új jégtörő fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér
	 * hamissal.
	 * 
	 * @return {@code true}, ha sikerült a vásárlás, {@code false} egyébként
	 */
	public boolean buyBreakerSnowplower() {
		if (removeMoney(BREAKER_SNOWPLOWER_PRICE)) {
			Snowplower p = Snowplower.createWithBreaker(this);
			snowplowers.add(p);
			Logger.getGlobal().log(Level.INFO, "[Obj] bought [Obj] with starting head Breaker successfully",
					new Object[] { this, p });
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO,
					"[Obj] couldn't buy snowplower with starting head Breaker, because not enough money",
					new Object[] { this });
			return false;
		}
	}

	/**
	 * Hozzáad (pénz levonása nélkül) egy új jégtörőfejes hókotrót
	 */
	public void createBreakerSnowplower() {
		this.addMoney(BREAKER_SNOWPLOWER_PRICE);
		if (!buyBreakerSnowplower()) {
			Logger.getGlobal().log(Level.SEVERE,
					"Couldn't create breaker snowplower for [Obj], because not enough money, which just got added.",
					this);
		}
	}

	/**
	 * Ha van elég pénze a játékosnak levonja egy új hókotró árát,
	 * a hókotróihoz hozzáad egy új hányó fejes hókotrót,
	 * és Igaz értékkel tér vissza, ha nincs akkor nem von le pénzt és visszatér
	 * hamissal.
	 * 
	 * @return {@code true}, ha sikerült a vásárlás, {@code false} egyébként
	 */
	public boolean buyEjectorSnowplower() {
		if (removeMoney(EJECTOR_SNOWPLOWER_PRICE)) {
			Snowplower p = Snowplower.createWithEjector(this);
			snowplowers.add(p);
			Logger.getGlobal().log(Level.INFO, "[Obj] bought [Obj] with starting head Ejector successfully",
					new Object[] { this, p });
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO,
					"[Obj] couldn't buy snowplower with starting head Ejector, because not enough money",
					new Object[] { this });
			return false;
		}
	}

	/**
	 * Hozzáad (pénz levonása nélkül) egy új hányófejes hókotrót
	 */
	public void createEjectorSnowplower() {
		this.addMoney(EJECTOR_SNOWPLOWER_PRICE);
		if (!buyEjectorSnowplower()) {
			Logger.getGlobal().log(Level.SEVERE,
					"Couldn't create ejector snowplower for [Obj], because not enough money, which just got added.",
					this);
		}
	}

	/**
	 * Visszaadja a játékos által irányított hókotrók listáját.
	 * 
	 * @return a játékos hókotrói
	 */
	public List<Snowplower> getSnowplowers() {
		return snowplowers;
	}

}