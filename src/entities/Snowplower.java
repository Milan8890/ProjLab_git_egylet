package entities;

import user.Cleaner;
import java.util.Arrays;

import equipment.Head;
import equipment.HeadInventory;
import playground.Crossing;
import playground.Lane;

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
	/**
	 * A hókotrót birtokló takarító.
	 */
	Cleaner owner;
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
	 * @param owner     a játékos aki irányítja a hókotrót
	 * @param spawn     a kereszteződés ahol a hókotró megjelenik
	 * @param inventory a fejtároló amivel a hókotró rendelkezik
	 */
	private Snowplower(Cleaner owner, Crossing spawn) {
		this.owner = owner;
		this.lastCrossing = spawn;
		saltAmount=0;
		bioAmount=0;
		gravelAmount=0;
	}


	/**
	 * Visszaadja, hogy behajthat-e az adott sávra a hókotrófeje szerint.
	 */
	@Override
	public boolean canEnterLane(Lane l) {
		return  headInventory.getActiveHead().canEnterLane(l);
	}

	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel tér vissza.
	 */
	@Override
	public boolean stepWaitBecauseOfStuck() {
		return  true;
	}

	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel tér vissza.
	 */
	@Override
	public boolean stepStuckInSnow() {
		return  true;
	}

	
	/**
	 * Nem csinál semmit, mert a hókotró nem tud megakadni a hóban. True értékkel tér vissza.
	 */
	@Override
	public boolean stepSlipOnIce() {
		return  true;
	}

	/**
	 * Az aktív fejével letakarítja a sávot amin van, majd meghívja az őse reachedCrossing()-ját.
	 */
	@Override
	public void reachedCrossing() {
		headInventory.getActiveHead().clean(currentLane); //Hogy kéne átadni a sávot?
		super.reachedCrossing();
	}

	/**
	 * visszaadja a birtokló játékost
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
		return headInventory;
	}

	/**
	 * Visszaadja, hogy mennyi só áll rendelkezésre.
	 * 
	 * @return mennyi Só áll rendelkezésre
	 */
	public double getSalt() {
		return saltAmount;
	}

	/**
	 * Visszaadja, hogy mennyi biokerozin áll rendelkezésre.
	 * 
	 * @return mennyi Biokerozin áll rendelkezésre
	 */
	public double getBio() {
		return bioAmount;
	}

	/**
	 * Visszaadja, hogy mennyi zuzalék áll rendelkezésre.
	 * 
	 * @return mennyi zuzalék áll rendelkezésre
	 */
	public double getGravel() {
		return gravelAmount;
	}
	

	private static final int saltPrice = 25; 
	private static final int saltAmountPerPurchase = 10; //TODO: ezt ki kéne szervezni valszeg innen, de itthagyom
	
	/**
	 * Vesz egy adag sót. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buySalt() { 
		if(owner.removeMoney(saltPrice)){
			saltAmount += saltAmountPerPurchase;
			return true;
		}
		else{
			return false;
		}
	}

	private static final int bioPrice = 25; //25 pénzért vesz 10l biot, ez így jó? 
	private static final int bioAmountPerPurchase = 10; //TODO: ezt ki kéne szervezni valszeg innen, de itthagyom
	
	/**
	 * Vesz egy adag kerozint. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buyBio() {	
		if(owner.removeMoney(bioPrice)){
			bioAmount += bioAmountPerPurchase;
			return true;
		}
		else{
			return false;
		}
	}


	private static final int gravelPrice = 25; //25 pénzért vesz 50kg zuzalékot, ez így jó?
	private static final int gravelAmountPerPurchase = 50; //TODO: ezt ki kéne szervezni valszeg innen, de itthagyom
	
	/**
	 * Vesz egy adag zuzalékot. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem
	 * kereszteződésben van.
	 * 
	 * @return Sikeres volt-e a vásárlás
	 */
	public boolean buyGravel() {
		if(owner.removeMoney(gravelPrice)){
			gravelAmount += gravelAmountPerPurchase;
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * levonja az elhasznált sót
	 * 
	 * @param saltAmount Az elhasznált só mennyisége
	 */
	public void useSalt(double a) {
		saltAmount -= a; //Ennyi?
	}

	/**
	 * levonja az elhasznált kerozint
	 * 
	 * @param bioAmount Az elhasznált kerozin mennyisége
	 */
	public void useBio(double a) {
		bioAmount -= a; //Ennyi?
	}

	/**
	 * levonja az elhasznált kerozint
	 * 
	 * @param bioAmount Az elhasznált kerozin mennyisége
	 */
	public void useGravel(double a) {
		gravelAmount -= a; //Ennyi?
	}

	/**
	 * Létrehoz és visszaad egy új hókotrót hányó fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @param base  a kereszteződés ahol a hókotró megjelenik
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithEjector(Cleaner owner, Crossing base) {
		Snowplower sp = new Snowplower(owner, base);
		sp.headInventory = HeadInventory.createWithEjector(sp);
		return sp;
	}

	/**
	 * Létrehoz és visszaad egy új hókotrót jégtörő fejjel.
	 * 
	 * @param owner a játékos aki irányítja a hókotrót
	 * @param base  a kereszteződés ahol a hókotró megjelenik
	 * @return a létrehozott Hókotró
	 */
	public static Snowplower createWithBreaker(Cleaner owner, Crossing base) {
		Snowplower sp = new Snowplower(owner, base);
		sp.headInventory = HeadInventory.createWithBreaker(sp);
		return sp;
	}
}