package entities;

import user.Cleaner;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import equipment.Head;
import equipment.HeadInventory;
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;

/**
 * 
 * A játékos által irányított jármű.<br>
 * Tudja melyik hókotrófejet használja éppen a hókotró.<br>
 * Tudja mennyi só, illetve biokerozin van még az egyes fejekhez.<br>
 * 
 * <p>
 * </p>
 * 
 * Felelősségei: <br>
 * kotrófejek cserélése, vásárlása. Alapanyag vásárlása, Eldönti, hogy fel tud-e
 * hajtani egy útra.<br>
 * Útvona követése. Ütközés utáni újraindulás. Nyilvántartja, hogy a jelenlegi
 * úton hol helyezkedik el.<br>
 * Letaposás végzése. A fej által visszaadott mennyiséggel kifizeti a
 * játékost.<br>
 */
public class Snowplower extends Vehicle {

	static final int SALT_PRICE = 25;
	static final int BIO_PRICE = 25;
	static final int GRAVEL_PRICE = 25;

	static final int SALT_BUY_AMOUNT = 10;
	static final int BIO_BUY_AMOUNT = 10;
	static final int GRAVEL_BUY_AMOUNT = 50;

	static final int MAX_GRAVEL = 1000;

	/**
	 * A hókotrót birtokló takarító.
	 */
	Cleaner cleaner;
	/**
	 * Inventory ami tartalmazza a megvett és megvehető árucikkeket.
	 */
	HeadInventory headInventory;
	/**
	 * A só állománya, ezt használja a sószórófej.
	 */
	double saltAmount;
	/**
	 * A biokerozin állománya, ezt használja a sárkány fej.
	 */
	double bioAmount;
	/**
	 * A zuzalék állománya, ezt használja a zuzalékszóró fej.
	 */
	double gravelAmount;

	/**
	 * Konstruktor
	 * 
	 * @param cleaner   a játékos aki irányítja a hókotrót
	 * @param spawn     a kereszteződés ahol a hókotró megjelenik
	 * @param inventory a fejtároló amivel a hókotró rendelkezik
	 */
	private Snowplower(Cleaner cleaner) {
		super(City.getSnowplowBase());
		this.cleaner = cleaner;
		saltAmount = 0;
		bioAmount = 0;
		gravelAmount = 0;
		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
		this.path = new Path(this);
	}

	/**
	 * Visszaadja, hogy behajthat-e az adott sávra a hókotrófeje szerint.
	 */
	@Override
	public boolean canEnterLane(Lane l) {
		return headInventory.getActiveHead().canEnterLane(l);
	}

	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel
	 * tér vissza.
	 * 
	 * @return true
	 */
	@Override
	public boolean stepWaitBecauseOfStuck() {
		return true;
	}

	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel
	 * tér vissza.
	 * 
	 * @return true
	 */
	@Override
	public boolean stepStuckInSnow() {
		return true;
	}

	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel
	 * tér vissza.
	 * 
	 * @return true
	 */
	@Override
	public boolean stepSlipOnIce() {
		return true;
	}

	/**
	 * Az aktív fejével letakarítja a sávot amin van, majd meghívja az őse
	 * reachedCrossing()-ját.
	 */
	@Override
	public void reachedCrossing() {
		cleaner.addMoney(headInventory.getActiveHead().clean(currentLane)); // Hogy kéne átadni a sávot?
		super.reachedCrossing();
	}

	/**
	 * visszaadja a birtokló játékost
	 * 
	 * @return A hókotrót birtokló játékos
	 */
	public Cleaner getCleaner() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned [Obj]", new Object[] { this, cleaner });

		return cleaner;
	}

	/**
	 * visszaadja a headInventory-t
	 * 
	 * @return HeadInventory
	 */
	public HeadInventory getHeadInventory() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned [Obj]", new Object[] { this, headInventory });

		return headInventory;
	}

	/**
	 * Visszaadja, hogy mennyi só áll rendelkezésre.
	 * 
	 * @return mennyi Só áll rendelkezésre
	 */
	public double getSalt() {
		Logger.getGlobal().log(Level.INFO, "[Obj] has " + saltAmount + " salt", new Object[] { this });

		return saltAmount;
	}

	/**
	 * Visszaadja, hogy mennyi biokerozin áll rendelkezésre.
	 * 
	 * @return mennyi Biokerozin áll rendelkezésre
	 */
	public double getBio() {
		Logger.getGlobal().log(Level.INFO, "[Obj] has " + bioAmount + " bio", new Object[] { this });

		return bioAmount;
	}

	/**
	 * Visszaadja, hogy mennyi zuzalék áll rendelkezésre.
	 * 
	 * @return mennyi zuzalék áll rendelkezésre
	 */
	public double getGravel() {
		Logger.getGlobal().log(Level.INFO, "[Obj] has " + gravelAmount + " gravel", new Object[] { this });

		return gravelAmount;
	}

	/**
	 * Vesz egy adag sót. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buySalt() {
		if (!isInCrossing()) {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy salt, because not in crossing",
					new Object[] { this });
			return false;
		}

		if (cleaner.removeMoney(SALT_PRICE)) {
			saltAmount += SALT_BUY_AMOUNT;
			Logger.getGlobal().log(Level.INFO, "[Obj] bought salt successfully", new Object[] { this });
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy salt, because not enough money",
					new Object[] { this });
			return false;
		}
	}

	/**
	 * Vesz egy adag kerozint. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buyBio() {
		if (!isInCrossing()) {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy bio, because not in crossing",
					new Object[] { this });
			return false;
		}

		if (cleaner.removeMoney(BIO_PRICE)) {
			bioAmount += BIO_BUY_AMOUNT;
			Logger.getGlobal().log(Level.INFO, "[Obj] bought bio successfully", new Object[] { this });
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy bio, because not enough money",
					new Object[] { this });
			return false;
		}
	}

	/**
	 * Vesz egy adag zuzalékot. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buyGravel() {
		if (!isInCrossing()) {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy gravel, because not in crossing",
					new Object[] { this });
			return false;
		}

		if (gravelAmount + GRAVEL_BUY_AMOUNT <= MAX_GRAVEL) {
			if (cleaner.removeMoney(GRAVEL_PRICE)) {
				gravelAmount += GRAVEL_BUY_AMOUNT;

				Logger.getGlobal().log(Level.INFO, "[Obj] bought gravel successfully", new Object[] { this });
				return true;
			} else {
				Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy gravel, because not enough money",
						new Object[] { this });
				return false;
			}

		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy gravel, because not enough space",
					new Object[] { this });
			return false;
		}
	}

	/**
	 * levonja az elhasznált sót
	 * 
	 * @param saltAmount Az elhasznált só mennyisége
	 */
	public void useSalt(double a) {
		Logger.getGlobal().log(Level.INFO, "[Obj] used " + a + "salt", new Object[] { this });
		saltAmount -= a; // Ennyi?
	}

	/**
	 * levonja az elhasznált kerozint
	 * 
	 * @param bioAmount Az elhasznált kerozin mennyisége
	 */
	public void useBio(double a) {
		Logger.getGlobal().log(Level.INFO, "[Obj] used " + a + "bio", new Object[] { this });
		bioAmount -= a; // Ennyi?
	}

	/**
	 * levonja az elhasznált kerozint
	 * 
	 * @param bioAmount Az elhasznált kerozin mennyisége
	 */
	public void useGravel(double a) {
		Logger.getGlobal().log(Level.INFO, "[Obj] used " + a + "gravel", new Object[] { this });
		gravelAmount -= a; // Ennyi?
	}

	/**
	 * Létrehoz és visszaad egy új hókotrót hányó fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithEjector(Cleaner owner) {
		Snowplower sp = new Snowplower(owner);
		sp.headInventory = HeadInventory.createWithEjector(sp);
		return sp;
	}

	/**
	 * Létrehoz és visszaad egy új hókotrót jégtörő fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithBreaker(Cleaner owner) {

		Snowplower sp = new Snowplower(owner);
		sp.headInventory = HeadInventory.createWithBreaker(sp);
		return sp;
	}
}