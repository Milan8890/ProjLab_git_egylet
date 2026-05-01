package equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * @param owner     a Hókotró aminek tároljuk a fejeit
	 * @param ownedHead a fej amit a Hókotró már birtokol, ez lesz az aktív fej is
	 */
	private HeadInventory(Snowplower snowplower, Head activeHead) {
		this.snowplower = snowplower;
		this.activeHead = activeHead;
		this.heads = new ArrayList<>();
		this.shop = new ArrayList<>();
	}

	/**
	 * Konstruál egy Hókotróhoz egy fejtárolót aminek jégtörő feje van
	 * 
	 * @param owner a Hókotró aminek tároljuk a fejeit
	 * @return HeadInventory ami a helyes elemeket tartalmazza
	 */
	public static HeadInventory createWithBreaker(Snowplower owner) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Konstruál egy Hókotróhoz egy fejtárolót aminek hányó feje van
	 * 
	 * @param owner a Hókotró aminek tároljuk a fejeit
	 * @return HeadInventory ami a helyes elemeket tartalmazza
	 */
	public static HeadInventory createWithEjector(Snowplower owner) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Visszaadja a fejtárolóban jelenleg aktív fejet
	 * 
	 * @return a jelenleg aktív fej
	 */
	public Head getActiveHead() {
		return activeHead;
	}

	/**
	 * Visszadja a fejtárolóban jelenleg megvehető Listingeket
	 * 
	 * @return a jelenleg megvehető Listingek
	 */
	public List<HeadListing> getShop() {
		return shop;
	}

	/**
	 * Az aktív fejet a következő megvásárolt fejre váltja.
	 */
	public void cycleActiveHead() {
		int idx = heads.indexOf(activeHead);
		activeHead = heads.get((idx + 1) % heads.size());
	}

	/**
	 * Megvásárol egy fejet a listing alapján, ha van elég pénze a játékosnak
	 * 
	 * @param listing a megvásárolni kívánt fej listingje
	 * @return sikeres volt-e a vásárlás
	 */
	public boolean buyListing(HeadListing listing) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

}