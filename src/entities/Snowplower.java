package entities;

import user.Cleaner;
import java.util.Arrays;

import equipment.HeadInventory;
import playground.Crossing;
import main.Skeleton;

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
	Cleaner owner;

	/**
	 * Konstruktor
	 * 
	 * @param owner     a játékos aki irányítja a hókotrót
	 * @param spawn     a kereszteződés ahol a hókotró megjelenik
	 * @param inventory a fejtároló amivel a hókotró rendelkezik
	 */
	private Snowplower(Cleaner owner, Crossing spawn) {

	}

	/**
	 * Sáv elhagyásakor tisztítja a sávot az aktív fejjel.
	 */
	public void onTick() {
		
	}

	/**
	 * visszaadja a birtokló játékost
	 * 
	 * 
	 * 
	 * @return A hókotrót birtokló játékos
	 */
	public Cleaner getCleaner() {
		return owner;
	}

	/**
	 * visszaadja a headInventory-t
	 * 
	 * @return HeadInventory
	 */
	public HeadInventory getHeadInventory() {
	
	}

	/**
	 * Visszaadja, hogy mennyi só áll rendelkezésre.
	 * 
	 * @return mennyi Só áll rendelkezésre
	 */
	public double getSalt() {

	}

	/**
	 * Visszaadja, hogy mennyi biokerozin áll rendelkezésre.
	 * 
	 * @return mennyi Biokerozin áll rendelkezésre
	 */
	public double getBio() {

	}

	/**
	 * Vesz egy adag sót. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buySalt() {

	}

	/**
	 * Vesz egy adag kerozint. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buyBio() {

	}

	/**
	 * levonja az elhasznált sót
	 * 
	 * @param saltAmount Az elhasznált só mennyisége
	 */
	public void useSalt(double saltAmount) {

	}

	/**
	 * levonja az elhasznált kerozint
	 * 
	 * @param bioAmount Az elhasznált kerozin mennyisége
	 */
	public void useBio(double bioAmount) {

	}

	/**
	 * Konstruktor segítségével létrehoz egy Hókotrót egy hányó fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @param base  a kereszteződés ahol a hókotró megjelenik
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithEjector(Cleaner owner, Crossing base) {

	}

	/**
	 * Konstruktor segítségével létrehoz egy Hókotrót egy jégtörő fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @param base  a kereszteződés ahol a hókotró megjelenik
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithBreaker(Cleaner owner, Crossing base) {

	}
}