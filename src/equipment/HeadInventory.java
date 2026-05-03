package equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.heads.*;

/**
 * Fejtároló egy hókotróhoz
 * <p>
 * 
 * Felelősség <br>
 * A vehető fejek nyilvántartása, megvett fejek tárolása, aktív fej
 * nyilvántartása.
 * Új fej vételének lebonyolítása.
 */
public class HeadInventory {

	private static final int SWEEPERPRICE = 1000;
	private static final int EJECTORPRICE = 2000;
	private static final int BREAKERPRICE = 3000;
	private static final int GRAVELSPREADERPRICE = 4000;
	private static final int SALTSPREADERPRICE = 5000;
	private static final int DRAGONPRICE = 6000;
	/**
	 * A Hókotró amihez a fejtároló tartozik
	 */
	Snowplower snowplower;
	/**
	 * A Hókotró által birtokolt fejek listája.
	 */
	List<Head> heads;
	/**
	 * A Hókotró által megvásárolható fejek listája.
	 */
	List<HeadListing> shop;
	/**
	 * A Hókotró által jelenleg használt fej.
	 */
	Head activeHead;

	/**
	 * Konstruktor
	 * 
	 * @param driver     a Hókotró aminek tároljuk a fejeit
	 * @param ownedHead a fej amit a Hókotró már birtokol, ez lesz az aktív fej is
	 */
	private HeadInventory(Snowplower snowplower, Head activeHead) {
		this.snowplower = snowplower;
		this.activeHead = activeHead;
		this.heads = new ArrayList<>();
		this.shop = new ArrayList<>();
		// TODO
		// Nincs belerakva a heads-be az active head, és a shop-ba a többi fej?
		// Vagy nem tudom, akkor nem kell az activeHead paraméter
		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
	}

	/**
	 * Konstruál egy Hókotróhoz egy fejtárolót aminek jégtörő feje van
	 * 
	 * @param owner a Hókotró aminek tároljuk a fejeit
	 * @return HeadInventory ami a helyes elemeket tartalmazza
	 */
	public static HeadInventory createWithBreaker(Snowplower owner) {
		Head activeHead = new Breaker(owner);
		HeadInventory ret = new HeadInventory(owner, activeHead);
		ret.heads.add(activeHead);
		ret.shop.add(new HeadListing( new Sweeper(owner) , SWEEPERPRICE));
		ret.shop.add(new HeadListing( new Ejector(owner) , EJECTORPRICE));
		ret.shop.add(new HeadListing( new GravelSpreader(owner) , GRAVELSPREADERPRICE));
		ret.shop.add(new HeadListing( new SaltSpreader(owner) , SALTSPREADERPRICE));
		ret.shop.add(new HeadListing( new Dragon(owner) , DRAGONPRICE));
		ret.activeHead=activeHead;

		Logger.getGlobal().log(Level.INFO, "[Obj] created with owner [Obj] and starting head Breaker" , new Object[] {ret, owner});
		return ret;
	}

	/**
	 * Konstruál egy Hókotróhoz egy fejtárolót aminek hányó feje van
	 * 
	 * @param owner a Hókotró aminek tároljuk a fejeit
	 * @return HeadInventory ami a helyes elemeket tartalmazza
	 */
	public static HeadInventory createWithEjector(Snowplower owner) {
		Head activeHead = new Ejector(owner);
		HeadInventory ret = new HeadInventory(owner, activeHead);
		ret.heads.add(activeHead);
		ret.shop.add(new HeadListing( new Sweeper(owner) , SWEEPERPRICE));
		ret.shop.add(new HeadListing( new Breaker(owner) , BREAKERPRICE));
		ret.shop.add(new HeadListing( new GravelSpreader(owner) , GRAVELSPREADERPRICE));
		ret.shop.add(new HeadListing( new SaltSpreader(owner) , SALTSPREADERPRICE));
		ret.shop.add(new HeadListing( new Dragon(owner) , DRAGONPRICE));
		ret.activeHead=activeHead;

		Logger.getGlobal().log(Level.INFO, "[Obj] created with owner [Obj] and starting head Ejector" , new Object[] {ret, owner});
		return ret;
	}
		

	/**
	 * Visszaadja a fejtárolóban jelenleg aktív fejet
	 * 
	 * @return a jelenleg aktív fej
	 */
	public Head getActiveHead() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned active head [Obj]" , new Object[] {this, activeHead});
		return activeHead;
	}

	/**
	 * Visszadja a fejtárolóban jelenleg megvehető Listingeket
	 * 
	 * @return a jelenleg megvehető Listingek
	 */
	public List<HeadListing> getShop() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned available head listings" , new Object[] {this});
		return shop;
	}

	/**
	 * Az aktív fejet a következő megvásárolt fejre váltja.
	 */
	public void cycleActiveHead() {
		int idx = heads.indexOf(activeHead);
		activeHead = heads.get((idx + 1) % heads.size());

		Logger.getGlobal().log(Level.INFO, "[Obj] switched active head to [Obj]" , new Object[] {this, activeHead});
	}

	/**
	 * Megvásárol egy fejet a listing alapján, ha van elég pénze a játékosnak
	 * 
	 * @param listing a megvásárolni kívánt fej listingje
	 * @return sikeres volt-e a vásárlás
	 */
	public boolean buyListing(HeadListing listing) {

		if(!snowplower.isInCrossing())
		{
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy [Obj], because not in crossing" , new Object[] {this, listing});
			return false;
		}

		if(snowplower.getCleaner().removeMoney(listing.price)){
			heads.add(listing.head);
			Logger.getGlobal().log(Level.INFO, "[Obj] bought [Obj] successfully" , new Object[] {this, listing});
			return true;
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t buy [Obj], because not enough money", new Object[] {this, listing});
			return false;
		}
		
	}

}