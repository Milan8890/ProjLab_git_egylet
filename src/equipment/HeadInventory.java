package equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Snowplower;
import equipment.heads.*;
import main.Skeleton;
/**
 * Fejtároló egy hókotróhoz
 * <p>
 * 
 * Felelősség <br>
 * A vehető fejek nyilvántartása, megvett fejek tárolása, aktív fej nyilvántartása. Új fej vételének lebonyolítása.
 */
public class HeadInventory {
    Snowplower owner;
    Head activeHead;
    List<Head> heads;
    List<HeadListing> listings;

    /**
     * Konstruktor
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @param ownedHead a fej amit a Hókotró már birtokol, ez lesz az aktív fej is
     */
    private HeadInventory(Snowplower owner, Head ownedHead){
        Skeleton.initSettingUpObjectStart(this);
        this.owner = owner;
        heads = new ArrayList<>();
        heads.add(ownedHead);
        listings=new ArrayList<>();

    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek jégtörő feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithBreaker(Snowplower owner){
        HeadInventory ret = new HeadInventory(owner, new Breaker(owner));
        ret.listings = new ArrayList<>(Arrays.asList( 
            new HeadListing(new Sweeper(owner) , 0), 
            new HeadListing(new Ejector(owner), 0),
            new HeadListing(new SaltSpreader(owner), 0),
            new HeadListing(new Dragon(owner), 0)));
        Skeleton.initSettingUpObjectEnd();
        return ret;

    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek hányó feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithEjector(Snowplower owner) {
        HeadInventory ret = new HeadInventory(owner, new Ejector(owner));
        ret.listings = new ArrayList<>(Arrays.asList( 
            new HeadListing(new Sweeper(owner) , 0), 
            new HeadListing(new Breaker(owner), 0),
            new HeadListing(new SaltSpreader(owner), 0),
            new HeadListing(new Dragon(owner), 0)));
        Skeleton.initSettingUpObjectEnd();
        return ret;
    }
    /**
     * visszaadja a fejtárolóban jelenleg aktív fejet
     * 
     * @return a jelenleg aktív fej
     */
    public Head getActiveHead() {
        Skeleton.logFunctionStart(this, "getActiveHead", null);
        Skeleton.logFunctionEnd();
        return activeHead;
    }
}