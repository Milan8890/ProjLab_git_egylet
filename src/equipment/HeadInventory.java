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
 * A vehető fejek nyilvántartása, megvett fejek tárolása, aktív fej nyilvántartása. Új fej vételének lebonyolítása.
 */
public class HeadInventory {
    /**
     * A Hókotró amihez a fejtároló tartozik
     */
    Snowplower owner;

    /**
     * Konstruktor
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @param ownedHead a fej amit a Hókotró már birtokol, ez lesz az aktív fej is
     */
    private HeadInventory(Snowplower owner, Head ownedHead){


    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek jégtörő feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithBreaker(Snowplower owner){

    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek hányó feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithEjector(Snowplower owner) {

    }
    /**
     * visszaadja a fejtárolóban jelenleg aktív fejet
     * 
     * @return a jelenleg aktív fej
     */
    public Head getActiveHead() {

    }

    /**
     * visszadja a fejtárolóban jelenleg megvehető Listingeket
     * @return a jelenleg megvehető Listingek
     */
    public List<HeadListing> getShop() {

    }
    /**
     * Átváltja az aktív fejet a következőre a fejtárolóban
     */
    public void cycleActiveHead() {

    }
    /**
     * Megvásárol egy fejet a listing alapján, ha van elég pénze a játékosnak
     * @param listing a megvásárolni kívánt fej listingje
     * @return sikeres volt-e a vásárlás
     */
    public boolean buyListing(HeadListing listing){

    }

}